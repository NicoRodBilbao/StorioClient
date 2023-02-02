package factories;

import dataAccess.ModelImplementation;
import interfaces.Modelable;

/**
 *
 * @author Nicolás Rodríguez
 */
public class ModelFactory {
    private static Modelable model = new ModelImplementation();

    /**
     * This method creates an interface instanced as a Data Access Object.
     *
     * @return interface Modelable as ModelImplementation
     */
    public static Modelable getAccessModel() {
        return model;
    }
}
