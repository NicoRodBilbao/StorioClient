/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entities.Pack;
import exceptions.PackManagerException;
import java.util.List;

/**
 *
 * @author 2dam
 */
public interface Packable {
    
    public void createPack(Pack pack) throws PackManagerException;

    public void updatePack(Pack pack) throws PackManagerException;

    public void deletePack(Pack pack) throws PackManagerException;
    
    public Pack getPackById(Integer id) throws PackManagerException;
    
    public List<Pack> getAllPacks() throws PackManagerException;
    
    public List<Pack> getPacksByType(String packType) throws PackManagerException;
    
    public List<Pack> getPacksByState(String packState) throws PackManagerException;

    public List<Pack> getPacksByBooking(Integer id) throws PackManagerException;
    
}
