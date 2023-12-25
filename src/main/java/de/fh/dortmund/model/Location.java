package main.java.de.fh.dortmund.model;

import java.util.*;

import main.java.de.fh.dortmund.comparator.CarComparator;

public class Location {

	private String areaName;
	private int zipcode;
	private List<EnergySource> energySource;
	private String weather;
	private PriorityQueue<Car> pqCar = new PriorityQueue<Car>(1, new CarComparator());

	public String getAreaName() {
		return areaName;
	}

	public int getZipcode() {
		return zipcode;
	}

	public List<EnergySource> getEnergySource() {
		return energySource;
	}

	public String getWeather() {
		return weather;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public void setZipcode(int zipcode) {
		this.zipcode = zipcode;
	}

	public void setEnergySource(List<EnergySource> energySource) {
		this.energySource = energySource;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	public PriorityQueue<Car> getPqCar() {
		return pqCar;
	}

}
