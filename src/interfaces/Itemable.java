package interfaces;

import entities.Item;
import java.util.List;

/**
 *
 * @author Nicolás Rodríguez
 */
public interface Itemable {
     public void createItem(Item model);
    
    public void updateItem(Item model);
    
    public List<Item> listAllItems();
    
    public List<Item> findItemById(Integer id);
    
    public List<Item> findItemByModel(String model);
    
    public List<Item> findItemByPack(String pack);
    
    public void deleteItem(Integer id);
    
    public Integer countItem();
}
