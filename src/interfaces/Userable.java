package interfaces;

import entities.*;
import exceptions.UserManagerException;
import java.util.List;

/**
 *
 * @author Joana
 */
public interface Userable {

	/**
	 * Creates a user in the data store
	 * 
	 * @param user The user to be added
	 * @throws UserManagerException 
	 */
	public void registerUser(User user) throws UserManagerException;

	/**
	 * Returns true if the login and password are correct
	 *
	 * @param login Username
	 * @param password PlainText Password
	 * @return OK only if the credentials are the correct ones
	 * @throws UserManagerException if incorrect login credentials
	 */
	public boolean loginUser(String login, String password) throws UserManagerException;

	/**
	 * This method edits a user from the data store
	 *
	 * @param user The modified user
	 * @throws UserManagerException
	 */
	public void editUser(User user) throws UserManagerException;

	/**
	 * This method removes a user from the data store
	 *
	 * @param user The user to be removed
	 * @throws UserManagerException
	 */
	public void removeUser(User user) throws UserManagerException;

	/**
	 * This method returns a user whose id matches the given parameter
	 *
	 * @param id The user to be added
	 * @return The matching user, if found
	 * @throws UserManagerException
	 */
	public User findUserById(Integer id) throws UserManagerException;

	/**
	 * This method finds a user with a matching username
	 *
	 * @param login The username to search for
	 * @return The matching user, if found
	 * @throws UserManagerException
	 */
	public User findUserByLogin(String login) throws UserManagerException;

	/**
	 * This method finds a user with a matching email
	 *
	 * @param email The email to search for
	 * @return The matching user, if found
	 * @throws UserManagerException
	 */
	public User findUserByEmail(String email) throws UserManagerException;

	/**
	 * This method finds a user with a matching phone number
	 *
	 * @param phoneNumber
	 * @return The matching user, if found
	 * @throws UserManagerException
	 */
	public User findUserByPhone(Integer phoneNumber) throws UserManagerException;

	/**
	 * This method returns all users from the data store
	 *
	 * @return A list containing all the users
	 * @throws UserManagerException
	 */
	public List<User> findAllUsers() throws UserManagerException;

	/**
	 * This method returns all users of a certain type
	 *
	 * @param privilege The privilege level to filter users
	 * @return A list of users that are of the requested type
	 * @throws UserManagerException
	 */
	public List<User> findUsersByPrivilege(UserPrivilege privilege) throws UserManagerException;

	/**
	 * This method returns all users matching the given status
	 *
	 * @param status The status by which to filter the users
	 * @return A list containing all the users of the given status
	 * @throws UserManagerException
	 */
	public List<User> findUsersByStatus(UserStatus status) throws UserManagerException;

	/**
	 * Returns a list of users matching a string
	 *
	 * @param fullName A string to filter the users
	 * @return A list containing all the users that match the string
	 * @throws UserManagerException
	 */
	public List<User> findUsersByFullName(String fullName) throws UserManagerException;

}
