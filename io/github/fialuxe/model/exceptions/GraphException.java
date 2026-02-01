package io.github.fialuxe.model.exceptions;

/**
 * Base exception class for all graph-related errors in the Planarity game.
 * This exception serves as the parent class for more specific graph exceptions.
 * 
 * <p>This exception is used to signal errors related to graph structure,
 * operations, or state management that violate the constraints of planar graphs.</p>
 * 
 * @author Fialuxe
 * @version 2.0
 * @since 2.0
 */
public class GraphException extends Exception {
    
    /**
     * Constructs a new GraphException with the specified detail message.
     * 
     * @param message the detail message explaining the cause of the exception
     */
    public GraphException(String message) {
        super(message);
    }
    
    /**
     * Constructs a new GraphException with the specified detail message and cause.
     * 
     * @param message the detail message explaining the cause of the exception
     * @param cause the underlying cause of this exception
     */
    public GraphException(String message, Throwable cause) {
        super(message, cause);
    }
}
