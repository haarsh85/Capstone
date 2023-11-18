package de.fh.dortmund.model;

import java.time.LocalDateTime;

public class Car {

	private String number;
	private String ownerName;
	private String brand;
	private int batteryLevel;
	private LocalDateTime bookedTimeSlot;
	private LocalDateTime finishTime;

	public Car(String number, String ownerName, String brand, int batteryLevel, LocalDateTime bookedTimeSlot) {
		this.number = ownerName;
		this.ownerName = ownerName;
		this.brand = brand;
		this.batteryLevel = batteryLevel;
		this.bookedTimeSlot = bookedTimeSlot;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getBrand() {
		return brand;
	}

	public void setModel(String model) {
		this.brand = model;
	}

	public int getBatteryLevel() {
		return batteryLevel;
	}

	public LocalDateTime getBookedTimeSlot() {
		return bookedTimeSlot;
	}

	public LocalDateTime getApproximateTimeToGetCharged() {
		if (getBatteryLevel() <= 10) {
			finishTime = (getBookedTimeSlot().plusMinutes(10));
		} else if (getBatteryLevel() > 10 && getBatteryLevel() <= 50) {
			finishTime = (getBookedTimeSlot().plusMinutes(5));
		} else {
			finishTime = (getBookedTimeSlot().plusMinutes(1));
		}

		return finishTime;
	}

}
