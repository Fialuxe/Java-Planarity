package io.github.fialuxe.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Point class.
 * 
 * Tests cover:
 * - Constructor and getters
 * - Distance calculations
 * - AWT Point conversion
 * - Equals and hashCode contracts
 * - Immutability guarantees
 */
@DisplayName("Point Class Tests")
class PointTest {

    @Test
    @DisplayName("Constructor should initialize x and y coordinates")
    void testConstructor() {
        Point point = new Point(10, 20);
        assertEquals(10, point.getX());
        assertEquals(20, point.getY());
    }

    @Test
    @DisplayName("Constructor should handle negative coordinates")
    void testConstructorWithNegativeCoordinates() {
        Point point = new Point(-5, -10);
        assertEquals(-5, point.getX());
        assertEquals(-10, point.getY());
    }

    @ParameterizedTest
    @DisplayName("Distance calculation should be accurate")
    @CsvSource({
            "0, 0, 3, 4, 5.0", // 3-4-5 triangle
            "0, 0, 0, 0, 0.0", // Same point
            "1, 1, 1, 1, 0.0", // Same point
            "0, 0, 1, 1, 1.4142135623730951", // √2
            "-3, -4, 0, 0, 5.0" // Negative coordinates
    })
    void testDistance(int x1, int y1, int x2, int y2, double expected) {
        Point p1 = new Point(x1, y1);
        Point p2 = new Point(x2, y2);
        assertEquals(expected, p1.distance(p2), 0.0001,
                "Distance from (" + x1 + "," + y1 + ") to (" + x2 + "," + y2 + ")");
    }

    @Test
    @DisplayName("Distance method should throw exception for null Point")
    void testDistanceWithNullPoint() {
        Point point = new Point(0, 0);
        assertThrows(IllegalArgumentException.class, () -> point.distance((Point) null));
    }

    @Test
    @DisplayName("Distance method should throw exception for null AWT Point")
    void testDistanceWithNullAwtPoint() {
        Point point = new Point(0, 0);
        assertThrows(IllegalArgumentException.class,
                () -> point.distance((java.awt.Point) null));
    }

    @Test
    @DisplayName("Distance to AWT Point should be accurate")
    void testDistanceToAwtPoint() {
        Point point = new Point(0, 0);
        java.awt.Point awtPoint = new java.awt.Point(3, 4);
        assertEquals(5.0, point.distance(awtPoint), 0.0001);
    }

    @Test
    @DisplayName("fromAwtPoint should create correct Point")
    void testFromAwtPoint() {
        java.awt.Point awtPoint = new java.awt.Point(15, 25);
        Point point = Point.fromAwtPoint(awtPoint);
        assertEquals(15, point.getX());
        assertEquals(25, point.getY());
    }

    @Test
    @DisplayName("fromAwtPoint should throw exception for null")
    void testFromAwtPointWithNull() {
        assertThrows(IllegalArgumentException.class, () -> Point.fromAwtPoint(null));
    }

    @Test
    @DisplayName("toAwtPoint should create correct AWT Point")
    void testToAwtPoint() {
        Point point = new Point(30, 40);
        java.awt.Point awtPoint = point.toAwtPoint();
        assertEquals(30, awtPoint.x);
        assertEquals(40, awtPoint.y);
    }

    @Test
    @DisplayName("Equals should return true for same coordinates")
    void testEquals() {
        Point p1 = new Point(10, 20);
        Point p2 = new Point(10, 20);
        Point p3 = new Point(15, 25);

        assertEquals(p1, p2, "Points with same coordinates should be equal");
        assertNotEquals(p1, p3, "Points with different coordinates should not be equal");
    }

    @Test
    @DisplayName("Equals should handle reflexivity")
    void testEqualsReflexive() {
        Point point = new Point(10, 20);
        assertEquals(point, point, "Point should equal itself");
    }

    @Test
    @DisplayName("Equals should handle null and different types")
    void testEqualsWithNullAndDifferentTypes() {
        Point point = new Point(10, 20);
        assertNotEquals(null, point, "Point should not equal null");
        assertNotEquals(point, "Not a Point", "Point should not equal different type");
    }

    @Test
    @DisplayName("HashCode should be consistent with equals")
    void testHashCode() {
        Point p1 = new Point(10, 20);
        Point p2 = new Point(10, 20);
        Point p3 = new Point(15, 25);

        assertEquals(p1.hashCode(), p2.hashCode(),
                "Equal points should have same hash code");
        // Note: Different points MAY have same hash code (collision),
        // but typically should differ
    }

    @Test
    @DisplayName("toString should return formatted string")
    void testToString() {
        Point point = new Point(10, 20);
        String str = point.toString();
        assertTrue(str.contains("10"), "toString should include x coordinate");
        assertTrue(str.contains("20"), "toString should include y coordinate");
        assertEquals("(10, 20)", str, "toString format should be (x, y)");
    }

    @Test
    @DisplayName("Point should be immutable")
    void testImmutability() {
        Point point = new Point(10, 20);
        int originalX = point.getX();
        int originalY = point.getY();

        // Attempt to modify via AWT conversion
        java.awt.Point awtPoint = point.toAwtPoint();
        awtPoint.x = 999;
        awtPoint.y = 999;

        // Original point should be unchanged
        assertEquals(originalX, point.getX(), "Point x should be immutable");
        assertEquals(originalY, point.getY(), "Point y should be immutable");
    }
}
