package main.java.de.fh.dortmund.comparator;

import java.util.Comparator;

import main.java.de.fh.dortmund.model.Car;

public class CarComparator implements Comparator<Car> {

	@Override
	public int compare(Car car1, Car car2) {
		if (car1.getBatteryLevel() < car2.getBatteryLevel())
			return 1;
		else if (car1.getBatteryLevel() > car2.getBatteryLevel())
			return -1;
		return 0;
	}

}
