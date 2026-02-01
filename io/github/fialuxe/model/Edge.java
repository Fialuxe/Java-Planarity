package io.github.fialuxe.model;

import java.util.Objects;

/**
 * Represents an immutable edge (connection) between two nodes in a planar graph.
 * 
 * <p>An edge connects two nodes identified by their indices in the graph's node list.
 * Edges are undirected, meaning Edge(i, j) is equivalent to Edge(j, i).</p>
 * 
 * <p><b>Graph Theory Constraints:</b></p>
 * <ul>
 *   <li>No self-loops: An edge cannot connect a node to itself (startIndex != endIndex)</li>
 *   <li>Non-negative indices: Node indices must be non-negative</li>
 *   <li>Immutability: Once created, an edge's endpoints cannot be changed</li>
 * </ul>
 * 
 * @author Fialuxe
 * @version 2.0
 * @since 1.0
 */
public class Edge {
    
    /** The index of the start node of this edge. */
    private final int startIndex;
    
    /** The index of the end node of this edge. */
    private final int endIndex;
    
    /**
     * Constructs a new Edge connecting two nodes.
     * 
     * <p><b>Validation:</b></p>
     * <ul>
     *   <li>Both indices must be non-negative</li>
     *   <li>startIndex must not equal endIndex (no self-loops)</li>
     * </ul>
     * 
     * @param startIndex the index of the start node
     * @param endIndex the index of the end node
     * @throws IllegalArgumentException if indices are negative or equal
     */
    public Edge(int startIndex, int endIndex) {
        if (startIndex < 0 || endIndex < 0) {
            throw new IllegalArgumentException(
                "Edge indices must be non-negative: start=" + startIndex + ", end=" + endIndex);
        }
        if (startIndex == endIndex) {
            throw new IllegalArgumentException(
                "Self-loops are not allowed in planar graphs: index=" + startIndex);
        }
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }
    
    /**
     * Returns the index of the start node.
     * 
     * @return the start node index
     */
    public int getStartIndex() {
        return startIndex;
    }
    
    /**
     * Returns the index of the end node.
     * 
     * @return the end node index
     */
    public int getEndIndex() {
        return endIndex;
    }
    
    /**
     * Checks if this edge shares at least one vertex (endpoint) with another edge.
     * 
     * <p>This method is used in intersection detection, as edges that share
     * a vertex cannot be considered as "crossing" each other.</p>
     * 
     * @param other the other edge to check
     * @return true if the edges share at least one vertex, false otherwise
     * @throws IllegalArgumentException if other is null
     * @complexity O(1)
     */
    public boolean sharesVertex(Edge other) {
        if (other == null) {
            throw new IllegalArgumentException("Edge cannot be null");
        }
        return this.startIndex == other.startIndex ||
               this.startIndex == other.endIndex ||
               this.endIndex == other.startIndex ||
               this.endIndex == other.endIndex;
    }
    
    /**
     * Compares this edge to another object for equality.
     * 
     * <p>Two edges are equal if they connect the same two nodes,
     * regardless of which is considered the "start" or "end" node.
     * This reflects the undirected nature of the graph.</p>
     * 
     * <p>Examples:</p>
     * <ul>
     *   <li>Edge(1, 2).equals(Edge(1, 2)) → true</li>
     *   <li>Edge(1, 2).equals(Edge(2, 1)) → true (undirected)</li>
     *   <li>Edge(1, 2).equals(Edge(1, 3)) → false</li>
     * </ul>
     * 
     * @param obj the object to compare to
     * @return true if the edges connect the same nodes, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Edge edge = (Edge) obj;
        // Undirected edge: (a,b) == (b,a)
        return (startIndex == edge.startIndex && endIndex == edge.endIndex) ||
               (startIndex == edge.endIndex && endIndex == edge.startIndex);
    }
    
    /**
     * Returns a hash code for this edge.
     * 
     * <p>The hash code is computed in a way that respects the undirected
     * nature of edges: Edge(i, j) and Edge(j, i) have the same hash code.</p>
     * 
     * @return a hash code value for this edge
     */
    @Override
    public int hashCode() {
        // Symmetric hash: ensure (a,b) and (b,a) have same hash
        return Objects.hash(Math.min(startIndex, endIndex), 
                          Math.max(startIndex, endIndex));
    }
    
    /**
     * Returns a string representation of this edge in the format "Edge(start→end)".
     * 
     * @return a string representation of this edge
     */
    @Override
    public String toString() {
        return "Edge(" + startIndex + "→" + endIndex + ")";
    }
}
