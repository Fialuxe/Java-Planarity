package io.github.fialuxe.model.services;

import io.github.fialuxe.model.Edge;
import io.github.fialuxe.model.Point;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Service class for detecting edge intersections in a planar graph drawing.
 * 
 * <p>This service encapsulates all the computational geometry algorithms
 * needed to determine whether edges (line segments) intersect. It uses
 * the cross-product method for determining line segment intersection,
 * which is a standard algorithm in computational geometry.</p>
 * 
 * <p><b>Algorithm:</b> For two line segments AB and CD:</p>
 * <ol>
 *   <li>Compute cross products to check if points are on opposite sides</li>
 *   <li>Handle special cases (collinear points, endpoint touching)</li>
 *   <li>Exclude edges that share a common vertex (not considered crossing)</li>
 * </ol>
 * 
 * <p><b>Theoretical Foundation:</b></p>
 * <ul>
 *   <li>Based on vector cross product properties in 2D geometry</li>
 *   <li>Uses orientation test: sign of (B-A) × (C-A)</li>
 *   <li>Numerical stability considerations for integer coordinates</li>
 * </ul>
 * 
 * @author Fialuxe
 * @version 2.0
 * @since 2.0
 */
public class IntersectionDetectionService {
    
    /**
     * Finds all edges that intersect with at least one other edge.
     * 
     * <p>This method performs pairwise intersection testing on all edges
     * in the graph. An edge is included in the result if it crosses any
     * other edge (excluding edges that share a common endpoint).</p>
     * 
     * @param nodes the list of node positions
     * @param edges the list of edges to check
     * @return a list of all edges that have intersections
     * @throws IllegalArgumentException if nodes or edges is null
     * @complexity O(E²) where E is the number of edges
     */
    public List<Edge> findIntersectingEdges(List<Point> nodes, List<Edge> edges) {
        if (nodes == null || edges == null) {
            throw new IllegalArgumentException("Nodes and edges cannot be null");
        }
        
        Set<Edge> intersectingEdges = new HashSet<>();
        
        // Pairwise intersection check
        for (int i = 0; i < edges.size(); i++) {
            for (int j = i + 1; j < edges.size(); j++) {
                Edge e1 = edges.get(i);
                Edge e2 = edges.get(j);
                
                if (areEdgesIntersecting(e1, e2, nodes)) {
                    intersectingEdges.add(e1);
                    intersectingEdges.add(e2);
                }
            }
        }
        
        return new ArrayList<>(intersectingEdges);
    }
    
    /**
     * Checks if two edges intersect in the current graph drawing.
     * 
     * <p>Two edges intersect if their corresponding line segments cross.
     * Edges that share a common endpoint are not considered intersecting,
     * as this is a valid configuration in any graph.</p>
     * 
     * @param e1 the first edge
     * @param e2 the second edge
     * @param nodes the list of node positions
     * @return true if the edges intersect, false otherwise
     * @throws IllegalArgumentException if any parameter is null
     * @complexity O(1)
     */
    public boolean areEdgesIntersecting(Edge e1, Edge e2, List<Point> nodes) {
        if (e1 == null || e2 == null || nodes == null) {
            throw new IllegalArgumentException("Edges and nodes cannot be null");
        }
        
        // Edges that share a vertex cannot be "crossing"
        if (e1.sharesVertex(e2)) {
            return false;
        }
        
        // Get the endpoints of both edges
        Point e1Start = nodes.get(e1.getStartIndex());
        Point e1End = nodes.get(e1.getEndIndex());
        Point e2Start = nodes.get(e2.getStartIndex());
        Point e2End = nodes.get(e2.getEndIndex());
        
        return doLineSegmentsIntersect(e1Start, e1End, e2Start, e2End);
    }
    
    /**
     * Finds the exact intersection point of two edges, if it exists.
     * 
     * <p>This method computes the geometric intersection point of two
     * line segments. If the segments don't intersect, an empty Optional
     * is returned.</p>
     * 
     * @param e1 the first edge
     * @param e2 the second edge
     * @param nodes the list of node positions
     * @return an Optional containing the intersection point, or empty if no intersection
     * @complexity O(1)
     */
    public Optional<Point> findIntersectionPoint(Edge e1, Edge e2, List<Point> nodes) {
        if (e1 == null || e2 == null || nodes == null) {
            throw new IllegalArgumentException("Edges and nodes cannot be null");
        }
        
        if (!areEdgesIntersecting(e1, e2, nodes)) {
            return Optional.empty();
        }
        
        Point a = nodes.get(e1.getStartIndex());
        Point b = nodes.get(e1.getEndIndex());
        Point c = nodes.get(e2.getStartIndex());
        Point d = nodes.get(e2.getEndIndex());
        
        return computeIntersectionPoint(a, b, c, d);
    }
    
