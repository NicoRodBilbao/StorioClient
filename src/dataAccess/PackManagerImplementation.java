/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataAccess;

import services.PackClient;
import entities.Pack;
import exceptions.PackManagerException;
import java.util.List;
import java.util.logging.Level;
import javax.ws.rs.core.GenericType;
import java.util.logging.Logger;
import interfaces.Packable;

/**
 *
 * @author 2dam
 */
public class PackManagerImplementation implements Packable {

    private PackClient packWebClient;
    private static final Logger LOGGER=Logger.getLogger(PackManagerImplementation.class.getName());

    public PackManagerImplementation() {
        packWebClient = new PackClient();
    }

    @Override
    public void createPack(Pack pack) throws PackManagerException {
        try{
            LOGGER.log(Level.INFO,"UsersManager: Creating user {0}.",pack.getId());
            //Send user data to web client for creation. 
            packWebClient.create_XML(pack);
        }catch(Exception ex){
            LOGGER.log(Level.SEVERE,
                    "UsersManager: Exception creating user, {0}",
                    ex.getMessage());
            throw new PackManagerException("Error creating pack:\n"+ex.getMessage());
        }
    }

    @Override
    public void updatePack(Pack pack) throws PackManagerException {
        try{
            LOGGER.log(Level.INFO,"UsersManager: Creating user {0}.",pack.getId());
            //Send user data to web client for creation. 
            packWebClient.update_XML(pack, pack.getId().toString());
        }catch(Exception ex){
            LOGGER.log(Level.SEVERE,
                    "UsersManager: Exception creating user, {0}",
                    ex.getMessage());
            throw new PackManagerException("Error creating pack:\n"+ex.getMessage());
        }
    }

    @Override
    public void deletePack(Pack pack) throws PackManagerException {
       try{
            LOGGER.log(Level.INFO,"UsersManager: Deleting user {0}.",pack.getId());
            packWebClient.remove(pack.getId().toString());
        }catch(Exception ex){
            LOGGER.log(Level.SEVERE,
                    "UsersManager: Exception deleting user, {0}",
                    ex.getMessage());
            throw new PackManagerException("Error deleting pack:\n"+ex.getMessage());
        }
    }

    @Override
    public Pack getPackById(Integer id) throws PackManagerException {
        Pack pack = null;
        try{
            LOGGER.log(Level.INFO,"PackManager: seach user {0}.",id);
            pack = packWebClient.findPackById_XML(Pack.class, id.toString());
        }catch(Exception ex){
            LOGGER.log(Level.SEVERE,
                    "PackManager: Exception seach user, {0}",
                    ex.getMessage());
            throw new PackManagerException("Error deleting pack:\n"+ex.getMessage());
        }
        return pack;
    }

    @Override
    public List<Pack> getAllPacks() throws PackManagerException {
        List<Pack> users =null;
        try{
            LOGGER.info("UsersManager: Finding all users from REST service (XML).");
            //Ask webClient for all users' data.
            users = packWebClient.findAll_XML(new GenericType<List<Pack>>() {});
        }catch(Exception ex){
            LOGGER.log(Level.SEVERE,
                    "UsersManager: Exception finding all users, {0}",
                    ex.getMessage());
            throw new PackManagerException("Error finding all users:\n"+ex.getMessage());
        }
        return users;
    }

    @Override
    public List<Pack> getPacksByType(String packType) throws PackManagerException {
       List<Pack> packs = null;
        try{
            packs = packWebClient.findPacksByType_XML(new GenericType<List<Pack>>() {}, packType);
        }catch(Exception ex){
            LOGGER.log(Level.SEVERE,
                    "PackManager: Exception deleting user, {0}",
                    ex.getMessage());
            throw new PackManagerException("Error in search packs by Type:\n"+ex.getMessage());
        }
        return packs;
    }

    @Override
    public List<Pack> getPacksByState(String packState) throws PackManagerException {
        List<Pack> packs = null;
        try{
            packs = packWebClient.findPacksByState_XML(new GenericType<List<Pack>>() {}, packState);
        }catch(Exception ex){
            LOGGER.log(Level.SEVERE,
                    "PackManager: search packs by Type",
                    ex.getMessage());
            throw new PackManagerException("Error in search packs by Type:\n"+ex.getMessage());
        }
        return packs;
    }

    @Override
    public List<Pack> getPacksByBooking(Integer id) throws PackManagerException {
        List<Pack> packs = null;
        try{
            packs = packWebClient.findPacksByType_XML(new GenericType<List<Pack>>() {}, id.toString());
        }catch(Exception ex){
            LOGGER.log(Level.SEVERE,
                    "PackManager: search packs by Type",
                    ex.getMessage());
            throw new PackManagerException("Error in search packs by Type:\n"+ex.getMessage());
        }
        return packs;
    }

    

}