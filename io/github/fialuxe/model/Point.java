package io.github.fialuxe.model;

import java.util.Objects;

/**
 * Represents an immutable 2D point in the planar graph coordinate system.
 * 
 * <p>This class represents a node's position in the planar graph puzzle.
 * Points are immutable value objects that follow best practices for
 * encapsulation and equality comparisons.</p>
 * 
 * <p><b>Immutability:</b> Once created, a Point's coordinates cannot be changed.
 * This prevents bugs related to shared mutable state and supports thread-safety.</p>
 * 
 * @author Fialuxe
 * @version 2.0
 * @since 1.0
 */
public class Point {
    
    /** The x-coordinate of this point. */
    private final int x;
    
    /** The y-coordinate of this point. */
    private final int y;
    
    /**
     * Constructs a new Point with the specified coordinates.
     * 
     * @param x the x-coordinate of the point
     * @param y the y-coordinate of the point
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Factory method to create a Point from a java.awt.Point.
     * 
     * @param awtPoint the AWT point to convert
     * @return a new Point with the same coordinates
     * @throws IllegalArgumentException if awtPoint is null
     */
    public static Point fromAwtPoint(java.awt.Point awtPoint) {
        if (awtPoint == null) {
            throw new IllegalArgumentException("AWT Point cannot be null");
        }
        return new Point((int) awtPoint.getX(), (int) awtPoint.getY());
    }
    
    /**
     * Converts this Point to a java.awt.Point.
     * 
     * @return a new java.awt.Point with the same coordinates
     */
    public java.awt.Point toAwtPoint() {
        return new java.awt.Point(x, y);
    }
    
    /**
     * Returns the x-coordinate of this point.
     * 
     * @return the x-coordinate
     */
    public int getX() {
        return x;
    }
    
    /**
     * Returns the y-coordinate of this point.
     * 
     * @return the y-coordinate
     */
    public int getY() {
        return y;
    }
    
    /**
     * Calculates the Euclidean distance from this point to another Point.
     * 
     * @param other the other point
     * @return the Euclidean distance between the two points
     * @throws IllegalArgumentException if other is null
     * @complexity O(1)
     */
    public double distance(Point other) {
        if (other == null) {
            throw new IllegalArgumentException("Point cannot be null");
        }
        int dx = this.x - other.x;
        int dy = this.y - other.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
    
    /**
     * Calculates the Euclidean distance from this point to a java.awt.Point.
     * 
     * <p>This method is provided for backward compatibility with the view layer
     * that uses java.awt.Point for mouse events.</p>
     * 
     * @param awtPoint the AWT point
     * @return the Euclidean distance between the two points
     * @throws IllegalArgumentException if awtPoint is null
     * @complexity O(1)
     */
    public double distance(java.awt.Point awtPoint) {
        if (awtPoint == null) {
            throw new IllegalArgumentException("AWT Point cannot be null");
        }
        int dx = this.x - (int) awtPoint.getX();
        int dy = this.y - (int) awtPoint.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }
    
    /**
     * Compares this point to another object for equality.
     * Two points are equal if they have the same x and y coordinates.
     * 
     * @param obj the object to compare to
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Point point = (Point) obj;
        return x == point.x && y == point.y;
    }
    
    /**
     * Returns a hash code for this point.
     * 
     * @return a hash code value for this point
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
    
    /**
     * Returns a string representation of this point in the format "(x, y)".
     * 
     * @return a string representation of this point
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
