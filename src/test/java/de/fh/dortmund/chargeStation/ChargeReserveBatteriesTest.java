package test.java.de.fh.dortmund.chargeStation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.FutureTask;

import org.junit.Assert;
import org.junit.Test;

import main.java.de.fh.dortmund.chargeStation.ChargeReserveBatteries;
import main.java.de.fh.dortmund.model.EnergySource;
import main.java.de.fh.dortmund.model.Location;

public class ChargeReserveBatteriesTest {

	//This test method is used to test charge battery functionality it will return 1 if location has energy source object 
	@Test
	public void chargeBatteriesTest() {
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
		ChargeReserveBatteries cb = new ChargeReserveBatteries(loc);
		Assert.assertEquals(1, cb.chargeBatteries());
	}

	//This test method is used to test charge battery functionality it will return 0 if there are no energy sources for location
	@Test
	public void chargeBatteriesWhenNullTest() {
		Location loc = new Location();
		loc.setEnergySource(null);
		ChargeReserveBatteries cb = new ChargeReserveBatteries(loc);
		Assert.assertEquals(0, cb.chargeBatteries());
	}

	//This test method is used to test call method functionality it will return 1 if it has energy sources
	@Test
	public void callTest() {
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
		ChargeReserveBatteries cb = new ChargeReserveBatteries(loc);
		FutureTask<Integer> future = new FutureTask<Integer>(cb);
		Thread th1 = new Thread(future);
		th1.start();
		int result = 0;
		try {
			result = future.get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Assert.assertEquals(1, result);
	}

}
