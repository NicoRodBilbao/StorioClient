package exceptions;

/**
 *
 * 
 * @author Nicolás Rodríguez
 */
public class ItemDeleteException extends Exception {

    /**
     * Creates a new instance of <code>ItemDeleteException</code> without detail message.
     */
    public ItemDeleteException() {
    }


    /**
     * Constructs an instance of <code>ItemDeleteException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public ItemDeleteException(String msg) {
        super(msg);
    }
}
