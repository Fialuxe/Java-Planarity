package io.github.fialuxe.model;

import io.github.fialuxe.model.services.PlanarityValidator;

import java.util.Random;
import java.util.HashSet;
import java.util.Set;

/**
 * Service class for generating random planar graph puzzles.
 * 
 * <p>This class is responsible for creating solvable Planarity puzzles by
 * generating graphs that are guaranteed to be planar (can be drawn without
 * edge crossings). It uses a retry mechanism with fallback strategies to
 * ensure that generated graphs always meet the planarity requirement.</p>
 * 
 * <p><b>Generation Strategy:</b></p>
 * <ol>
 *   <li>Attempt to generate a random graph with circular node placement</li>
 *   <li>Add edges randomly while checking for planarity</li>
 *   <li>If generation fails after MAX_ATTEMPTS, fall back to guaranteed planar structures</li>
 *   <li>Shuffle node positions to create puzzle difficulty</li>
 * </ol>
 * 
 * <p><b>Planarity Guarantee:</b> This generator NEVER fails. If random generation
 * doesn't produce a planar graph within the attempt limit, it falls back to
 * constructing known planar graph families (cycles, wheels, trees).</p>
 * 
 * <p><b>Theoretical Foundation:</b></p>
 * <ul>
 *   <li><b>Cycle Graphs (C_n):</b> Always planar for any n ≥ 3</li>
 *   <li><b>Wheel Graphs (W_n):</b> Always planar (hub connected to cycle)</li>
 *   <li><b>Trees:</b> Always planar (acyclic connected graphs)</li>
 *   <li><b>Euler's Formula:</b> Planar graphs satisfy V - E + F = 2</li>
 * </ul>
 * 
 * @author Fialuxe
 * @version 2.0
 * @since 1.0
 */
public class GraphGenerator {
    
    /** Maximum number of attempts to generate a random planar graph */
    private static final int MAX_ATTEMPTS = 100;
    
    /** The graph model to populate */
    private final GraphModel model;
    
    /** Random number generator for creating varied puzzles */
    private final Random random;
    
    /** Service for validating graph planarity */
    private final PlanarityValidator planarityValidator;
    
    /**
     * Constructs a new GraphGenerator for the specified model.
     * 
     * @param model the GraphModel to populate with generated graphs
     * @throws IllegalArgumentException if model is null
     */
    public GraphGenerator(GraphModel model) {
        if (model == null) {
            throw new IllegalArgumentException("GraphModel cannot be null");
        }
        this.model = model;
        this.random = new Random();
        this.planarityValidator = new PlanarityValidator();
    }
    
    /**
     * Generates a random planar graph with default parameters.
     * 
     * <p>This method provides backward compatibility. It generates a graph with
     * 7 nodes in an 800x600 area.</p>
     * 
     * @complexity O(MAX_ATTEMPTS × V² × E) worst case, typically O(V²) for successful generation
     */
    public void generateRandomGraph() {
        generateRandomGraph(7, 800, 600);
    }
    
