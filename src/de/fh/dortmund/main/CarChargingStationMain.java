package de.fh.dortmund.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import de.fh.dortmund.chargeStation.ChargeStation;
import de.fh.dortmund.exceptions.LocationNotAvailableException;
import de.fh.dortmund.model.Car;
import de.fh.dortmund.model.EnergySource;
import de.fh.dortmund.model.Location;
import de.fh.dortmund.users.Admin;
import de.fh.dortmund.users.User;

public class CarChargingStationMain {

	public static void main(String[] args) {

		List<User> userInput = new ArrayList<>();
		String readData = null;
		try (BufferedReader br = new BufferedReader(new FileReader("users.txt"))) {
			// create new User
			while ((readData = br.readLine()) != null) {
				User user = new User();
				Car car = new Car();
				String[] readArray = readData.split(",");
				car.setOwnerName(readArray[0]);
				car.setNumber(readArray[1]);
				car.setBookedTimeSlot(
						LocalDateTime.parse(readArray[2], DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
				car.setBatteryLevel(Integer.parseInt(readArray[3]));
				car.setBrand(readArray[4]);
				user.setCar(car);
				userInput.add(user);
			}
		} catch (IOException e) {

		}
		Admin admin = new Admin();
		List<Admin> adminList = new ArrayList<>();
		List<EnergySource> energyList = new ArrayList<>();
		List<Location> locList = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader("admin.txt"))) {
			String read = null;
			// create new User
			while ((read = br.readLine()) != null) {
				Location loc = new Location();
				EnergySource energy = new EnergySource();
				String[] readArray = read.split(",");
				admin.setAdminName(readArray[0]);
				admin.setAdminID(readArray[1]);
				loc.setAreaName(readArray[2]);
				loc.setZipcode(Integer.parseInt(readArray[3]));
				loc.setWeather(readArray[4]);
				locList.add(loc);
				admin.setLocation(locList);
				energy.setId(readArray[5]);
				energy.setSourceName(readArray[6]);
				energy.setCapacity(readArray[7]);
				energyList.add(energy);
				admin.setEnergy(energyList);
				adminList.add(admin);
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		ChargeStation charge1 = new ChargeStation();
		ChargeStation charge2 = new ChargeStation();
		ChargeStation charge3 = new ChargeStation();
		charge1.setLocations(adminList.get(0).getLocation());
		charge2.setLocations(adminList.get(1).getLocation());
		charge3.setLocations(adminList.get(1).getLocation());
		StringBuilder error = new StringBuilder("");
		try {
			charge1.checkLocations(userInput.get(0));
		} catch (LocationNotAvailableException ex) {
			error = error.append(ex.getMessage());
		} finally {

			try (FileWriter fr = new FileWriter("ChargeStationlogs.txt")) {
				fr.write(error.toString());
			} catch (IOException e) {
				System.out.println("Error while writing to log file" + e.getMessage());
			}

		}

	}

}
