package factories;

import dataAccess.ItemImplementation;
import interfaces.Itemable;

/**
 *
 * @author Nicolás Rodríguez
 */
public class ItemFactory {
    private static Itemable item = new ItemImplementation();

    /**
     * This method creates an interface instanced as a Data Access Object.
     *
     * @return interface Itemable as ItemImplementation
     */
    public static Itemable getAccessItem() {
        return item;
    }
}