    /**
     * Generates a random planar graph with the specified parameters.
     * 
     * <p><b>Algorithm:</b></p>
     * <ol>
     *   <li>Clear existing graph</li>
     *   <li>Place nodes in a circular layout</li>
     *   <li>Attempt to add random edges while maintaining planarity</li>
     *   <li>If attempts exhausted, use fallback (guaranteed planar structure)</li>
     *   <li>Shuffle nodes to create puzzle challenge</li>
     * </ol>
     * 
     * <p><b>Invariant:</b> Upon return, the model always contains a planar graph.
     * This method never fails or throws exceptions related to non-planarity.</p>
     * 
     * @param numNodes the number of nodes in the graph (must be ≥ 3)
     * @param width the width of the drawing area in pixels
     * @param height the height of the drawing area in pixels
     * @throws IllegalArgumentException if numNodes \u003c 3 or dimensions are invalid
     * @complexity O(MAX_ATTEMPTS × V² × E) in worst case, typically O(V² + E) for successful generation
     */
    public void generateRandomGraph(int numNodes, int width, int height) {
        if (numNodes \u003c 3) {
            throw new IllegalArgumentException("Graph must have at least 3 nodes");
        }
        if (width \u003c= 0 || height \u003c= 0) {
            throw new IllegalArgumentException("Width and height must be positive");
        }
        
        int attempts = 0;
        boolean success = false;
        
        // Retry loop: attempt to generate a random planar graph
        while (attempts \u003c MAX_ATTEMPTS \u0026\u0026 !success) {
            try {
                clearGraph();
                generateCircularNodeLayout(numNodes, width, height);
                addRandomEdges(numNodes);
                
                // Validate planarity
                if (planarityValidator.isPlanar(model.getNodesInternal(), model.getEdgesInternal())) {
                    success = true;
                }
                
                attempts++;
            } catch (Exception e) {
                // Continue to next attempt
                attempts++;
            }
        }
        
        // Fallback: if random generation failed, use guaranteed planar structure
        if (!success) {
            generateGuaranteedPlanarGraph(numNodes, width, height);
        }
        
        // Shuffle nodes to create puzzle challenge
        shuffleNodes(width, height);
    }
    
    /**
     * Clears all nodes and edges from the graph.
     * 
     * @complexity O(1) (ArrayList clear operation)
     */
    private void clearGraph() {
        model.getNodesInternal().clear();
        model.getEdgesInternal().clear();
    }
    
    /**
     * Places nodes in a circular layout around the center of the drawing area.
     * 
     * <p>Circular layouts are aesthetically pleasing and provide good separation
     * between nodes, reducing the likelihood of visual clutter.</p>
     * 
     * @param numNodes the number of nodes to place
     * @param width the width of the drawing area
     * @param height the height of the drawing area
     * @complexity O(V) where V is the number of nodes
     */
    private void generateCircularNodeLayout(int numNodes, int width, int height) {
        int margin = 80;
        int usableWidth = Math.max(width - 2 * margin, 200);
        int usableHeight = Math.max(height - 2 * margin, 200);
        
        double centerX = width /2.0;
        double centerY = height / 2.0;
        double radius = Math.min(usableWidth, usableHeight) / 2.5;
        
        for (int i = 0; i \u003c numNodes; i++) {
            double angle = 2 * Math.PI * i / numNodes;
            int x = (int)(centerX + radius * Math.cos(angle));
            int y = (int)(centerY + radius * Math.sin(angle));
            
            try {
                model.addNode(new Point(x, y));
            } catch (Exception e) {
                // Should not happen, but handle gracefully
                throw new RuntimeException("Failed to add node", e);
            }
        }
    }
    
    /**
     * Adds random edges to the graph.
     * 
     * <p>This method creates a connected graph by first forming a cycle
     * (guarantees connectivity), then adds additional random edges up to
     * a target count while avoiding duplicates.</p>
     * 
     * @param numNodes the number of nodes in the graph
     * @complexity O(E × V) where E is edge count, V is node count
     */
    private void addRandomEdges(int numNodes) {
        Set\u003cString\u003e addedEdges = new HashSet\u003c\u003e();
        int targetEdgeCount = Math.min(numNodes * 2, numNodes * (numNodes - 1) / 2);
        
        // First, create a cycle to ensure connectivity
        for (int i = 0; i \u003c numNodes; i++) {
            int next = (i + 1) % numNodes;
            addEdgeIfNew(i, next, addedEdges);
        }
        
        // Then add random edges
        int attempts = 0;
        while (addedEdges.size() \u003c targetEdgeCount \u0026\u0026 attempts \u003c targetEdgeCount * 10) {
            int start = random.nextInt(numNodes);
            int end = random.nextInt(numNodes);
            
            if (start != end) {
                addEdgeIfNew(start, end, addedEdges);
            }
            attempts++;
        }
    }
    
