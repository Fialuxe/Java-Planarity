package io.github.fialuxe.model;

import io.github.fialuxe.model.exceptions.GraphException;
import io.github.fialuxe.model.exceptions.InvalidGraphStateException;
import io.github.fialuxe.model.services.IntersectionDetectionService;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

/**
 * Core domain model representing a planar graph in the Planarity puzzle game.
 * 
 * <p>This class manages the state of the graph, including nodes (vertices) and
 * edges (connections between nodes). It follows the Single Responsibility Principle
 * by delegating algorithmic logic (intersection detection) to service classes.</p>
 * 
 * <p><b>Design Patterns:</b></p>
 * <ul>
 *   <li><b>Observer Pattern:</b> Uses PropertyChangeSupport for state change notifications</li>
 *   <li><b>Service Layer:</b> Delegates intersection detection to IntersectionDetectionService</li>
 *   <li><b>Value Objects:</b> Uses immutable Point and Edge objects</li>
 * </ul>
 * 
 * <p><b>State Management:</b></p>
 * <ul>
 *   <li>Nodes are stored as a list of Point objects representing positions</li>
 *   <li>Edges are stored as a list of Edge objects referencing node indices</li>
 *   <li>State changes trigger PropertyChangeEvents for observers (e.g., the view)</li>
 * </ul>
 * 
 * <p><b>Thread Safety:</b> This class is NOT thread-safe. External synchronization
 * is required if accessed from multiple threads.</p>
 * 
 * @author Fialuxe
 * @version 2.0
 * @since 1.0
 */
public class GraphModel {
    
    /** Property name for graph state changes */
    public static final String PROPERTY_GRAPH_CHANGED = "graphChanged";
    
    /** Property name for game solved state changes */
    public static final String PROPERTY_GAME_SOLVED = "gameSolved";
    
    /** List of node positions in the graph */
    private final List<Point> nodes;
    
    /** List of edges connecting nodes */
    private final List<Edge> edges;
    
    /** Support for property change listeners (replacement for Observable) */
    private final PropertyChangeSupport pcs;
    
    /** Service for detecting edge intersections */
    private final IntersectionDetectionService intersectionService;
    
    /**
     * Constructs a new empty GraphModel.
     */
    public GraphModel() {
        this.nodes = new ArrayList<>();
        this.edges = new ArrayList<>();
        this.pcs = new PropertyChangeSupport(this);
        this.intersectionService = new IntersectionDetectionService();
    }
    
    /**
     * Adds a PropertyChangeListener to receive notifications of state changes.
     * 
     * <p>Listeners will be notified when:</p>
     * <ul>
     *   <li>Nodes are added or moved</li>
     *   <li>Edges are added</li>
     *   <li>The graph reaches a solved state</li>
     * </ul>
     * 
     * @param listener the PropertyChangeListener to add
     * @throws IllegalArgumentException if listener is null
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("PropertyChangeListener cannot be null");
        }
        pcs.addPropertyChangeListener(listener);
    }
    
    /**
     * Removes a PropertyChangeListener.
     * 
     * @param listener the PropertyChangeListener to remove
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        if (listener != null) {
            pcs.removePropertyChangeListener(listener);
        }
    }
    
    /**
     * Adds a node at the specified position to the graph.
     * 
     * <p>After adding the node, all registered PropertyChangeListeners are notified.</p>
     * 
     * @param node the position of the new node
     * @throws InvalidGraphStateException if node is null
     * @complexity O(1) amortized (ArrayList add operation)
     */
    public void addNode(Point node) throws InvalidGraphStateException {
        if (node == null) {
            throw new InvalidGraphStateException("Cannot add null node to graph");
        }
        
        nodes.add(node);
        pcs.firePropertyChange(PROPERTY_GRAPH_CHANGED, null, nodes.size());
    }
    
    /**
     * Adds an edge connecting two nodes to the graph.
     * 
     * <p>The edge is specified by the indices of its endpoints in the nodes list.
     * The Edge constructor validates that indices are non-negative and not equal
     * (no self-loops).</p>
     * 
     * @param startIndex the index of the start node
     * @param endIndex the index of the end node
     * @throws InvalidGraphStateException if indices are invalid or out of bounds
     * @complexity O(1) amortized (ArrayList add operation)
     */
    public void addEdge(int startIndex, int endIndex) throws InvalidGraphStateException {
        // Validate indices are within bounds
        if (startIndex < 0 || startIndex >= nodes.size()) {
            throw new InvalidGraphStateException(
                "Start index out of bounds: " + startIndex + " (nodes: " + nodes.size() + ")");
        }
        if (endIndex < 0 || endIndex >= nodes.size()) {
            throw new InvalidGraphStateException(
                "End index out of bounds: " + endIndex + " (nodes: " + nodes.size() + ")");
        }
        
        try {
            Edge edge = new Edge(startIndex, endIndex);
            edges.add(edge);
            pcs.firePropertyChange(PROPERTY_GRAPH_CHANGED, null, edges.size());
        } catch (IllegalArgumentException e) {
            throw new InvalidGraphStateException("Cannot create edge: " + e.getMessage(), e);
        }
    }
    
