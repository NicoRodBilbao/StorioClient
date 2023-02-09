package exceptions;

/**
 * 
 *
 * @author Nicolás Rodríguez
 */
public class ItemModifyException extends Exception {

    /**
     * Creates a new instance of <code>ItemModifyException</code> without detail message.
     */
    public ItemModifyException() {
    }


    /**
     * Constructs an instance of <code>ItemModifyException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public ItemModifyException(String msg) {
        super(msg);
    }
}
