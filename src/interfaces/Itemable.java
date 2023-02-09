package interfaces;

import entities.Item;
import exceptions.ItemCreateException;
import exceptions.ItemDeleteException;
import exceptions.ItemFindException;
import exceptions.ItemModifyException;
import java.util.List;

/**
 *
 * @author Nicolás Rodríguez
 */
public interface Itemable {
     public void createItem(Item model) throws ItemCreateException;
    
    public void updateItem(Item model) throws ItemModifyException;
    
    public List<Item> listAllItems() throws ItemFindException;
    
    public List<Item> findItemById(Integer id)  throws ItemFindException;
    
    public List<Item> findItemByModel(String model) throws ItemFindException;
    
    public List<Item> findItemByPack(String pack) throws ItemFindException;
    
    public void deleteItem(Integer id) throws ItemDeleteException;
    
    public Integer countItem() throws ItemFindException;
}
