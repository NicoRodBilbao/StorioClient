package dataAccess;

import entities.Item;
import exceptions.ItemCreateException;
import exceptions.ItemDeleteException;
import exceptions.ItemFindException;
import exceptions.ItemModifyException;
import interfaces.Itemable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericType;
import services.ItemClient;

/**
 * This interface implements the methods that get the data.
 *
 * @author Nicolás Rodríguez
 */
public class ItemImplementation implements Itemable {
    
    public ItemClient ic;
    
    protected static final Logger LOGGER = Logger.getLogger(ItemImplementation.class.getName());
    
    public ItemImplementation() {
        ic = new ItemClient();
    }

    @Override
    public void createItem(Item item) throws ItemCreateException {
        try {
            LOGGER.info("Creating new Item.");
            ic.createItem_XML(item);
        } catch (ItemCreateException e) {
            throw new ItemCreateException("There was an error when creating Items.\n"+ e.getLocalizedMessage());
        }
    }

    @Override
    public void updateItem(Item item) throws ItemModifyException {
        try {
            LOGGER.log(Level.INFO, "Modifiying Item {0}.", item.getId());
            ic.updateItem_XML(item, ""+item.getId());
        } catch (ItemModifyException e) {
            throw new ItemModifyException("There was an error when modifying Items.\n"+ e.getLocalizedMessage());
        }
    }

    @Override
    public List<Item> listAllItems() throws ItemFindException{
        try {
            LOGGER.info("Listing all Items.");
            return ic.findAllItems_XML(new GenericType<List<Item>>() {});
        } catch (ItemFindException e) {
            throw new ItemFindException("There was an error when finding Items.\n"+ e.getLocalizedMessage());
        }
    }

    @Override
    public List<Item> findItemById(Integer id) throws ItemFindException {
        try {
            LOGGER.log(Level.INFO, "Listing Item {0}", id);
            List<Item> listItem = new ArrayList<>();
            listItem.add(ic.find_XML(Item.class, ""+id));
            return listItem;
        } catch (ItemFindException e) {
            throw new ItemFindException("There was an error when finding Items.\n"+ e.getLocalizedMessage());
        }
    }

    @Override
    public void deleteItem(Integer id) throws ItemDeleteException {
        try {
            LOGGER.log(Level.INFO, "Removing Item {0}.", id);
            ic.removeItem(""+id);
        } catch (ItemDeleteException e) {
            throw new ItemDeleteException("There was an error when deleting the Item.\n"+ e.getLocalizedMessage());
        }
    }

    @Override
    public Integer countItem() throws ItemFindException {
        try {
            return Integer.parseInt(ic.countREST(String.class));
        } catch (ItemFindException e) {
            throw new ItemFindException("There was an error when counting the Items.\n"+ e.getLocalizedMessage());
        }
    }

    @Override
    public List<Item> findItemByModel(String model) throws ItemFindException {
        try {
            LOGGER.log(Level.INFO, "Listing Items from model {0}", model);
            List<Item> listItem = new ArrayList<>();
            return ic.findAllModelsItems_XML(new GenericType<List<Item>>() {}, model);
        } catch (ItemFindException e) {
            throw new ItemFindException("There was an error when finding all Models Items.\n"+ e.getLocalizedMessage());
        }
    }

    @Override
    public List<Item> findItemByPack(String pack) throws ItemFindException {
        try {
            LOGGER.log(Level.INFO, "Listing Items from pack {0}", pack);
            List<Item> listItem = new ArrayList<>();
            return ic.findAllPacksItems_XML(new GenericType<List<Item>>() {}, pack);
        } catch (ItemFindException e) {
            throw new ItemFindException("There was an error when finding all Packs Items.\n"+ e.getLocalizedMessage());
        }
    }

}
