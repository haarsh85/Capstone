package de.fh.dortmund.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import de.fh.dortmund.chargeStation.ChargeStation;
import de.fh.dortmund.exceptions.LocationNotAvailableException;
import de.fh.dortmund.metadata.Metadata;
import de.fh.dortmund.model.Car;
import de.fh.dortmund.model.EnergySource;
import de.fh.dortmund.model.Location;
import de.fh.dortmund.users.Admin;
import de.fh.dortmund.users.User;

public class CarChargingStationMain {

	private static BufferedWriter fr1;

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
				printLogs2("User " + user.getCar().getOwnerName() + " data created from file");
			}
		} catch (IOException e) {

			printLogs1(e.getMessage());
		}
		Admin admin = new Admin();
		List<Admin> adminList = new ArrayList<>();
		List<EnergySource> energyList = new ArrayList<>();
		List<Location> locList = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader("admin.txt"))) {
			String read = null;
			// create new Location
			while ((read = br.readLine()) != null) {
				Location loc = new Location();
				EnergySource energy = new EnergySource();
				String[] readArray = read.split(",");
				admin.setAdminName(readArray[0]);
				admin.setAdminID(readArray[1]);
				loc.setAreaName(readArray[2]);
				loc.setZipcode(Integer.parseInt(readArray[3]));
				loc.setWeather(readArray[4]);
				energy.setId(readArray[5]);
				energy.setSourceName(readArray[6]);
				energy.setCapacity(readArray[7]);
				energyList.add(energy);
				loc.setEnergySource(energyList);
				locList.add(loc);
				admin.setLocation(locList);
				adminList.add(admin);
				printLogs3("Admin " + admin.getAdminName() + " data created from file");
			}
		} catch (IOException e) {
			printLogs1(e.getMessage());
		}

		ChargeStation charge1 = new ChargeStation();
		charge1.setLocations(adminList.get(0).getLocation());
		try {
			charge1.checkLocations(userInput.get(0));
		} catch (LocationNotAvailableException ex) {
			printLogs1(ex.getMessage());
		}

		Metadata meta = new Metadata();
		meta.writeMetatdata("AdminData.txt");
		meta.writeMetatdata("ChargeStationMainlogs.txt");
		meta.writeMetatdata("PriorityQueueLogs.txt");
		meta.writeMetatdata("UserData.txt");
		meta.writeMetatdata("LocationIsFull.txt");
		meta.writeMetatdata("CalcWaitTimeForCars.txt");
		meta.writeMetatdata("AdminActivity.txt");

		// To delete any log file - pass "yes" to admin method
		admin.deleteFile("No", "AdminData.txt");
		// Add Delete Energy Source
		EnergySource es = new EnergySource();
		es.setId("211");
		es.setSourceName("Hydro");
		es.setCapacity("100w");
		admin.addDeleteEnergySource(12345, es, "add", charge1);

	}

	public static void printLogs1(String msg) {

		try {
			fr1 = new BufferedWriter(new FileWriter("CarChargingStationMain.txt", true));
			fr1.write(msg);
			fr1.newLine();
			fr1.close();
		} catch (IOException e) {
			System.out.println("Error while writing to log file" + e.getMessage());
		}
	}

	public static void printLogs2(String msg) {

		try {
			fr1 = new BufferedWriter(new FileWriter("UserData.txt", true));
			fr1.write(msg);
			fr1.newLine();
			fr1.close();
		} catch (IOException e) {
			printLogs1("Error while writing to log file" + e.getMessage());
		}
	}

	public static void printLogs3(String msg) {

		try {
			fr1 = new BufferedWriter(new FileWriter("AdminData.txt", true));
			fr1.write(msg);
			fr1.newLine();
			fr1.close();
		} catch (IOException e) {
			printLogs1("Error while writing to log file" + e.getMessage());
		}
	}

}
