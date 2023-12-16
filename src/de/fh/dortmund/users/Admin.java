package de.fh.dortmund.users;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import de.fh.dortmund.ManageFiles.ManageFiles;
import de.fh.dortmund.chargeStation.ChargeStation;
import de.fh.dortmund.model.EnergySource;
import de.fh.dortmund.model.Location;

public class Admin {

	private String adminName;
	private String adminID;
	private List<Location> location;
	private BufferedWriter fr1;
	private String logFilePattern = ".*\\.txt";
	String logDirectoryPath = "../Capstone";

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

	public void addDeleteEnergySource(int zipcode, EnergySource energy, String input, ChargeStation charge) {
		List<Location> locList = charge.getLocations();
		if ("Add".equalsIgnoreCase(input)) {
			for (Location loc : locList) {
				if (zipcode == loc.getZipcode()) {
					List<EnergySource> energyList = loc.getEnergySource();
					energyList.add(energy);
					loc.setEnergySource(energyList);
					printLogs1("Admin added new energy source for zipcode " + zipcode);
				}
			}
		} else if ("Delete".equalsIgnoreCase(input)) {
			for (Location loc : locList) {
				if (zipcode == loc.getZipcode()) {
					List<EnergySource> energyList = loc.getEnergySource();
					try {
						energyList.removeIf(obj -> energy.getSourceName().equalsIgnoreCase(energy.getSourceName()));
					} catch (NullPointerException ex) {
						printLogs1(energy.getSourceName()
								+ "Energy Source is not present in energy source List, hence can not be deleted for zipcode"
								+ zipcode);
					}

				}
			}
		}
	}

	public void archieveLogFiles() {
		Pattern pattern = Pattern.compile(logFilePattern);

		// List all files in the log directory
		File logDirectory = new File(logDirectoryPath);
		File[] logFiles = logDirectory.listFiles();

		if (logFiles != null) {
			for (File file : logFiles) {
				// Check if the file matches the regex pattern
				if (pattern.matcher(file.getName()).matches()) {
					zipFile("yes", file.getName());
				}
			}
			
			for(File file : logFiles) {
				if (pattern.matcher(file.getName()).matches()) {
					deleteFile("Yes", file.getName());
				}
			}
		}
	}

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

}
