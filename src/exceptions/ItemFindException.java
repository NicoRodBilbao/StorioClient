package exceptions;

/**
 *
 * 
 * @author Nicolás Rodríguez
 */
public class ItemFindException extends Exception {

    /**
     * Creates a new instance of <code>ItemFindException</code> without detail message.
     */
    public ItemFindException() {
    }


    /**
     * Constructs an instance of <code>ItemFindException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public ItemFindException(String msg) {
        super(msg);
    }
}
