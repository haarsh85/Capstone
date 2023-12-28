package test.java.de.fh.dortmund.chargeStation;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import org.junit.Test;
import main.java.de.fh.dortmund.chargeStation.ChargeStation;
import main.java.de.fh.dortmund.model.Car;
import main.java.de.fh.dortmund.model.EnergySource;
import main.java.de.fh.dortmund.model.Location;
import main.java.de.fh.dortmund.users.User;

public class ChargeStationTest {

	@Test
	public void runTest() {
		Location loc = new Location();
		loc.setAreaName("area1");
		loc.setWeather("sunny");
		loc.setZipcode(1234);
		EnergySource es = new EnergySource();
		es.setCapacity("10w");
		es.setId("1");
		es.setSourceName("solar");
		List<EnergySource> esList = new ArrayList<EnergySource>();
		esList.add(es);
		loc.setEnergySource(esList);
		List<Location> locList = new ArrayList<Location>();
		locList.add(loc);
		Car car = new Car();
		car.setBatteryLevel(10);
		car.setBrand("bmw");
		car.setModel("model");
		car.setNumber("test123");
		car.setOwnerName("test1");
		car.setBookedTimeSlot(LocalDateTime.now());
		BlockingQueue<User> userInput = new ArrayBlockingQueue<User>(1);
		User user = new User();
		user.setCar(car);
		userInput.add(user);
		ChargeStation cs = new ChargeStation(locList, userInput);
		Thread th = new Thread(cs);
		th.start();
		assertNotNull(cs.getLocations());
	}

	@Test
	public void checkwithinWaitTimeTest() {
		Location loc = new Location();
		loc.setAreaName("area1");
		loc.setWeather("sunny");
		loc.setZipcode(1234);
		EnergySource es = new EnergySource();
		es.setCapacity("10w");
		es.setId("1");
		es.setSourceName("solar");
		List<EnergySource> esList = new ArrayList<EnergySource>();
		esList.add(es);
		loc.setEnergySource(esList);
		List<Location> locList = new ArrayList<Location>();
		locList.add(loc);
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
		BlockingQueue<User> userInput = new ArrayBlockingQueue<User>(1);
		User user = new User();
		user.setCar(car1);
		userInput.add(user);
		ChargeStation cs = new ChargeStation(locList, userInput);
		Method method;
		Class<?>[] classArray = new Class<?>[2];
		classArray[0] = car.getClass();
		classArray[1] = car1.getClass();
		try {
			method = cs.getClass().getDeclaredMethod("checkwithinWaitTime", classArray);
			method.setAccessible(true);
			Object result = method.invoke(cs, car, car1);
			assertNotNull(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void checkCarWaitingList() {
		Location loc = new Location();
		loc.setAreaName("area1");
		loc.setWeather("sunny");
		loc.setZipcode(1234);
		EnergySource es = new EnergySource();
		es.setCapacity("10w");
		es.setId("1");
		es.setSourceName("solar");
		List<EnergySource> esList = new ArrayList<EnergySource>();
		esList.add(es);
		loc.setEnergySource(esList);
		List<Location> locList = new ArrayList<Location>();
		locList.add(loc);
		Car car = new Car();
		car.setBatteryLevel(10);
		car.setBrand("bmw");
		car.setModel("model");
		car.setNumber("test123");
		car.setOwnerName("test1");
		car.setBookedTimeSlot(LocalDateTime.now());
		BlockingQueue<User> userInput = new ArrayBlockingQueue<User>(1);
		User user = new User();
		user.setCar(car);
		userInput.add(user);
		ChargeStation cs = new ChargeStation(locList, userInput);
		Method method;
		try {
			method = cs.getClass().getDeclaredMethod("checkCarWaitingList", loc.getClass());
			method.setAccessible(true);
			Object result = method.invoke(cs, loc);
			assertNull(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
