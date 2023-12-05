package de.fh.dortmund.chargeStation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.BlockingQueue;

import de.fh.dortmund.exceptions.LocationNotAvailableException;
import de.fh.dortmund.model.Car;
import de.fh.dortmund.model.Location;
import de.fh.dortmund.model.WaitTimeData;
import de.fh.dortmund.users.User;

public class ChargeStation implements Runnable {

	private List<Location> locations;
	private BufferedWriter fr1;
	private BlockingQueue<User> user;

	@Override
	public void run() {
		try {
			while (user.size() != 0) {
				checkLocations(user.take());
			}
		} catch (LocationNotAvailableException | InterruptedException ex) {
			printLogs4(ex.getMessage());
		}

	}

	public ChargeStation(List<Location> locations, BlockingQueue<User> user) {
		this.locations = locations;
		this.user = user;
	}

	private synchronized void checkLocations(User user) throws LocationNotAvailableException {
		List<Location> locationList = getLocations();
		if (locationList != null) {
			checkCarWaitingList(locationList, user.getCar());
		} else {
			throw new LocationNotAvailableException("No Location is available for car charging");
		}

	}

	private synchronized void checkCarWaitingList(List<Location> locationList, Car car) {

		int locationTried = 0;
		boolean addedToQueue = false;
		for (Location loc : locationList) {
			locationTried++;
			PriorityQueue<Car> pqCar = loc.getPqCar();
			if (pqCar.isEmpty()) {
				printLogs1("Slot is available for the car " + car.getNumber()
						+ " and confirmed as there are no cars in the slot.");
				pqCar.add(car);
				printLogs1(car.getNumber() + " has received a charging slot successfully");
				addedToQueue = true;
				break;
			} else if (pqCar.size() == 1) {
				WaitTimeData withinTime = checkwithinWaitTime(pqCar.peek(), car.getBookedTimeSlot());
				if (withinTime.isWithinWaitTime()) {
					try {
						printLogs1(
								pqCar.peek().getNumber() + " is currently charging and is almost done, please wait...");
						Thread.sleep((withinTime.getWaitTime() * 3600));
					} catch (InterruptedException e) {
						printLogs4("System got interupted in between");
					}
					printLogs1((pqCar.poll()).getNumber() + " Car is charged and removed");
					printLogs1("Slot is confirmed for the car " + car.getNumber());
					pqCar.add(car);
					printLogs1(car.getNumber() + " has received a charging slot successfully");
					addedToQueue = true;
				} else {
					printLogs1(car.getNumber() + " High Waiting Time, please try another location");
					continue;
				}
			}
		}
		if (!addedToQueue && locationTried == locationList.size()) {
			WaitTimeData withinTime = checkwithinWaitTime(locationList.get(0).getPqCar().peek(),
					car.getBookedTimeSlot());
			try {
				printLogs2("Since slots in all locations are full, trying again. Please wait till slot is available.");
				Thread.sleep(withinTime.getWaitTime() * 3600);
				printLogs2(locationList.get(0).getPqCar().peek().getNumber()
						+ " is currently charging and is almost done, please wait...");
				locationList.get(0).getPqCar().poll();
				printLogs2("Slot confirmed for the car " + car.getNumber());
				locationList.get(0).getPqCar().add(car);
				printLogs2(car.getNumber() + " has received a charging slot successfully");
			} catch (InterruptedException e) {
				printLogs4("System got interupted in between");
			}

		}
	}

	private synchronized WaitTimeData checkwithinWaitTime(Car pqCar, LocalDateTime newBookingTime) {
		// Wait time is 15 minutes
		WaitTimeData waitTime = new WaitTimeData();
		long remainingTime = Duration.between(newBookingTime, pqCar.getApproximateTimeToGetCharged()).toMinutes();
		if (remainingTime < 0) {
			remainingTime = Duration.between(pqCar.getApproximateTimeToGetCharged(), newBookingTime).toMinutes();
		}
		printLogs3("Waiting Time for " + pqCar.getNumber() + " is " + remainingTime + " minutes.");
		waitTime.setWaitTime(remainingTime);
		if (remainingTime > 15) {
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

	public synchronized void printLogs1(String msg) {

		try {
			fr1 = new BufferedWriter(new FileWriter("PriorityQueueLogs.txt", true));
			fr1.write(msg);
			fr1.newLine();
			fr1.close();
		} catch (IOException e) {
			printLogs4("Error while writing to log file " + e.getMessage());
		}
	}

	public synchronized void printLogs2(String msg) {

		try {
			fr1 = new BufferedWriter(new FileWriter("LocationIsFull.txt", true));
			fr1.write(msg);
			fr1.newLine();
			fr1.close();
		} catch (IOException e) {
			printLogs4("Error while writing to log file " + e.getMessage());
		}
	}

	public synchronized void printLogs3(String msg) {

		try {
			fr1 = new BufferedWriter(new FileWriter("CalcWaitTimeForCars.txt", true));
			fr1.write(msg);
			fr1.newLine();
			fr1.close();
		} catch (IOException e) {
			printLogs4("Error while writing to log file " + e.getMessage());
		}
	}

	public synchronized void printLogs4(String msg) {

		try {
			fr1 = new BufferedWriter(new FileWriter("ChargeStation.txt", true));
			fr1.write(msg);
			fr1.newLine();
			fr1.close();
		} catch (IOException e) {
			System.out.println("Error while writing to log file " + e.getMessage());
		}
	}

}
