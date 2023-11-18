package de.fh.dortmund.main;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import de.fh.dortmund.chargeStation.ChargeStation;
import de.fh.dortmund.exceptions.InvalidCarDataException;
import de.fh.dortmund.exceptions.LocationIsFullException;
import de.fh.dortmund.exceptions.LocationNotAvailableException;
import de.fh.dortmund.exceptions.WaitTimeException;
import de.fh.dortmund.model.Car;
import de.fh.dortmund.model.EnergySource;
import de.fh.dortmund.model.Location;

public class CarChargingStationMain {

	public static void main(String[] args) {

		// Test Car ChargeStation
		Car car1 = new Car("KA1B200", "MR.A", "BMW", 10, LocalDateTime.of(2023, 11, 18, 10, 28));
		List<Car> carList1 = new ArrayList<>();
		carList1.add(car1);

		Location location1 = new Location("Nordstadt", 44135, carList1);
		List<Location> locationList1 = new ArrayList<>();
		locationList1.add(location1);

		EnergySource energy = new EnergySource("1", "Solar", "100W");
		List<EnergySource> energySorceList = new ArrayList<EnergySource>();
		energySorceList.add(energy);

		ChargeStation charge1 = new ChargeStation(energySorceList, locationList1);
		String error = "";
		try {
			charge1.checkLocations();
		} catch (LocationNotAvailableException | LocationIsFullException ex) {
			error = ex.getMessage() + " " + ex.getCause().toString();
		} catch (WaitTimeException e) {
			error = e.getMessage();
		} catch (InvalidCarDataException e) {
			error = e.getMessage();
		} catch (Throwable e) {
			error = e.getMessage();
		} finally {

			try (FileWriter fr = new FileWriter("logs.txt")) {
				fr.write(error);
			} catch (IOException e) {
				System.out.println("Error while writing to log file" + e.getMessage());
			}

		}

	}

}
