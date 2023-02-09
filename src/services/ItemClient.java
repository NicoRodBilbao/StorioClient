package services;

import entities.Item;
import exceptions.ItemCreateException;
import exceptions.ItemDeleteException;
import exceptions.ItemFindException;
import exceptions.ItemModifyException;
import java.util.ResourceBundle;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 * Jersey REST client generated for REST resource:ItemFacadeREST
 * [entities.item]<br>
 * USAGE:
 * <pre>
 *        ItemClient client = new ItemClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author Nicolás Rodríguez
 */
public class ItemClient {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = ResourceBundle.getBundle("services.config").getString("URL");// Clase
    //private static final String BASE_URI = "http://localhost:30753/StorioWeb/webresources";// Casa

    public ItemClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("entities.item");
    }

    public void removeItem(String id) throws ItemDeleteException {
        try {
            webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete();
        } catch (Exception e) {
            throw new ItemDeleteException();
        }
    }

    public <T> T findAllItemsWIthoutPack_XML(Class<T> responseType) throws ItemFindException {
        try {
            WebTarget resource = webTarget;
            resource = resource.path("AllItemsWithoutPack");
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
        } catch (Exception e) {
            throw new ItemFindException();
        }
    }

    public <T> T findAllItemsWIthoutPack_JSON(Class<T> responseType) throws ItemFindException {
        try {
            WebTarget resource = webTarget;
            resource = resource.path("AllItemsWithoutPack");
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
        } catch (Exception e) {
            throw new ItemFindException();
        }

    }

    public <T> T countREST(Class<T> responseType) throws ItemFindException {
        try {
            WebTarget resource = webTarget;
            resource = resource.path("count");
            return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(responseType);
        } catch (Exception e) {
            throw new ItemFindException();
        }
    }

    public <T> T find_XML(Class<T> responseType, String id) throws ItemFindException {
        try {
            WebTarget resource = webTarget;
            resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
        } catch (Exception e) {
            throw new ItemFindException();
        }
    }

    public <T> T find_JSON(Class<T> responseType, String id) throws ItemFindException {
        try {
            WebTarget resource = webTarget;
            resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
        } catch (Exception e) {
            throw new ItemFindException();
        }

    }

    public void createItem_XML(Item requestEntity) throws ItemCreateException {
        try {
            webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML),Item.class);
        } catch (Exception e) {
            throw new ItemCreateException();
        }
    }

    public void createItem_JSON(Object requestEntity) throws ItemCreateException {
        try {
            webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
        } catch (Exception e) {
            throw new ItemCreateException();
        }
        
    }

    public <T> T findAllPacksItems_XML(GenericType<T> responseType, String id) throws ItemFindException {
        try {
            WebTarget resource = webTarget;
            resource = resource.path(java.text.MessageFormat.format("{0}/AllPacksItems", new Object[]{id}));
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
        } catch (Exception e) {
            throw new ItemFindException();
        }
    }

    public <T> T findAllPacksItems_JSON(Class<T> responseType, String id) throws ItemFindException {
        try {
            WebTarget resource = webTarget;
            resource = resource.path(java.text.MessageFormat.format("{0}/AllPacksItems", new Object[]{id}));
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
        } catch (Exception e) {
            throw new ItemFindException();
        }
    }

    public <T> T findAllItems_XML(GenericType<T> responseType) throws ItemFindException {
        try {
            WebTarget resource = webTarget;
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
        } catch (Exception e) {
            throw new ItemFindException();
        }
    }

    public <T> T findAllItems_JSON(Class<T> responseType) throws ItemFindException {
        try {
            WebTarget resource = webTarget;
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
        } catch (Exception e) {
            throw new ItemFindException();
        }
    }

    public <T> T findAllModelsItems_XML(GenericType<T> responseType, String id) throws ItemFindException {
        try {
            WebTarget resource = webTarget;
            resource = resource.path(java.text.MessageFormat.format("{0}/AllModelsItems", new Object[]{id}));
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
        } catch (Exception e) {
            throw new ItemFindException();
        }
    }

    public <T> T findAllModelsItems_JSON(Class<T> responseType, String id) throws ItemFindException {
        try {
            WebTarget resource = webTarget;
            resource = resource.path(java.text.MessageFormat.format("{0}/AllModelsItems", new Object[]{id}));
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
        } catch (Exception e) {
            throw new ItemFindException();
        }
    }

    public void updateItem_XML(Object requestEntity, String id) throws ItemModifyException {
        try {
            webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
        } catch (Exception e) {
            throw new ItemModifyException();
        }
    }

    public void updateItem_JSON(Object requestEntity, String id) throws ItemModifyException {
        try {
            webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request(javax.ws.rs.core.MediaType.APPLICATION_JSON).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
        } catch (Exception e) {
            throw new ItemModifyException();
        }
    }

    public void close() {
        client.close();
    }

}
