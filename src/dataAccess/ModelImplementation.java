package dataAccess;

import entities.Model;
import interfaces.Modelable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.GenericType;
import services.ModelClient;

/**
 *
 * @author Nicolás Rodríguez
 */
public class ModelImplementation implements Modelable{

    public ModelClient mc;
    
    protected static final Logger LOGGER = Logger.getLogger(ModelImplementation.class.getName());
    
    public ModelImplementation() {
        mc = new ModelClient();
    }
    
    @Override
    public void createModel(Model model) {
        try {
            LOGGER.info("Creating new Model.");
            mc.createModel_XML(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateModel(Model model) {
        try {
            LOGGER.log(Level.INFO, "Modifiying model {0}.", model.getId());
            mc.updateModel_XML(model, ""+model.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Model> listAllModels() {
        try {
            LOGGER.info("Listing all models.");
            return mc.findAll_XML(new GenericType<List<Model>>() {});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Model> findModelById(Integer id) {
        try {
            LOGGER.log(Level.INFO, "Listing model {0}", id);
            List<Model> listModel = new ArrayList<>();
            listModel.add(mc.find_XML(Model.class, ""+id));
            return listModel;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteModel(Integer id) {
        try {
            LOGGER.log(Level.INFO, "Removing model {0}.", id);
            mc.removeModel(""+id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Integer countModel() {
        try {
            return Integer.parseInt(mc.countREST(String.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
