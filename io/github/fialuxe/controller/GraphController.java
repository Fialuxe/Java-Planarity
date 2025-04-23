package io.github.fialuxe.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import io.github.fialuxe.model.GraphModel;
import io.github.fialuxe.model.Point;
import io.github.fialuxe.view.PlanarityPanel;

//this class manages the mouse movement of planarity.
//このクラスはPlanarityにおけるマウスの動きを管理する。
public class GraphController extends MouseAdapter {
    private GraphModel model;
    private PlanarityPanel view;

    private int selectedNodeIndex = -1;

    public GraphController(GraphModel model, PlanarityPanel view) {
        this.model = model;
        this.view = view;

        view.addMouseListener(this);
        view.addMouseMotionListener(this);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        double minDistance = Double.MAX_VALUE;
        int closestIndex = -1;
        // ノードの選択
        for (int i = 0; i < model.getNodes().size(); i++) {
            Point node = model.getNodes().get(i);
            double distance = node.distance(e.getPoint());
            if (distance < minDistance && distance < 10) {
                minDistance = distance;
                closestIndex = i;
            }
        }
        if(closestIndex != -1){
            selectedNodeIndex = closestIndex;
        }else{
            selectedNodeIndex = -1;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // ノードの移動
        if (selectedNodeIndex != -1) {
            model.moveNode(selectedNodeIndex, e.getPoint());
            view.repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        selectedNodeIndex = -1;
    }
}
