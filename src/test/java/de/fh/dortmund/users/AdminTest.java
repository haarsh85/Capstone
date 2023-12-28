package test.java.de.fh.dortmund.users;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import main.java.de.fh.dortmund.users.Admin;

public class AdminTest {

	@Test
	public void deleteFileTest() {
		Admin adm = new Admin();
		adm.deleteFile("no", "EnergySource.log");
		assertNotNull(adm);
	}

	@Test
	public void zipFileTest() {
		Admin adm = new Admin();
		adm.zipFile("yes", "EnergySource.log");
		assertNotNull(adm);
	}

	@Test
	public void moveFileTest() {
		Admin adm = new Admin();
		adm.zipFile("no", "EnergySource.log");
		assertNotNull(adm);
	}

}