    /**
     * Determines if two line segments AB and CD intersect.
     * 
     * <p>Uses the cross-product method to check if the line segments intersect.
     * This is based on the orientation test in computational geometry.</p>
     * 
     * <p><b>Algorithm:</b> Two segments intersect if:</p>
     * <ul>
     *   <li>Points C and D are on opposite sides of line AB, AND</li>
     *   <li>Points A and B are on opposite sides of line CD</li>
     * </ul>
     * <p>OR if an endpoint of one segment lies on the other segment.</p>
     * 
     * @param a first point of first segment
     * @param b second point of first segment
     * @param c first point of second segment
     * @param d second point of second segment
     * @return true if segments AB and CD intersect
     * @complexity O(1)
     * @see <a href="https://en.wikipedia.org/wiki/Line_segment_intersection">Line Segment Intersection</a>
     */
    private boolean doLineSegmentsIntersect(Point a, Point b, Point c, Point d) {
        // Cross products to check orientation
        double cross1 = crossProduct(b, a, c) * crossProduct(b, a, d);
        double cross2 = crossProduct(d, c, a) * crossProduct(d, c, b);
        
        // Proper intersection: points on opposite sides
        boolean properIntersection = cross1 < 0 && cross2 < 0;
        
        // Endpoint on segment (boundary case)
        boolean endpointOnSegment = 
            (crossProduct(b, a, c) == 0 && isOnSegment(a, b, c)) ||
            (crossProduct(b, a, d) == 0 && isOnSegment(a, b, d)) ||
            (crossProduct(d, c, a) == 0 && isOnSegment(c, d, a)) ||
            (crossProduct(d, c, b) == 0 && isOnSegment(c, d, b));
        
        return properIntersection || endpointOnSegment;
    }
    
    /**
     * Computes the cross product of vectors (p2-p1) and (p3-p1).
     * 
     * <p>The cross product determines the orientation of three points:</p>
     * <ul>
     *   <li>Positive: p3 is to the left of vector p1→p2 (counter-clockwise)</li>
     *   <li>Zero: p3 is collinear with p1 and p2</li>
     *   <li>Negative: p3 is to the right of vector p1→p2 (clockwise)</li>
     * </ul>
     * 
     * @param p1 the origin point
     * @param p2 the end point of the base vector
     * @param p3 the point to test
     * @return the cross product value
     * @complexity O(1)
     */
    private double crossProduct(Point p1, Point p2, Point p3) {
        return (p3.getX() - p1.getX()) * (p2.getY() - p1.getY()) -
               (p3.getY() - p1.getY()) * (p2.getX() - p1.getX());
    }
    
    /**
     * Checks if point p lies on the line segment AB (including endpoints).
     * 
     * <p>Assumes that p is known to be collinear with A and B.
     * This method only checks if p is within the bounding box of AB.</p>
     * 
     * @param a the first endpoint of the segment
     * @param b the second endpoint of the segment
     * @param p the point to test
     * @return true if p is on segment AB
     * @complexity O(1)
     */
    private boolean isOnSegment(Point a, Point b, Point p) {
        return Math.min(a.getX(), b.getX()) <= p.getX() && 
               p.getX() <= Math.max(a.getX(), b.getX()) &&
               Math.min(a.getY(), b.getY()) <= p.getY() && 
               p.getY() <= Math.max(a.getY(), b.getY());
    }
    
    /**
     * Computes the exact intersection point of two line segments.
     * 
     * <p>Uses parametric line equation to find intersection.</p>
     * 
     * @param a first point of first segment
     * @param b second point of first segment
     * @param c first point of second segment
     * @param d second point of second segment
     * @return Optional containing intersection point, or empty if parallel/non-intersecting
     * @complexity O(1)
     */
    private Optional<Point> computeIntersectionPoint(Point a, Point b, Point c, Point d) {
        double x1 = a.getX(), y1 = a.getY();
        double x2 = b.getX(), y2 = b.getY();
        double x3 = c.getX(), y3 = c.getY();
        double x4 = d.getX(), y4 = d.getY();
        
        double denom = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
        
        if (Math.abs(denom) < 1e-10) {
            // Lines are parallel or coincident
            return Optional.empty();
        }
        
        double t = ((x1 - x3) * (y3 - y4) - (y1 - y3) * (x3 - x4)) / denom;
        
        int x = (int) Math.round(x1 + t * (x2 - x1));
        int y = (int) Math.round(y1 + t * (y2 - y1));
        
        return Optional.of(new Point(x, y));
    }
}
