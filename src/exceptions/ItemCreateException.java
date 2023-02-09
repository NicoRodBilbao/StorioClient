package exceptions;

/**
 *
 * 
 * @author Nicolás Rodríguez
 */
public class ItemCreateException extends Exception {

    /**
     * Creates a new instance of <code>ItemCreateException</code> without detail message.
     */
    public ItemCreateException() {
    }


    /**
     * Constructs an instance of <code>ItemCreateException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public ItemCreateException(String msg) {
        super(msg);
    }
}
