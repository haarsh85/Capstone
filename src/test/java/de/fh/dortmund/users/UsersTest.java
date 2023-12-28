package test.java.de.fh.dortmund.users;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import main.java.de.fh.dortmund.users.User;

public class UsersTest {

	@Test
	public void readLofFilesTest() {
		User user = new User();
		user.readLogFile("EnergySource.log");
		assertNotNull(user);
		assertNull(user.getCar());
	}

}
