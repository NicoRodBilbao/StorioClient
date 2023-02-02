package factories;

import interfaces.Userable;
import dataAccess.UserImplementation;

/**
 *
 * @author Joana
 */
public class UserFactory {
	private static Userable user = new UserImplementation();

	public static Userable getAccessUser() {
		return user;
	}
	
}
