package dataAccess;

import entities.Model;
import interfaces.Modelable;
import java.util.List;
import javax.ws.rs.core.GenericType;
import services.ModelClient;

/**
 *
 * @author Nicolás Rodríguez
 */
public class ModelImplementation implements Modelable{

    public ModelClient mc;
    
    public ModelImplementation() {
        mc = new ModelClient();
    }
    
    @Override
    public void createModel(Model model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void modifyModel(Model model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Model> listAllModels() {
        try {
            return mc.findAll_XML(new GenericType<List<Model>>() {});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Model> findModelById(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteMode(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
