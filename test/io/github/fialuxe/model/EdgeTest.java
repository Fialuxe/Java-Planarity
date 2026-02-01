package io.github.fialuxe.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Edge class.
 * 
 * Tests cover:
 * - Constructor validation (no self-loops, non-negative indices)
 * - Getters
 * - Vertex sharing detection
 * - Undirected edge equality semantics
 * - Equals and hashCode contracts
 * - Immutability guarantees
 */
@DisplayName("Edge Class Tests")
class EdgeTest {

    @Test
    @DisplayName("Constructor should initialize start and end indices")
    void testConstructor() {
        Edge edge = new Edge(0, 1);
        assertEquals(0, edge.getStartIndex());
        assertEquals(1, edge.getEndIndex());
    }

    @Test
    @DisplayName("Constructor should reject negative start index")
    void testConstructorRejectsNegativeStart() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Edge(-1, 5));
        assertTrue(exception.getMessage().contains("non-negative"));
    }

    @Test
    @DisplayName("Constructor should reject negative end index")
    void testConstructorRejectsNegativeEnd() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Edge(5, -1));
        assertTrue(exception.getMessage().contains("non-negative"));
    }

    @Test
    @DisplayName("Constructor should reject self-loops")
    void testConstructorRejectsSelfLoop() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Edge(3, 3));
        assertTrue(exception.getMessage().contains("Self-loops"));
    }

    @Test
    @DisplayName("Constructor should reject both negative indices")
    void testConstructorRejectsBothNegative() {
        assertThrows(IllegalArgumentException.class, () -> new Edge(-1, -2));
    }

    @Test
    @DisplayName("sharesVertex should detect shared start vertex")
    void testSharesVertexStartStart() {
        Edge e1 = new Edge(0, 1);
        Edge e2 = new Edge(0, 2);
        assertTrue(e1.sharesVertex(e2), "Edges sharing start vertex should be detected");
    }

    @Test
    @DisplayName("sharesVertex should detect shared end vertex")
    void testSharesVertexEndEnd() {
        Edge e1 = new Edge(0, 1);
        Edge e2 = new Edge(2, 1);
        assertTrue(e1.sharesVertex(e2), "Edges sharing end vertex should be detected");
    }

    @Test
    @DisplayName("sharesVertex should detect start-end sharing")
    void testSharesVertexStartEnd() {
        Edge e1 = new Edge(0, 1);
        Edge e2 = new Edge(1, 2);
        assertTrue(e1.sharesVertex(e2), "Edges sharing vertex (start-end) should be detected");
    }

    @Test
    @DisplayName("sharesVertex should return false for non-adjacent edges")
    void testSharesVertexNonAdjacent() {
        Edge e1 = new Edge(0, 1);
        Edge e2 = new Edge(2, 3);
        assertFalse(e1.sharesVertex(e2), "Non-adjacent edges should not share vertices");
    }

    @Test
    @DisplayName("sharesVertex should throw exception for null edge")
    void testSharesVertexWithNull() {
        Edge edge = new Edge(0, 1);
        assertThrows(IllegalArgumentException.class, () -> edge.sharesVertex(null));
    }

    @Test
    @DisplayName("Equals should implement undirected semantics")
    void testEqualsUndirected() {
        Edge e1 = new Edge(0, 1);
        Edge e2 = new Edge(1, 0); // Reversed
        assertEquals(e1, e2, "Edge(0,1) should equal Edge(1,0) (undirected)");
    }

    @Test
    @DisplayName("Equals should return true for identical edges")
    void testEqualsIdentical() {
        Edge e1 = new Edge(2, 5);
        Edge e2 = new Edge(2, 5);
        assertEquals(e1, e2, "Identical edges should be equal");
    }

    @Test
    @DisplayName("Equals should return false for different edges")
    void testEqualsDifferent() {
        Edge e1 = new Edge(0, 1);
        Edge e2 = new Edge(0, 2);
        assertNotEquals(e1, e2, "Different edges should not be equal");
    }

    @Test
    @DisplayName("Equals should handle reflexivity")
    void testEqualsReflexive() {
        Edge edge = new Edge(3, 7);
        assertEquals(edge, edge, "Edge should equal itself");
    }

    @Test
    @DisplayName("Equals should handle null and different types")
    void testEqualsWithNullAndDifferentTypes() {
        Edge edge = new Edge(0, 1);
        assertNotEquals(null, edge, "Edge should not equal null");
        assertNotEquals(edge, "Not an Edge", "Edge should not equal different type");
    }

    @Test
    @DisplayName("HashCode should be consistent with undirected equality")
    void testHashCodeUndirected() {
        Edge e1 = new Edge(0, 1);
        Edge e2 = new Edge(1, 0);
        assertEquals(e1.hashCode(), e2.hashCode(),
                "Edge(0,1) and Edge(1,0) should have same hash code (undirected)");
    }

    @Test
    @DisplayName("HashCode should be consistent with equals")
    void testHashCodeConsistentWithEquals() {
        Edge e1 = new Edge(3, 7);
        Edge e2 = new Edge(3, 7);
        Edge e3 = new Edge(7, 3); // Reversed

        assertEquals(e1.hashCode(), e2.hashCode(),
                "Equal edges should have same hash code");
        assertEquals(e1.hashCode(), e3.hashCode(),
                "Undirected equal edges should have same hash code");
    }

    @Test
    @DisplayName("toString should return formatted string")
    void testToString() {
        Edge edge = new Edge(5, 10);
        String str = edge.toString();
        assertTrue(str.contains("5"), "toString should include start index");
        assertTrue(str.contains("10"), "toString should include end index");
        assertTrue(str.contains("Edge"), "toString should indicate it's an Edge");
    }

    @Test
    @DisplayName("Edge should be immutable")
    void testImmutability() {
        Edge edge = new Edge(2, 8);
        int originalStart = edge.getStartIndex();
        int originalEnd = edge.getEndIndex();

        // No setters exist, but verify values don't change
        assertEquals(originalStart, edge.getStartIndex(),
                "Start index should be immutable");
        assertEquals(originalEnd, edge.getEndIndex(),
                "End index should be immutable");
    }

    @Test
    @DisplayName("Multiple edges can use same indices")
    void testMultipleEdgesWithSameIndices() {
        // Should be able to create multiple Edge objects with same values
        Edge e1 = new Edge(0, 1);
        Edge e2 = new Edge(0, 1);
        Edge e3 = new Edge(1, 0);

        // All should be equal
        assertEquals(e1, e2);
        assertEquals(e1, e3);
        assertEquals(e2, e3);
    }
}
