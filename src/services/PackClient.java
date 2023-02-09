/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Pack;
import exceptions.PackManagerException;
import java.util.ResourceBundle;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 * Jersey REST client generated for REST resource:PackFacadeREST
 * [entities.pack]<br>
 * USAGE:
 * <pre>
 *        PackRESTfullClient client = new PackRESTfullClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author 2dam
 */
public class PackClient {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = ResourceBundle.getBundle("services.config").getString("URL"); //Parametrizar en un archivo de propiedades

    public PackClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("entities.pack");
    }

    public <T> T findPacksByState_XML(GenericType<T> responseType, String state) throws PackManagerException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("state/{0}", new Object[]{state}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public <T> T findPacksByState_JSON(GenericType<T> responseType, String state) throws PackManagerException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("state/{0}", new Object[]{state}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public <T> T listPackByBooking_XML(GenericType<T> responseType, String id) throws PackManagerException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("booking/{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public <T> T listPackByBooking_JSON(GenericType<T> responseType, String id) throws PackManagerException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("booking/{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public <T> T findPacksByType_XML(GenericType<T> responseType, String type) throws PackManagerException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("type/{0}", new Object[]{type}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public <T> T findPacksByType_JSON(GenericType<T> responseType, String type) throws PackManagerException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("type/{0}", new Object[]{type}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public void create_XML(Object requestEntity) throws PackManagerException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), Pack.class);
    }

    public void create_JSON(Object requestEntity) throws PackManagerException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), Pack.class);
    }

    public void update_XML(Object requestEntity, String id) throws PackManagerException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), Pack.class);
    }

    public void update_JSON(Object requestEntity, String id) throws PackManagerException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request(javax.ws.rs.core.MediaType.APPLICATION_JSON).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), Pack.class);
    }

    public <T> T findPackById_XML(Class<T> responseType, String id) throws PackManagerException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public <T> T findPackById_JSON(Class<T> responseType, String id) throws PackManagerException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public <T> T findAll_XML(GenericType<T> responseType) throws PackManagerException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public <T> T findAll_JSON(GenericType<T> responseType) throws PackManagerException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public void remove(String id) throws PackManagerException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete(Pack.class);
    }

    public void close() {
        client.close();
    }

}
