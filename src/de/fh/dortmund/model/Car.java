package de.fh.dortmund.model;

import java.time.LocalDateTime;

public class Car {

	private String number;
	private String ownerName;
	private String brand;
	private int batteryLevel;
	private LocalDateTime bookedTimeSlot;
	private LocalDateTime finishTime;

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
			finishTime = (getBookedTimeSlot().plusMinutes(20));
		} else if (getBatteryLevel() > 10 && getBatteryLevel() <= 50) {
			finishTime = (getBookedTimeSlot().plusMinutes(10));
		} else {
			finishTime = (getBookedTimeSlot().plusMinutes(5));
		}

		return finishTime;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public void setBatteryLevel(int batteryLevel) {
		this.batteryLevel = batteryLevel;
	}

	public void setBookedTimeSlot(LocalDateTime bookedTimeSlot) {
		this.bookedTimeSlot = bookedTimeSlot;
	}

	public void setFinishTime(LocalDateTime finishTime) {
		this.finishTime = finishTime;
	}

}
