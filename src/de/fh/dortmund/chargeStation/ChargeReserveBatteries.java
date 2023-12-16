package de.fh.dortmund.chargeStation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.Callable;

import de.fh.dortmund.model.EnergySource;
import de.fh.dortmund.model.Location;

public class ChargeReserveBatteries implements Callable<Integer> {

	private BufferedWriter fr1;
	private Location loc = null;

	public ChargeReserveBatteries(Location loc) {
		this.loc = loc;
	}

	public synchronized int chargeBatteries() {

		for (EnergySource src : loc.getEnergySource()) {
			printLogs("Location " + loc.getAreaName() + " reserved battery is being charged with energy source "
					+ src.getSourceName(), "ChargeReservedBatteries.txt");
			printLogs("Location " + loc.getAreaName() + " available energy source -  "
					+ src.getSourceName(), "EnergySource.txt");
		}
		return 1;
	}

	public synchronized void printLogs(String msg, String file) {

		try {
			fr1 = new BufferedWriter(new FileWriter(file, true));
			fr1.write(msg);
			fr1.newLine();
			fr1.close();
		} catch (IOException e) {
			System.out.println("Error while writing to log file " + e.getMessage());
		}
	}

	@Override
	public Integer call() throws Exception {

		return chargeBatteries();
	}

}
