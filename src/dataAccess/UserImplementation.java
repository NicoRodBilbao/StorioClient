package dataAccess;

import entities.Admin;
import entities.Client;
import entities.User;
import entities.UserPrivilege;
import entities.UserStatus;
import exceptions.UserManagerException;
import services.UserClient;
import interfaces.Userable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author Joana
 */
public class UserImplementation implements Userable {

	protected static final Logger LOGGER = Logger.getLogger(UserImplementation.class.getName());

	public UserClient uc;

	/**
	 *
	 */
	public UserImplementation() {
		uc = new UserClient();
	}

	@Override
	public void registerClient(Client user) throws UserManagerException {
		try {
			uc.create_XML(user);
		} catch (ClientErrorException ce) {
			LOGGER.severe(ce.getMessage());
			throw new UserManagerException("Error registering user");
		}
	}

	@Override
	public void registerAdmin(Admin user) throws UserManagerException {
		try {
			uc.create_XML(user);
		} catch (ClientErrorException ce) {
			LOGGER.severe(ce.getMessage());
			throw new UserManagerException("Error registering user");
		}
	}

	@Override
	public boolean loginUser(String login, String password) throws UserManagerException {
		try {
			LOGGER.log(Level.INFO, "Login in user {0}", login);
			return uc.login(String.class, login, password);
		} catch (ClientErrorException ce) {
			LOGGER.severe(ce.getMessage());
			throw new UserManagerException("Error Login in");
		}
	}

	@Override
	public void editUser(User user) throws UserManagerException {
		uc.edit_XML(user, user.getId().toString());
	}

	@Override
	public void removeUser(User user) throws UserManagerException {
		uc.remove(user.getId().toString());
	}

	@Override
	public User findUserById(Integer id) throws UserManagerException {
		System.out.println("GET user by id");
		User user = uc.find_XML(User.class, id.toString());
		System.out.println(user);
		return user;
	}

	@Override
	public Client findClientByLogin(String login) throws UserManagerException {
		try {
			Client user = uc.findByLogin_XML(Client.class, login);
			return user;
		} catch (ClientErrorException ce) {
			LOGGER.severe(ce.getMessage());
			throw new UserManagerException("Could not find user");
		}
	}

	@Override
	public User findUserByEmail(String email) throws UserManagerException {
		return uc.findByEmail_XML(User.class, email);
	}

	@Override
	public User findUserByPhone(Integer phoneNumber) throws UserManagerException {
		return uc.findByPhone_XML(User.class, phoneNumber.toString());
	}

	@Override
	public List<User> findAllUsers() throws UserManagerException {
		List<User> users = null;
		try {
			users = uc.findAll_XML(new GenericType<List<User>>() {
			});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return users;
	}

	@Override
	public List<User> findUsersByPrivilege(UserPrivilege privilege) throws UserManagerException {
		return uc.findUsersByPrivilege_XML(new GenericType<List<User>>() {
		}, privilege.toString());
	}

	@Override
	public List<User> findUsersByStatus(UserStatus status) throws UserManagerException {
		return uc.findUsersByStatus_XML(new GenericType<List<User>>() {
		}, status.toString());
	}

	@Override
	public List<User> findUsersByFullName(String fullName) throws UserManagerException {
		return uc.findUsersByFullName_XML(new GenericType<List<User>>() {
		}, fullName);
	}

}