    /**
     * Moves a node to a new position.
     * 
     * <p>This method updates the position of the node at the specified index.
     * It does NOT validate that the new position maintains planarity - that
     * responsibility belongs to the game logic.</p>
     * 
     * @param index the index of the node to move
     * @param newPosition the new position for the node
     * @throws InvalidGraphStateException if index is out of bounds or newPosition is null
     * @complexity O(1) for list update
     */
    public void moveNode(int index, Point newPosition) throws InvalidGraphStateException {
        if (index < 0 || index >= nodes.size()) {
            throw new InvalidGraphStateException(
                "Node index out of bounds: " + index + " (nodes: " + nodes.size() + ")");
        }
        if (newPosition == null) {
            throw new InvalidGraphStateException("New position cannot be null");
        }
        
        Point oldPosition = nodes.get(index);
        nodes.set(index, newPosition);
        pcs.firePropertyChange(PROPERTY_GRAPH_CHANGED, oldPosition, newPosition);
    }
    
    /**
     * Moves a node to a new position (AWT Point version for backward compatibility).
     * 
     * <p>This method converts the java.awt.Point to our Point type and checks
     * if the move results in a solved state, firing appropriate events.</p>
     * 
     * @param index the index of the node to move
     * @param newPosition the new position as a java.awt.Point
     * @throws InvalidGraphStateException if index is out of bounds or newPosition is null
     * @complexity O(E²) due to solved state check
     */
    public void moveNode(int index, java.awt.Point newPosition) throws InvalidGraphStateException {
        if (newPosition == null) {
            throw new InvalidGraphStateException("New position cannot be null");
        }
        
        Point point = Point.fromAwtPoint(newPosition);
        boolean wasSolved = isGameSolved();
        
        moveNode(index, point);
        
        boolean isSolved = isGameSolved();
        if (isSolved && !wasSolved) {
            // Game just became solved
            pcs.firePropertyChange(PROPERTY_GAME_SOLVED, false, true);
        } else if (!isSolved && wasSolved) {
            // Game was solved but now isn't
            pcs.firePropertyChange(PROPERTY_GAME_SOLVED, true, false);
        }
    }
    
    /**
     * Returns all edges that intersect with at least one other edge.
     * 
     * <p>This method delegates to the IntersectionDetectionService to maintain
     * separation of concerns. The service handles all computational geometry logic.</p>
     * 
     * @return a list of intersecting edges (empty if no intersections)
     * @complexity O(E²) where E is the number of edges
     */
    public List<Edge> getIntersectingEdges() {
        return intersectionService.findIntersectingEdges(nodes, edges);
    }
    
    /**
     * Checks if the game is in a solved state.
     * 
     * <p>The game is considered solved when no edges intersect. This is equivalent
     * to the graph being drawn in a planar configuration.</p>
     * 
     * @return true if no edges intersect, false otherwise
     * @complexity O(E²) where E is the number of edges
     */
    public boolean isGameSolved() {
        if (edges.isEmpty()) {
            return true; // Empty graph is trivially solved
        }
        List<Edge> intersecting = getIntersectingEdges();
        return intersecting.isEmpty();
    }
    
    /**
     * Returns an unmodifiable view of the nodes list.
     * 
     * <p><b>Important:</b> While the list itself is unmodifiable, the Point objects
     * are immutable, so there is no risk of external modification of node positions.</p>
     * 
     * @return an unmodifiable list of nodes
     */
    public List<Point> getNodes() {
        return Collections.unmodifiableList(nodes);
    }
    
    /**
     * Returns an unmodifiable view of the edges list.
     * 
     * <p><b>Important:</b> While the list itself is unmodifiable, the Edge objects
     * are immutable, so there is no risk of external modification of edges.</p>
     * 
     * @return an unmodifiable list of edges
     */
    public List<Edge> getEdges() {
        return Collections.unmodifiableList(edges);
    }
    
    /**
     * Returns the internal mutable nodes list for direct manipulation.
     * 
     * <p><b>Warning:</b> This method is provided for backward compatibility with
     * GraphGenerator which needs to clear and rebuild the graph. Use with caution!</p>
     * 
     * <p>This method may be deprecated in future versions. Prefer using the public
     * API methods (addNode, moveNode, etc.) when possible.</p>
     * 
     * @return the mutable internal nodes list
     * @deprecated Use addNode() and moveNode() instead
     */
    @Deprecated
    List<Point> getNodesInternal() {
        return nodes;
    }
    
    /**
     * Returns the internal mutable edges list for direct manipulation.
     * 
     * <p><b>Warning:</b> This method is provided for backward compatibility with
     * GraphGenerator which needs to clear and rebuild the graph. Use with caution!</p>
     * 
     * <p>This method may be deprecated in future versions. Prefer using the public
     * API methods (addEdge, etc.) when possible.</p>
     * 
     * @return the mutable internal edges list
     * @deprecated Use addEdge() instead
     */
    @Deprecated
    List<Edge> getEdgesInternal() {
        return edges;
    }
}

