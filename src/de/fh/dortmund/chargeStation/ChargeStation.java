package de.fh.dortmund.chargeStation;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

import de.fh.dortmund.exceptions.LocationNotAvailableException;
import de.fh.dortmund.model.Car;
import de.fh.dortmund.model.Location;
import de.fh.dortmund.model.WaitTimeData;
import de.fh.dortmund.users.User;

public class ChargeStation {

	private List<Location> locations;

	public void checkLocations(User user) throws LocationNotAvailableException {
		List<Location> locationList = getLocations();
		if (locationList != null) {
			checkCarWaitingList(locationList, user.getCar());
		} else {
			throw new LocationNotAvailableException("No Location is available for car charging");
		}

	}

	private void checkCarWaitingList(List<Location> locationList, Car car) {

		int locationTried = 0;
		boolean addedToQueue = false;
		for (Location loc : locationList) {
			locationTried++;
			PriorityQueue<Car> pqCar = loc.getPqCar();
			if (pqCar.isEmpty()) {
				System.out.println("Slot is available and confirmed as there are no cars in the slot.");
				pqCar.add(car);
				break;
			} else if (pqCar.size() == 1) {
				WaitTimeData withinTime = checkwithinWaitTime(pqCar.peek(), car.getBookedTimeSlot());
				if (withinTime.isWithinWaitTime()) {
					try {
						Thread.sleep((withinTime.getWaitTime() * 3600));
					} catch (InterruptedException e) {
						System.out.println("System got interupted in between");
					}
					System.out.println((pqCar.poll()).getNumber() + " Car is charged and removed");
					System.out.println("Slot is confirmed for the car " + car.getNumber());
					pqCar.add(car);
				} else {
					System.out.println("High Waiting Time, please try another location");
					continue;
				}
			}
		}
		if (!addedToQueue && locationTried == locationList.size()) {
			WaitTimeData withinTime = checkwithinWaitTime(locationList.get(0).getPqCar().peek(),
					car.getBookedTimeSlot());
			try {
				System.out.println("Since slots in all locations are full, please wait till slot is available.");
				Thread.sleep(withinTime.getWaitTime() * 3600);
				locationList.get(0).getPqCar().poll();
				locationList.get(0).getPqCar().add(car);
				System.out.println("Slot confirmed for the car " + car.getNumber());
			} catch (InterruptedException e) {
				System.out.println("System got interupted in between");
			}

		}
	}

	private WaitTimeData checkwithinWaitTime(Car pqCar, LocalDateTime newBookingTime) {
		// Wait time is 5 minutes
		WaitTimeData waitTime = new WaitTimeData();
		long remainingTime = Duration.between(newBookingTime, pqCar.getApproximateTimeToGetCharged()).toMinutes();
		waitTime.setWaitTime(remainingTime);
		if (remainingTime > 5) {
			waitTime.setWithinWaitTime(false);
		}
		waitTime.setWithinWaitTime(true);
		return waitTime;
	}

	public List<Location> getLocations() {
		return locations;
	}

	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}

}
