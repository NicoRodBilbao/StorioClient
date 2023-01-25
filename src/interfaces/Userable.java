package interfaces;

import entities.*;
import java.util.List;
import javax.ws.rs.ClientErrorException;

/**
 *
 * @author Joana
 */
public interface Userable {
	
	/**
	 * Returns OK if the login and password are correct
	 * @param login Username
	 * @param password PlainText Password
	 * @return OK only if the credentials are the correct ones
	 * @throws ClientErrorException if incorrect login credentials
	 */
	public boolean loginUser(String login, String password) throws ClientErrorException;

    /**
     * This method edits a user from the data store
     *
     * @param user The modified user
     * @throws ClientErrorException
     */
    public void editUser(User user) throws ClientErrorException;

    /**
     * This method removes a user from the data store
     *
     * @param user The user to be removed
     * @throws ClientErrorException
     */
    public void removeUser(User user) throws ClientErrorException;

    /**
     * This method returns a user whose id matches the given parameter
     *
     * @param id The user to be added
     * @return The matching user, if found
     * @throws ClientErrorException
     */
    public User findUserById(Integer id) throws ClientErrorException;

    /**
     * This method finds a user with a matching username
     *
     * @param login The username to search for
     * @return The matching user, if found
     * @throws ClientErrorException
     */
    public User findUserByLogin(String login) throws ClientErrorException;

    /**
     * This method finds a user with a matching email
     *
     * @param email The email to search for
     * @return The matching user, if found
     * @throws ClientErrorException
     */
    public User findUserByEmail(String email) throws ClientErrorException;

    /**
     * This method finds a user with a matching phone number
     *
     * @param phoneNumber
     * @return The matching user, if found
     * @throws ClientErrorException
     */
    public User findUserByPhone(Integer phoneNumber) throws ClientErrorException;

    /**
     * This method returns all users from the data store
     *
     * @return A list containing all the users
     * @throws ClientErrorException
     */
    public List<User> findAllUsers() throws ClientErrorException;

    /**
     * This method returns all users of a certain type
     *
     * @param privilege The privilege level to filter users
     * @return A list of users that are of the requested type
     * @throws ClientErrorException
     */
    public List<User> findUsersByPrivilege(UserPrivilege privilege) throws ClientErrorException;

    /**
     * This method returns all users matching the given status
     *
     * @param status The status by which to filter the users
     * @return A list containing all the users of the given status
     * @throws ClientErrorException
     */
    public List<User> findUsersByStatus(UserStatus status) throws ClientErrorException;

    /**
     * Returns a list of users matching a string
     *
     * @param fullName A string to filter the users
     * @return A list containing all the users that match the string
     * @throws ClientErrorException
     */
    public List<User> findUsersByFullName(String fullName) throws ClientErrorException;

}
