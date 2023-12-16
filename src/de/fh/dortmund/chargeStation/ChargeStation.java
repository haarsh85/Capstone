package de.fh.dortmund.chargeStation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
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
	private WaitTimeData withinTime;
	private BlockingQueue<Car> userCar = new ArrayBlockingQueue<Car>(10);

	@Override
	public void run() {
		try {
			while (user.size() != 0) {
				userCar.add(user.take().getCar());
			}
			checkLocations();
		} catch (LocationNotAvailableException | InterruptedException ex) {
			printLogs(ex.getMessage(), "ChargeStation.txt");
		}

	}

	public ChargeStation(List<Location> locations, BlockingQueue<User> user) {
		this.locations = locations;
		this.user = user;
	}

	private synchronized void checkLocations() throws LocationNotAvailableException {
		List<Location> locationList = getLocations();
		if (locationList != null) {
			for (Location loc : locationList) {
				checkCarWaitingList(loc);
			}
		} else {
			throw new LocationNotAvailableException("No Location is available for car charging");
		}

	}

	private synchronized void checkCarWaitingList(Location location) {

		try {
			Car car = null;
			PriorityQueue<Car> pqCar = location.getPqCar();
			while (userCar.size() > 0) {
				car = userCar.peek();
				if (pqCar.size() == 0) {
					printLogs("Slot is available for the car " + car.getNumber(), "PriorityQueueLogs.txt");
					pqCar.add(userCar.take());
					printLogs(car.getNumber() + " has received a charging slot successfully", "PriorityQueueLogs.txt");
				} else {
					withinTime = checkwithinWaitTime(pqCar.peek(), car);
					if (withinTime.isWithinWaitTime()) {
						printLogs(
								pqCar.peek().getNumber() + " is currently charging and is almost done, please wait...",
								"PriorityQueueLogs.txt");
						Thread.sleep((withinTime.getWaitTime()));
						printLogs((pqCar.poll()).getNumber() + " Car is charged and removed", "PriorityQueueLogs.txt");
					} else {
						printLogs(
								car.getNumber() + " High Waiting Time, please try another location. Leaving the Queue.",
								"PriorityQueueLogs.txt");
						userCar.take();
						continue;
					}
				}
			}
		} catch (Exception e) {
			printLogs("System got interupted in between", "ChargeStation.txt");
		}
	}

	private synchronized WaitTimeData checkwithinWaitTime(Car pqCar, Car car) {
		// Wait time is 15 minutes
		WaitTimeData waitTime = new WaitTimeData();
		long remainingTime = Duration.between(car.getBookedTimeSlot(), pqCar.getApproximateTimeToGetCharged())
				.toMinutes();
		if (remainingTime < 0) {
			remainingTime = Duration.between(pqCar.getApproximateTimeToGetCharged(), car.getBookedTimeSlot())
					.toMinutes();
		}
		printLogs("Waiting Time for " + car.getNumber() + " is " + remainingTime + " minutes.",
				"CalcWaitTimeForCars.txt");
		waitTime.setWaitTime(remainingTime);
		if (remainingTime > 15) {
			waitTime.setWithinWaitTime(false);
		} else {
			waitTime.setWithinWaitTime(true);
		}
		return waitTime;
	}

	public List<Location> getLocations() {
		return locations;
	}

	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}

	public synchronized void printLogs(String msg, String fileName) {

		try {
			fr1 = new BufferedWriter(new FileWriter(fileName, true));
			fr1.write(msg);
			fr1.newLine();
			fr1.close();
		} catch (IOException e) {
			System.out.println("Error while writing to log file " + e.getMessage());
		}
	}

}
