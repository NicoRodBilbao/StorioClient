package dataAccess;

import entities.User;
import entities.UserPrivilege;
import entities.UserStatus;
import services.UserClient;
import interfaces.Userable;
import java.util.List;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author Joana
 */
public class UserImplementation implements Userable {

	public UserClient uc;

	public UserImplementation() {
		uc = new UserClient();
	}

	@Override
	public boolean loginUser(String login, String password) throws ClientErrorException {
		return uc.login(Boolean.class, login, password);
	}

	@Override
	public void editUser(User user) throws ClientErrorException {
		uc.edit_XML(user, user.getId().toString());
	}

	@Override
	public void removeUser(User user) throws ClientErrorException {
		uc.remove(user.getId().toString());
	}

	@Override
	public User findUserById(Integer id) throws ClientErrorException {
		return uc.find_XML(User.class, id.toString());
	}

	@Override
	public User findUserByLogin(String login) throws ClientErrorException {
		return uc.findByLogin_XML(User.class, login);
	}

	@Override
	public User findUserByEmail(String email) throws ClientErrorException {
		return uc.findByEmail_XML(User.class, email);
	}

	@Override
	public User findUserByPhone(Integer phoneNumber) throws ClientErrorException {
		return uc.findByPhone_XML(User.class, phoneNumber.toString());
	}

	@Override
	public List<User> findAllUsers() throws ClientErrorException {
		return uc.findAll_XML(new GenericType<List<User>>() {});
	}

	@Override
	public List<User> findUsersByPrivilege(UserPrivilege privilege) throws ClientErrorException {
		return uc.findUsersByPrivilege_XML(new GenericType<List<User>>() {}, privilege.toString());
	}

	@Override
	public List<User> findUsersByStatus(UserStatus status) throws ClientErrorException {
		return uc.findUsersByStatus_XML(new GenericType<List<User>>() {}, status.toString());
	}

	@Override
	public List<User> findUsersByFullName(String fullName) throws ClientErrorException {
		return uc.findUsersByFullName_XML(new GenericType<List<User>>() {}, fullName);
	}
	
}