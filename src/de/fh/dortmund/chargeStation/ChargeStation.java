package de.fh.dortmund.chargeStation;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

import de.fh.dortmund.exceptions.InvalidCarDataException;
import de.fh.dortmund.exceptions.LocationIsFullException;
import de.fh.dortmund.exceptions.LocationNotAvailableException;
import de.fh.dortmund.exceptions.MultipleCarsException;
import de.fh.dortmund.exceptions.WaitTimeException;
import de.fh.dortmund.model.Car;
import de.fh.dortmund.model.EnergySource;
import de.fh.dortmund.model.Location;

public class ChargeStation {

	private List<EnergySource> energySource;
	private List<Location> locations;

	public ChargeStation(List<EnergySource> energySource, List<Location> locations) {
		this.energySource = energySource;
		this.locations = locations;
	}

	public void checkLocations() throws LocationNotAvailableException, LocationIsFullException, WaitTimeException,
			InvalidCarDataException, Throwable {
		List<Location> locationList = getLocations();
		if (locationList != null) {
			try {
				checkCarWaitingList(locationList);
			} catch (MultipleCarsException ex) {
				throw new LocationIsFullException("Location is full, no charging space is available for car")
						.initCause(new MultipleCarsException("More than 1 car is not allowed in same location"));
			} catch (WaitTimeException ex) {
				throw new WaitTimeException("High Waiting Time, please try another location");
			} catch (InvalidCarDataException ex) {
				throw new InvalidCarDataException("Invalid car data for the location");
			}
		} else {
			throw new LocationNotAvailableException("No Location is available for car charging");
		}

	}

	private void checkCarWaitingList(List<Location> locationList)
			throws MultipleCarsException, WaitTimeException, InvalidCarDataException {

		for (Location loc : locationList) {
			List<Car> carList = loc.getCar();
			if (carList != null) {
				if (carList.size() <= 1) {
					boolean withinTime = checkwithinWaitTime(carList, loc.getbookSlotTime());
					if (withinTime) {
						/* To Do
						 * Add User car to the List loc.getCar().add(null);
						 */
					} else {
						throw new WaitTimeException("High Waiting Time, please try another location");
					}
				} else {
					throw new MultipleCarsException("More than 1 car is not allowed in same location");
				}
			} else {
				throw new InvalidCarDataException("Invalid car data for the location");
			}
		}

	}

	private boolean checkwithinWaitTime(List<Car> carList, LocalDateTime newBookingTime) {
		// Wait time is 5 minutes
		if (carList.size() == 0) {
			return true;
		} else if ((Duration.between(newBookingTime, carList.get(0).getApproximateTimeToGetCharged())
				.toMinutes()) > 5) {
			return false;
		}
		return false;
	}

	public List<EnergySource> getEnergySource() {
		return energySource;
	}

	public List<Location> getLocations() {
		return locations;
	}

}
