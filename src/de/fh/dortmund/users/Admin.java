package de.fh.dortmund.users;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import de.fh.dortmund.ManageFiles.ManageFiles;
import de.fh.dortmund.model.EnergySource;
import de.fh.dortmund.model.Location;

public class Admin {

	private String adminName;
	private String adminID;
	private List<Location> location;
	private List<EnergySource> energy;
	private BufferedWriter fr1;

	public void deleteFile(String input, String file) {
		if ("Yes".equalsIgnoreCase(input)) {
			ManageFiles manageFile = new ManageFiles();
			manageFile.deleteFile(file);
			printLogs1("Admin deleted " + file);
		}
	}

	public void movedFile(String input, String file, String newPath) {
		if ("Yes".equalsIgnoreCase(input)) {
			ManageFiles manageFile = new ManageFiles();
			manageFile.moveFile(file, newPath);
			printLogs1("Admin moved " + file + "to new path: " + newPath);
		}
	}

	public void zipFile(String input, String file) {
		if ("Yes".equalsIgnoreCase(input)) {
			ManageFiles manageFile = new ManageFiles();
			manageFile.archieve(file);
			printLogs1("Admin archieved " + file);
		}
	}

	/* public void addDeleteEnergySource(int zipcode, EnergySource energy, String input) {
		if ("Add".equalsIgnoreCase(input)) {
			for (Location loc : getLocation()) {
				if (zipcode == loc.getZipcode()) {
					List<EnergySource> energyList = loc.getEnergySource();
					energyList.add(energy);
					loc.setEnergySource(energyList);
				}
			}
		}
		else if ("Delete".equalsIgnoreCase(input)) {
			for(Location loc: getLocation()) {
				
			}
		}
	} */

	public void printLogs1(String msg) {

		try {
			fr1 = new BufferedWriter(new FileWriter("AdminActivity.txt", true));
			fr1.write(msg);
			fr1.newLine();
			fr1.close();
		} catch (IOException e) {
			printLogs1("Error while writing to log file" + e.getMessage());
		}
	}

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
