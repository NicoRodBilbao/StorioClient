package interfaces;

import entities.Model;
import exceptions.ItemFindException;
import java.util.List;

/**
 *
 * @author Nicolás Rodríguez
 */
public interface Modelable {
    public void createModel(Model model);
    
    public void updateModel(Model model);
    
    public List<Model> listAllModels() throws ItemFindException;
    
    public List<Model> findModelById(Integer id);
    
    public void deleteModel(Integer id);
    
    public Integer countModel();
}
