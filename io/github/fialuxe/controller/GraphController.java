package io.github.fialuxe.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Cursor;

import io.github.fialuxe.model.GraphModel;
import io.github.fialuxe.model.Point;
import io.github.fialuxe.view.PlanarityPanel;

/**
 * Controller for handling user mouse interactions with the graph.
 * 
 * <p>
 * This controller implements the Controller component of the MVC pattern,
 * managing user input and updating the model accordingly. It handles:
 * </p>
 * <ul>
 * <li>Node selection and dragging</li>
 * <li>Hover effects for visual feedback</li>
 * <li>Boundary constraints to keep nodes within the viewable area</li>
 * </ul>
 * 
 * <p>
 * <b>Design Pattern:</b> This class uses the MouseAdapter pattern to
 * selectively override only the mouse events of interest.
 * </p>
 * 
 * @author Fialuxe
 * @version 2.0
 * @since 1.0
 */
public class GraphController extends MouseAdapter {

    /** The graph model containing node and edge data */
    private final GraphModel model;

    /** The view panel for rendering and user interaction */
    private final PlanarityPanel view;

    /** Index of currently selected (being dragged) node, -1 if none */
    private int selectedNodeIndex = -1;

    /** Index of currently hovered node, -1 if none */
    private int hoveredNodeIndex = -1;

    /** Click detection radius in pixels */
    private static final int CLICK_RADIUS = 15;

    /** Minimum distance from screen edge for node positions */
    private static final int EDGE_MARGIN = 20;

    /**
     * Constructs a new GraphController.
     * 
     * <p>
     * Automatically registers itself as a mouse listener and
     * mouse motion listener on the view panel.
     * </p>
     * 
     * @param model the graph model to control
     * @param view  the view panel to receive input from
     * @throws IllegalArgumentException if model or view is null
     */
    public GraphController(GraphModel model, PlanarityPanel view) {
        if (model == null) {
            throw new IllegalArgumentException("Model cannot be null");
        }
        if (view == null) {
            throw new IllegalArgumentException("View cannot be null");
        }

        this.model = model;
        this.view = view;

        view.addMouseListener(this);
        view.addMouseMotionListener(this);
    }

    /**
     * Handles mouse press events to select a node for dragging.
     * 
     * <p>
     * Finds the closest node within the click radius to the mouse
     * position. If a node is found, it becomes the selected node.
     * </p>
     * 
     * @param e the mouse event
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (e == null)
            return;

        double minDistance = Double.MAX_VALUE;
        int closestIndex = -1;

        // Find closest node within click radius
        for (int i = 0; i < model.getNodes().size(); i++) {
            Point node = model.getNodes().get(i);
            double distance = node.distance(e.getPoint());

            if (distance < minDistance && distance < CLICK_RADIUS) {
                minDistance = distance;
                closestIndex = i;
            }
        }

        // Update selection state
        if (closestIndex != -1) {
            selectedNodeIndex = closestIndex;
            view.setDraggedNode(closestIndex);
        } else {
            selectedNodeIndex = -1;
            view.setDraggedNode(-1);
        }
    }

    /**
     * Handles mouse drag events to move the selected node.
     * 
     * <p>
     * Updates the position of the currently selected node,
     * constraining it to stay within the view boundaries with
     * a margin from the edges.
     * </p>
     * 
     * @param e the mouse event
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        if (e == null)
            return;

        // Only process if a node is selected
        if (selectedNodeIndex != -1) {
            // Constrain position to view bounds
            int x = Math.max(EDGE_MARGIN,
                    Math.min(e.getX(), view.getWidth() - EDGE_MARGIN));
            int y = Math.max(EDGE_MARGIN,
                    Math.min(e.getY(), view.getHeight() - EDGE_MARGIN));

            try {
                // Update model (triggers view update via Observer pattern)
                model.moveNode(selectedNodeIndex, new java.awt.Point(x, y));
            } catch (Exception ex) {
                // Log error but don't crash the UI
                System.err.println("Error moving node: " + ex.getMessage());
                // Clear selection on error
                selectedNodeIndex = -1;
                view.setDraggedNode(-1);
            }
        }
    }

    /**
     * Handles mouse movement to provide hover feedback.
     * 
     * <p>
     * Detects when the mouse is over a node and updates the cursor
     * and view highlighting accordingly. This provides visual feedback
     * that nodes are interactive.
     * </p>
     * 
     * @param e the mouse event
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        if (e == null)
            return;

        int previousHovered = hoveredNodeIndex;
        hoveredNodeIndex = -1;

        // Find hovered node
        for (int i = 0; i < model.getNodes().size(); i++) {
            Point node = model.getNodes().get(i);
            double distance = node.distance(e.getPoint());

            if (distance < CLICK_RADIUS) {
                hoveredNodeIndex = i;
                view.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                break;
            }
        }

        // Reset cursor if not hovering over any node
        if (hoveredNodeIndex == -1) {
            view.setCursor(Cursor.getDefaultCursor());
        }

        // Update view if hover state changed
        if (previousHovered != hoveredNodeIndex) {
            view.setHoveredNode(hoveredNodeIndex);
        }
    }

    /**
     * Handles mouse release events to deselect the dragged node.
     * 
     * <p>
     * Clears the selection state, allowing the user to select
     * a different node on the next click.
     * </p>
     * 
     * @param e the mouse event
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        selectedNodeIndex = -1;
        view.setDraggedNode(-1);
    }

    /**
     * Gets the index of the currently selected node.
     * 
     * @return the selected node index, or -1 if no node is selected
     */
    public int getSelectedNodeIndex() {
        return selectedNodeIndex;
    }

    /**
     * Gets the index of the currently hovered node.
     * 
     * @return the hovered node index, or -1 if no node is hovered
     */
    public int getHoveredNodeIndex() {
        return hoveredNodeIndex;
    }
}
