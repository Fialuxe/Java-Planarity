package io.github.fialuxe.model.services;

import io.github.fialuxe.model.Edge;
import io.github.fialuxe.model.Point;

import java.util.List;

/**
 * Service class for validating planarity of graph drawings.
 * 
 * <p>This service provides algorithms to determine whether a given graph
 * drawing is planar (i.e., can be drawn in the plane with no edge crossings).</p>
 * 
 * <p><b>Theoretical Foundation:</b></p>
 * <ul>
 *   <li><b>Kuratowski's Theorem:</b> A graph is planar if and only if it contains
 *       no subdivision of K₅ or K₃,₃</li>
 *   <li><b>Wagner's Theorem:</b> A graph is planar if and only if it has no
 *       K₅ or K₃,₃ graph minor</li>
 *   <li><b>Planarity Testing:</b> Full planarity testing can be done in O(V)
 *       using Boyer-Myrvold or similar algorithms</li>
 * </ul>
 * 
 * <p><b>Implementation Note:</b> This implementation uses a simplified approach
 * based on intersection detection rather than full planarity testing. For small
 * graphs (V ≤ 12 nodes as in this puzzle game), this approach is sufficient
 * and avoids the complexity of implementing Boyer-Myrvold.</p>
 * 
 * <p><b>Validation Strategy:</b></p>
 * <ol>
 *   <li>Check structural constraints (no self-loops, valid indices)</li>
 *   <li>Check for edge intersections in current drawing</li>
 *   <li>If no intersections exist, the current drawing is planar</li>
 * </ol>
 * 
 * @author Fialuxe
 * @version 2.0
 * @since 2.0
 * @see <a href="https://en.wikipedia.org/wiki/Planar_graph">Planar Graph - Wikipedia</a>
 * @see <a href="https://en.wikipedia.org/wiki/Kuratowski%27s_theorem">Kuratowski's Theorem</a>
 */
public class PlanarityValidator {
    
    /** Service for detecting edge intersections */
    private final IntersectionDetectionService intersectionService;
    
    /**
     * Constructs a new PlanarityValidator.
     */
    public PlanarityValidator() {
        this.intersectionService = new IntersectionDetectionService();
    }
    
    /**
     * Checks if the given graph drawing is planar.
     * 
     * <p>A graph drawing is considered planar if:</p>
     * <ul>
     *   <li>All structural constraints are satisfied (no self-loops, valid indices)</li>
     *   <li>No edges intersect in the current node positioning</li>
     * </ul>
     * 
     * <p><b>Important:</b> This method checks if the <i>current drawing</i> is planar,
     * not whether the abstract graph is planar. A graph might be abstractly planar
     * but drawn with crossings in a particular configuration.</p>
     * 
     * @param nodes the list of node positions
     * @param edges the list of edges
     * @return true if the current drawing has no edge intersections, false otherwise
     * @throws IllegalArgumentException if nodes or edges is null
     * @complexity O(E²) where E is the number of edges (from intersection detection)
     */
    public boolean isPlanar(List<Point> nodes, List<Edge> edges) {
        if (nodes == null || edges == null) {
            throw new IllegalArgumentException("Nodes and edges cannot be null");
        }
        
        // Empty graph is planar
        if (edges.isEmpty()) {
            return true;
        }
        
        // Validate structural constraints
        if (!validateStructuralConstraints(nodes, edges)) {
            return false;
        }
        
        // Check for edge intersections
        List<Edge> intersectingEdges = intersectionService.findIntersectingEdges(nodes, edges);
        
        // Graph is planar if no edges intersect
        return intersectingEdges.isEmpty();
    }
    
    /**
     * Validates structural constraints of the graph.
     * 
     * <p>Checks that:</p>
     * <ul>
     *   <li>All edge indices are valid (within bounds of nodes list)</li>
     *   <li>No self-loops exist (checked by Edge constructor, but verified here)</li>
     * </ul>
     * 
     * @param nodes the list of node positions
     * @param edges the list of edges
     * @return true if all constraints are satisfied, false otherwise
     * @complexity O(E) where E is the number of edges
     */
    private boolean validateStructuralConstraints(List<Point> nodes, List<Edge> edges) {
        int numNodes = nodes.size();
        
        for (Edge edge : edges) {
            // Check if edge indices are within bounds
            if (edge.getStartIndex() < 0 || edge.getStartIndex() >= numNodes ||
                edge.getEndIndex() < 0 || edge.getEndIndex() >= numNodes) {
                return false;
            }
            
            // Self-loop check (should never happen due to Edge constructor validation)
            if (edge.getStartIndex() == edge.getEndIndex()) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Counts the number of edge intersections in the current drawing.
     * 
     * <p>This is useful for providing feedback to users about how close
     * they are to solving the puzzle.</p>
     * 
     * @param nodes the list of node positions
     * @param edges the list of edges
     * @return the number of intersecting edge pairs
     * @throws IllegalArgumentException if nodes or edges is null
     * @complexity O(E²) where E is the number of edges
     */
    public int countIntersections(List<Point> nodes, List<Edge> edges) {
        if (nodes == null || edges == null) {
            throw new IllegalArgumentException("Nodes and edges cannot be null");
        }
        
        int count = 0;
        
        for (int i = 0; i < edges.size(); i++) {
            for (int j = i + 1; j < edges.size(); j++) {
                if (intersectionService.areEdgesIntersecting(edges.get(i), edges.get(j), nodes)) {
                    count++;
                }
            }
        }
        
        return count;
    }
}
