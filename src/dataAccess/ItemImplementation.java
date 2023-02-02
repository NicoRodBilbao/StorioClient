package dataAccess;

import static dataAccess.ModelImplementation.LOGGER;
import entities.Item;
import entities.Model;
import interfaces.Itemable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    public void createItem(Item item) {
        try {
            LOGGER.info("Creating new Item.");
            ic.createItem_XML(item);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateItem(Item item) {
        try {
            LOGGER.log(Level.INFO, "Modifiying Item {0}.", item.getId());
            ic.updateItem_XML(item, ""+item.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Item> listAllItems() {
        try {
            LOGGER.info("Listing all Items.");
            return ic.findAllItems_XML(new GenericType<List<Item>>() {});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Item> findItemById(Integer id) {
        try {
            LOGGER.log(Level.INFO, "Listing Item {0}", id);
            List<Item> listItem = new ArrayList<>();
            listItem.add(ic.find_XML(Item.class, ""+id));
            return listItem;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteItem(Integer id) {
        try {
            LOGGER.log(Level.INFO, "Removing Item {0}.", id);
            ic.removeItem(""+id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Integer countItem() {
        try {
            return Integer.parseInt(ic.countREST(String.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
