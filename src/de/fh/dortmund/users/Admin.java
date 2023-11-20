package de.fh.dortmund.users;

import java.util.List;

import de.fh.dortmund.model.EnergySource;
import de.fh.dortmund.model.Location;

public class Admin {

	private String adminName;
	private String adminID;
	private List<Location> location;
	private List<EnergySource> energy;

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getAdminID() {
		return adminID;
	}

	public void setAdminID(String adminID) {
		this.adminID = adminID;
	}

	public List<Location> getLocation() {
		return location;
	}

	public void setLocation(List<Location> location) {
		this.location = location;
	}

	public List<EnergySource> getEnergy() {
		return energy;
	}

	public void setEnergy(List<EnergySource> energy) {
		this.energy = energy;
	}

}
