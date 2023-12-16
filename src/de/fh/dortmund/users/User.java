package de.fh.dortmund.users;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import de.fh.dortmund.model.Car;

public class User {

	private Car car;

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public void readLogFile(String file) {
		try {
			FileInputStream fstream = new FileInputStream(file);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			String strLine;
			/* read log line by line */
			while ((strLine = br.readLine()) != null) {
				/* parse strLine to obtain what you want */
				System.out.println(strLine);
			}
			fstream.close();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

}