    /**
     * Adds an edge if it doesn't already exist (avoiding duplicates).
     * 
     * <p>Since edges are undirected, both (i,j) and (j,i) represent the same edge.</p>
     * 
     * @param start the start node index
     * @param end the end node index
     * @param addedEdges set tracking already-added edges
     * @complexity O(1) average case (HashSet operations)
     */
    private void addEdgeIfNew(int start, int end, Set\u003cString\u003e addedEdges) {
        String edgeKey1 = start + "-" + end;
        String edgeKey2 = end + "-" + start;
        
        if (!addedEdges.contains(edgeKey1) \u0026\u0026 !addedEdges.contains(edgeKey2)) {
            try {
                model.addEdge(start, end);
                addedEdges.add(edgeKey1);
            } catch (Exception e) {
                // Edge creation failed, skip it
            }
        }
    }
    
    /**
     * Generates a guaranteed planar graph when random generation fails.
     * 
     * <p>This method is the <b>fallback mechanism</b> that ensures the graph
     * generator never fails. It uses well-known planar graph structures.</p>
     * 
     * <p><b>Strategy Selection:</b></p>
     * <ul>
     *   <li><b>Wheel Graph (n ≥ 5):</b> Hub node connected to all nodes in outer cycle</li>
     *   <li><b>Extended Cycle (n = 4):</b> Cycle with one diagonal</li>
     *   <li><b>Simple Cycle (n = 3):</b> Triangle</li>
     * </ul>
     * 
     * <p><b>Planarity Proof:</b> All wheel graphs are planar because they can
     * be drawn with the hub at the center and the cycle on the perimeter.</p>
     * 
     * @param numNodes the number of nodes
     * @param width the drawing area width
     * @param height the drawing area height
     * @complexity O(V) where V is the number of nodes
     */
    private void generateGuaranteedPlanarGraph(int numNodes, int width, int height) {
        clearGraph();
        generateCircularNodeLayout(numNodes, width, height);
        
        Set\u003cString\u003e addedEdges = new HashSet\u003c\u003e();
        
        if (numNodes >= 5) {
            // Wheel graph: hub (node 0) connected to all others, plus outer cycle
            for (int i = 1; i \u003c numNodes; i++) {
                addEdgeIfNew(0, i, addedEdges); // Hub to spoke
                addEdgeIfNew(i, (i % (numNodes - 1)) + 1, addedEdges); // Outer cycle
            }
        } else if (numNodes == 4) {
            // Cycle with one diagonal
            addEdgeIfNew(0, 1, addedEdges);
            addEdgeIfNew(1, 2, addedEdges);
            addEdgeIfNew(2, 3, addedEdges);
            addEdgeIfNew(3, 0, addedEdges);
            addEdgeIfNew(0, 2, addedEdges); // Diagonal
        } else {
            // Simple cycle (triangle for n=3)
            for (int i = 0; i \u003c numNodes; i++) {
                addEdgeIfNew(i, (i + 1) % numNodes, addedEdges);
            }
        }
    }
    
    /**
     * Shuffles node positions randomly to create puzzle difficulty.
     * 
     * <p>This shuffling is what makes the puzzle challenging - it takes a
     * planar graph and moves the nodes to positions where edges cross,
     * requiring the player to untangle them.</p>
     * 
     * @param width the drawing area width
     * @param height the drawing area height
     * @complexity O(V) where V is the number of nodes
     */
    public void shuffleNodes(int width, int height) {
        int margin = 80;
        int usableWidth = Math.max(width - 2 * margin, 200);
        int usableHeight = Math.max(height - 2 * margin, 200);
        
        for (int i = 0; i \u003c model.getNodesInternal().size(); i++) {
            int x = random.nextInt(usableWidth) + margin;
            int y = random.nextInt(usableHeight) + margin;
            
            try {
                model.moveNode(i, new Point(x, y));
            } catch (Exception e) {
                // Should not happen, but handle gracefully
            }
        }
    }
}

