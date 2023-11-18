package de.fh.dortmund.model;

import java.time.LocalDateTime;
import java.util.*;

public class Location {

	private String areaName;
	private int zipcode;
	private List<Car> car;
	private LocalDateTime bookSlotTime;

	public Location(String areaName, int zipcode, List<Car> car) {
		this.areaName = areaName;
		this.zipcode = zipcode;
		this.car = car;
	}

	public String getAreaName() {
		return areaName;
	}

	public int getZipcode() {
		return zipcode;
	}

	public List<Car> getCar() {
		return car;
	}

	public LocalDateTime getbookSlotTime() {
//		bookSlotTime = LocalDateTime.now();
		bookSlotTime = LocalDateTime.of(2023, 11, 18, 10, 30);
		return bookSlotTime;
	}

}
