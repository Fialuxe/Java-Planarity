package io.github.fialuxe.view;

import java.util.*;
import java.util.List;

import io.github.fialuxe.model.*;
import io.github.fialuxe.model.Point;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("deprecation")
public class PlanarityPanel extends JPanel implements Observer{
    private GraphModel model;

    public PlanarityPanel(GraphModel model){
        this.model = model;
        model.addObserver(this);
    }

    @Override 
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        //エッジの描画
        List<Edge> edges = model.getEdges();
        for(Edge edge : edges){
            Point start = model.getNodes().get(edge.getStartIndex());
            Point end = model.getNodes().get(edge.getEndIndex());
            g.setColor(Color.BLACK);
            g.drawLine(start.getX(), start.getY(), end.getX(), end.getY());
        }

        //交差
        List<Edge> intersectingEdges = model.getIntersectingEdges();
        for(Edge edge : intersectingEdges){
            Point start = model.getNodes().get(edge.getStartIndex());
            Point end = model.getNodes().get(edge.getEndIndex());
            g.setColor(Color.RED);
            g.drawLine(start.getX(), start.getY(), end.getX(), end.getY());
        }

        for (Point node : model.getNodes()) {
            g.setColor(Color.BLUE);
            g.fillOval(node.getX() - 5, node.getY() - 5, 10, 10);
        }
    }

    @Override
    public void update(Observable o, Object arg){
        if(arg.equals("SOLVED")){
            System.out.println("SOLVED!");
            repaint();
        }else{
            repaint();
        }
        
    }
}
