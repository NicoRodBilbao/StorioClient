package services;

import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

/**
 * Jersey REST client generated for REST resource:UserFacadeREST
 * [entities.user]<br>
 * USAGE:
 * <pre>
 *        UserClient client = new UserClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author Joana
 */
public class UserClient {

	private WebTarget webTarget;
	private Client client;
	private static final String BASE_URI = ResourceBundle.getBundle("services.config").getString("URL");// Clase
                  //private static final String BASE_URI = "http://localhost:30753/StorioWeb/webresources";// Casa

	protected static final Logger LOGGER = Logger.getLogger(UserClient.class.getName());

	public UserClient() {
		client = javax.ws.rs.client.ClientBuilder.newClient();
		webTarget = client.target(BASE_URI).path("entities.user");
	}

	public <T> T resetPassword(Class<T> responseType, String email) throws ClientErrorException {
		WebTarget resource = webTarget;
		return resource
				.path(java.text.MessageFormat.format("mail/resetPassword/{0}", new Object[]{email}))
				.request(javax.ws.rs.core.MediaType.APPLICATION_XML)
				.get(responseType);
	}

	public <T> T findUsersByStatus_XML(GenericType<T> responseType, String status) throws ClientErrorException {
		WebTarget resource = webTarget;
		resource = resource.path(java.text.MessageFormat.format("status/{0}", new Object[]{status}));
		return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
	}

	public <T> T findUsersByStatus_JSON(GenericType<T> responseType, String status) throws ClientErrorException {
		WebTarget resource = webTarget;
		resource = resource.path(java.text.MessageFormat.format("status/{0}", new Object[]{status}));
		return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
	}

	public Response edit_XML(Object requestEntity, String id) throws ClientErrorException {
		return webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), Response.class);
	}

	public Response edit_JSON(Object requestEntity, String id) throws ClientErrorException {
		return webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request(javax.ws.rs.core.MediaType.APPLICATION_JSON).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), Response.class);
	}

	public <T> T findUsersByFullName_XML(GenericType<T> responseType, String fullName) throws ClientErrorException {
		WebTarget resource = webTarget;
		resource = resource.path(java.text.MessageFormat.format("fullName/{0}", new Object[]{fullName}));
		return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
	}

	public <T> T findUsersByFullName_JSON(GenericType<T> responseType, String fullName) throws ClientErrorException {
		WebTarget resource = webTarget;
		resource = resource.path(java.text.MessageFormat.format("fullName/{0}", new Object[]{fullName}));
		return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
	}

	public <T> boolean login(Class<T> responseType, String login, String password) throws ClientErrorException {
		WebTarget resource = webTarget;
		try {
			Object response = resource
					.path(java.text.MessageFormat.format("login/{0}/{1}", new Object[]{login, password}))
					.request(javax.ws.rs.core.MediaType.APPLICATION_XML)
					.get(responseType);
		return (response.toString().equals(""));
		} catch (InternalServerErrorException ise) {
			return false;
		}
	}

	public <T> T findUsersByPrivilege_XML(GenericType<T> responseType, String privilege) throws ClientErrorException {
		WebTarget resource = webTarget;
		resource = resource.path(java.text.MessageFormat.format("privilege/{0}", new Object[]{privilege}));
		return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
	}

	public <T> T findUsersByPrivilege_JSON(GenericType<T> responseType, String privilege) throws ClientErrorException {
		WebTarget resource = webTarget;
		resource = resource.path(java.text.MessageFormat.format("privilege/{0}", new Object[]{privilege}));
		return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
	}

	public <T> T findByLogin_XML(Class<T> responseType, String login) throws ClientErrorException {
		WebTarget resource = webTarget;
		resource = resource.path(java.text.MessageFormat.format("login/{0}", new Object[]{login}));
		return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
	}

	public <T> T findByLogin_JSON(Class<T> responseType, String login) throws ClientErrorException {
		WebTarget resource = webTarget;
		resource = resource.path(java.text.MessageFormat.format("login/{0}", new Object[]{login}));
		return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
	}

	public <T> T findAll_XML(GenericType<T> responseType) throws ClientErrorException {
		WebTarget resource = webTarget;
		return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
	}

	public <T> T findAll_JSON(GenericType<T> responseType) throws ClientErrorException {
		WebTarget resource = webTarget;
		return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
	}

	public Response remove(String id) throws ClientErrorException {
		return webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete(Response.class);
	}

	public <T> T sendEmail(Class<T> responseType, String email) throws ClientErrorException {
		WebTarget resource = webTarget;
		return resource
				.path(java.text.MessageFormat.format("mail/sendMail/{0}", new Object[]{email}))
				.request(javax.ws.rs.core.MediaType.APPLICATION_XML)
				.get(responseType);
	}

	public String countREST() throws ClientErrorException {
		WebTarget resource = webTarget;
		resource = resource.path("count");
		return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
	}

	public <T> T find_XML(Class<T> responseType, String id) throws ClientErrorException {
		WebTarget resource = webTarget;
		resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
		return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
	}

	public <T> T find_JSON(Class<T> responseType, String id) throws ClientErrorException {
		WebTarget resource = webTarget;
		resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
		return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
	}

	public <T> T findByPhone_XML(Class<T> responseType, String phone) throws ClientErrorException {
		WebTarget resource = webTarget;
		resource = resource.path(java.text.MessageFormat.format("phone/{0}", new Object[]{phone}));
		return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
	}

	public <T> T findByPhone_JSON(Class<T> responseType, String phone) throws ClientErrorException {
		WebTarget resource = webTarget;
		resource = resource.path(java.text.MessageFormat.format("phone/{0}", new Object[]{phone}));
		return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
	}

	public Response create_XML(Object requestEntity) throws ClientErrorException {
		return webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), Response.class);
	}

	public Response create_JSON(Object requestEntity) throws ClientErrorException {
		return webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), Response.class);
	}

	public <T> T findByEmail_XML(Class<T> responseType, String email) throws ClientErrorException {
		WebTarget resource = webTarget;
		resource = resource.path(java.text.MessageFormat.format("email/{0}", new Object[]{email}));
		return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
	}

	public <T> T findByEmail_JSON(Class<T> responseType, String email) throws ClientErrorException {
		WebTarget resource = webTarget;
		resource = resource.path(java.text.MessageFormat.format("email/{0}", new Object[]{email}));
		return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
	}

	public void close() {
		client.close();
	}
	
}
