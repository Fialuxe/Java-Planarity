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
    private int hoveredNodeIndex = -1;

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
        // ノードの選択（クリック範囲を少し広げる）
        for (int i = 0; i < model.getNodes().size(); i++) {
            Point node = model.getNodes().get(i);
            double distance = node.distance(e.getPoint());
            if (distance < minDistance && distance < 15) {
                minDistance = distance;
                closestIndex = i;
            }
        }
        if(closestIndex != -1){
            selectedNodeIndex = closestIndex;
            view.setDraggedNode(closestIndex);
        }else{
            selectedNodeIndex = -1;
            view.setDraggedNode(-1);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // ノードの移動
        if (selectedNodeIndex != -1) {
            // 画面外に出ないように制限
            int x = Math.max(20, Math.min(e.getX(), view.getWidth() - 20));
            int y = Math.max(20, Math.min(e.getY(), view.getHeight() - 20));
            model.moveNode(selectedNodeIndex, new java.awt.Point(x, y));
        }
    }
    
    @Override
    public void mouseMoved(MouseEvent e) {
        // ホバー効果
        int previousHovered = hoveredNodeIndex;
        hoveredNodeIndex = -1;
        
        for (int i = 0; i < model.getNodes().size(); i++) {
            Point node = model.getNodes().get(i);
            double distance = node.distance(e.getPoint());
            if (distance < 15) {
                hoveredNodeIndex = i;
                view.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                break;
            }
        }
        
        if(hoveredNodeIndex == -1){
            view.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        }
        
        if(previousHovered != hoveredNodeIndex){
            view.setHoveredNode(hoveredNodeIndex);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        selectedNodeIndex = -1;
        view.setDraggedNode(-1);
    }
}
