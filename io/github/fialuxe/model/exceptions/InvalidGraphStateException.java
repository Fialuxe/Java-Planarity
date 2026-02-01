package io.github.fialuxe.model.exceptions;

/**
 * Exception thrown when an invalid graph operation is attempted or
 * when the graph is in an invalid state.
 * 
 * <p>Examples of invalid operations include:</p>
 * <ul>
 *   <li>Adding a null node or edge</li>
 *   <li>Referencing a node with an invalid index</li>
 *   <li>Creating an edge with a self-loop (startIndex == endIndex)</li>
 *   <li>Moving a node to an invalid position</li>
 *   <li>Duplicate edges in the graph</li>
 * </ul>
 * 
 * @author Fialuxe
 * @version 2.0
 * @since 2.0
 */
public class InvalidGraphStateException extends GraphException {
    
    /**
     * Constructs a new InvalidGraphStateException with the specified detail message.
     * 
     * @param message the detail message explaining what operation or state is invalid
     */
    public InvalidGraphStateException(String message) {
        super(message);
    }
    
    /**
     * Constructs a new InvalidGraphStateException with the specified detail message and cause.
     * 
     * @param message the detail message explaining what operation or state is invalid
     * @param cause the underlying cause of this exception
     */
    public InvalidGraphStateException(String message, Throwable cause) {
        super(message, cause);
    }
}
