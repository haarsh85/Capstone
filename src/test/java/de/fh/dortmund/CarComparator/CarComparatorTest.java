package test.java.de.fh.dortmund.CarComparator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.Test;

import main.java.de.fh.dortmund.comparator.CarComparator;
import main.java.de.fh.dortmund.model.Car;

public class CarComparatorTest {

	@Test
	public void compareTest1() {

		Car car = new Car();
		car.setBatteryLevel(10);
		car.setBrand("bmw");
		car.setModel("model");
		car.setNumber("test123");
		car.setOwnerName("test1");
		car.setBookedTimeSlot(LocalDateTime.now());
		Car car1 = new Car();
		car1.setBatteryLevel(11);
		car1.setBrand("bmw");
		car1.setModel("model");
		car1.setNumber("test123");
		car1.setOwnerName("test1");
		car1.setBookedTimeSlot(LocalDateTime.now().plusMinutes(5));
		CarComparator cc = new CarComparator();
		assertEquals(1, cc.compare(car, car1));
	}

	@Test
	public void compareTest2() {

		Car car = new Car();
		car.setBatteryLevel(10);
		car.setBrand("bmw");
		car.setModel("model");
		car.setNumber("test123");
		car.setOwnerName("test1");
		car.setBookedTimeSlot(LocalDateTime.now());
		Car car1 = new Car();
		car1.setBatteryLevel(9);
		car1.setBrand("bmw");
		car1.setModel("model");
		car1.setNumber("test123");
		car1.setOwnerName("test1");
		car1.setBookedTimeSlot(LocalDateTime.now().plusMinutes(5));
		CarComparator cc = new CarComparator();
		assertEquals(-1, cc.compare(car, car1));
	}

	@Test
	public void compareTest3() {

		Car car = new Car();
		car.setBatteryLevel(10);
		car.setBrand("bmw");
		car.setModel("model");
		car.setNumber("test123");
		car.setOwnerName("test1");
		car.setBookedTimeSlot(LocalDateTime.now());
		Car car1 = new Car();
		car1.setBatteryLevel(10);
		car1.setBrand("bmw");
		car1.setModel("model");
		car1.setNumber("test123");
		car1.setOwnerName("test1");
		car1.setBookedTimeSlot(LocalDateTime.now().plusMinutes(5));
		CarComparator cc = new CarComparator();
		assertEquals(0, cc.compare(car, car1));
	}

}
