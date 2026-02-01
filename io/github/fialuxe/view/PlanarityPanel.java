package io.github.fialuxe.view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;
import java.util.List;

import io.github.fialuxe.model.*;
import io.github.fialuxe.model.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

/**
 * Custom JPanel for rendering the Planarity game.
 * 
 * <p>This panel listens to PropertyChangeEvents from the GraphModel and
 * repaints itself when the graph state changes.</p>
 * 
 * @author Fialuxe
 * @version 2.0
 */
public class PlanarityPanel extends JPanel implements PropertyChangeListener {
    private GraphModel model;
    private int hoveredNodeIndex = -1;
    private int draggedNodeIndex = -1;
    private boolean showSolvedMessage = false;
    private long solvedTime = 0;
    private LanguageChangeListener languageListener;
    
    // カラーパレット
    private static final Color BG_COLOR = new Color(250, 250, 252);
    private static final Color EDGE_COLOR = new Color(100, 120, 140);
    private static final Color INTERSECT_COLOR = new Color(220, 60, 80);
    private static final Color NODE_COLOR = new Color(70, 130, 180);
    private static final Color NODE_HOVER_COLOR = new Color(100, 160, 220);
    private static final Color NODE_DRAG_COLOR = new Color(50, 200, 150);
    private static final Color SOLVED_COLOR = new Color(76, 175, 80);
    private static final Color TEXT_COLOR = new Color(50, 50, 60);
    
    public PlanarityPanel(GraphModel model){
        this.model = model;
        model.addPropertyChangeListener(this);
        setBackground(BG_COLOR);
        setPreferredSize(new Dimension(800, 600));
    }
    
    public void setLanguageChangeListener(LanguageChangeListener listener) {
        this.languageListener = listener;
    }
    
    public interface LanguageChangeListener {
        void onLanguageChanged();
    }

    @Override 
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        // アンチエイリアシングを有効化
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        
        // 交差しているエッジのリスト取得
        List<Edge> intersectingEdges = model.getIntersectingEdges();
        Set<Edge> intersectSet = new HashSet<>(intersectingEdges);
        
        // エッジの描画（通常のエッジ）
        g2d.setStroke(new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        List<Edge> edges = model.getEdges();
        for(Edge edge : edges){
            if(!intersectSet.contains(edge)){
                Point start = model.getNodes().get(edge.getStartIndex());
                Point end = model.getNodes().get(edge.getEndIndex());
                
                if(model.isGameSolved()){
                    g2d.setColor(SOLVED_COLOR);
                } else {
                    g2d.setColor(EDGE_COLOR);
                }
                g2d.drawLine(start.getX(), start.getY(), end.getX(), end.getY());
            }
        }
        
        // 交差しているエッジの描画（目立つように）
        g2d.setStroke(new BasicStroke(3.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        for(Edge edge : intersectingEdges){
            Point start = model.getNodes().get(edge.getStartIndex());
            Point end = model.getNodes().get(edge.getEndIndex());
            g2d.setColor(INTERSECT_COLOR);
            g2d.drawLine(start.getX(), start.getY(), end.getX(), end.getY());
        }
        
        // 交差点にマーカーを表示
        drawIntersectionMarkers(g2d, intersectingEdges);
        
        // ノードの描画
        for (int i = 0; i < model.getNodes().size(); i++) {
            Point node = model.getNodes().get(i);
            drawNode(g2d, node, i);
        }
        
        // 情報表示
        drawInfoPanel(g2d);
        
        // 解決メッセージの表示
        if(showSolvedMessage && System.currentTimeMillis() - solvedTime < 3000){
            drawSolvedMessage(g2d);
        }
    }
    
    private void drawNode(Graphics2D g2d, Point node, int index){
        int nodeSize = 20;
        int x = node.getX();
        int y = node.getY();
        
        // ノードの状態に応じた色選択
        Color nodeColor = NODE_COLOR;
        if(index == draggedNodeIndex){
            nodeColor = NODE_DRAG_COLOR;
            nodeSize = 24;
        } else if(index == hoveredNodeIndex){
            nodeColor = NODE_HOVER_COLOR;
            nodeSize = 22;
        }
        
        // シャドウ効果
        g2d.setColor(new Color(0, 0, 0, 30));
        g2d.fillOval(x - nodeSize/2 + 2, y - nodeSize/2 + 2, nodeSize, nodeSize);
        
        // グラデーション効果
        GradientPaint gradient = new GradientPaint(
            x - nodeSize/2, y - nodeSize/2, nodeColor.brighter(),
            x + nodeSize/2, y + nodeSize/2, nodeColor.darker()
        );
        g2d.setPaint(gradient);
        g2d.fillOval(x - nodeSize/2, y - nodeSize/2, nodeSize, nodeSize);
        
        // 外枠
        g2d.setColor(nodeColor.darker());
        g2d.setStroke(new BasicStroke(2f));
        g2d.drawOval(x - nodeSize/2, y - nodeSize/2, nodeSize, nodeSize);
        
        // ハイライト
        g2d.setColor(new Color(255, 255, 255, 100));
        g2d.fillOval(x - nodeSize/4, y - nodeSize/4, nodeSize/3, nodeSize/3);
    }
    
    private void drawIntersectionMarkers(Graphics2D g2d, List<Edge> intersectingEdges){
        Set<java.awt.Point> intersections = new HashSet<>();
        
        for(int i = 0; i < intersectingEdges.size(); i++){
            for(int j = i + 1; j < intersectingEdges.size(); j++){
                Edge e1 = intersectingEdges.get(i);
                Edge e2 = intersectingEdges.get(j);
                
                Point e1Start = model.getNodes().get(e1.getStartIndex());
                Point e1End = model.getNodes().get(e1.getEndIndex());
                Point e2Start = model.getNodes().get(e2.getStartIndex());
                Point e2End = model.getNodes().get(e2.getEndIndex());
                
                java.awt.Point intersection = getIntersectionPoint(e1Start, e1End, e2Start, e2End);
                if(intersection != null){
                    intersections.add(intersection);
                }
            }
        }
        
        // 交差点にマーカーを描画
        for(java.awt.Point p : intersections){
            g2d.setColor(new Color(255, 255, 255, 200));
            g2d.fillOval(p.x - 6, p.y - 6, 12, 12);
            g2d.setColor(INTERSECT_COLOR);
            g2d.setStroke(new BasicStroke(2f));
            g2d.drawOval(p.x - 6, p.y - 6, 12, 12);
        }
    }
    
    private java.awt.Point getIntersectionPoint(Point a, Point b, Point c, Point d){
        double x1 = a.getX(), y1 = a.getY();
        double x2 = b.getX(), y2 = b.getY();
        double x3 = c.getX(), y3 = c.getY();
        double x4 = d.getX(), y4 = d.getY();
        
        double denom = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
        if(Math.abs(denom) < 1e-10) return null;
        
        double t = ((x1 - x3) * (y3 - y4) - (y1 - y3) * (x3 - x4)) / denom;
        double u = -((x1 - x2) * (y1 - y3) - (y1 - y2) * (x1 - x3)) / denom;
        
        if(t >= 0 && t <= 1 && u >= 0 && u <= 1){
            int x = (int)(x1 + t * (x2 - x1));
            int y = (int)(y1 + t * (y2 - y1));
            return new java.awt.Point(x, y);
        }
        return null;
    }
    
    private void drawInfoPanel(Graphics2D g2d){
        int intersectionCount = model.getIntersectingEdges().size() / 2;
        
        // 背景パネル
        g2d.setColor(new Color(255, 255, 255, 230));
        g2d.fillRoundRect(10, 10, 280, 100, 15, 15);
        g2d.setColor(new Color(200, 200, 200));
        g2d.setStroke(new BasicStroke(2f));
        g2d.drawRoundRect(10, 10, 280, 100, 15, 15);
        
        // タイトル
        g2d.setColor(TEXT_COLOR);
        g2d.setFont(new Font("Meiryo", Font.BOLD, 20));
        g2d.drawString(LanguageManager.getText("game.title"), 20, 35);
        
        // 説明テキスト
        g2d.setFont(new Font("Meiryo", Font.PLAIN, 12));
        g2d.drawString(LanguageManager.getText("game.description"), 20, 55);
        
        // 交差数表示
        g2d.setFont(new Font("Meiryo", Font.BOLD, 14));
        if(model.isGameSolved()){
            g2d.setColor(SOLVED_COLOR);
            g2d.drawString(LanguageManager.getText("info.cleared"), 20, 80);
        } else {
            g2d.setColor(intersectionCount > 0 ? INTERSECT_COLOR : TEXT_COLOR);
            g2d.drawString(LanguageManager.getText("info.intersections") + intersectionCount, 20, 80);
        }
        
        // ノード数とエッジ数
        g2d.setColor(TEXT_COLOR);
        g2d.setFont(new Font("Meiryo", Font.PLAIN, 11));
        g2d.drawString(LanguageManager.getText("info.stats", model.getNodes().size(), model.getEdges().size()), 20, 98);
    }
    
    private void drawSolvedMessage(Graphics2D g2d){
        int width = getWidth();
        int height = getHeight();
        
        // 半透明の背景
        g2d.setColor(new Color(0, 0, 0, 100));
        g2d.fillRect(0, 0, width, height);
        
        // メッセージボックス
        int boxWidth = 400;
        int boxHeight = 150;
        int boxX = (width - boxWidth) / 2;
        int boxY = (height - boxHeight) / 2;
        
        g2d.setColor(new Color(255, 255, 255, 250));
        g2d.fillRoundRect(boxX, boxY, boxWidth, boxHeight, 20, 20);
        g2d.setColor(SOLVED_COLOR);
        g2d.setStroke(new BasicStroke(4f));
        g2d.drawRoundRect(boxX, boxY, boxWidth, boxHeight, 20, 20);
        
        // おめでとうメッセージ
        g2d.setColor(SOLVED_COLOR);
        g2d.setFont(new Font("Meiryo", Font.BOLD, 36));
        String message = LanguageManager.getText("solved.title");
        FontMetrics fm = g2d.getFontMetrics();
        int messageWidth = fm.stringWidth(message);
        g2d.drawString(message, boxX + (boxWidth - messageWidth) / 2, boxY + 60);
        
        g2d.setFont(new Font("Meiryo", Font.PLAIN, 18));
        g2d.setColor(TEXT_COLOR);
        String subMessage = LanguageManager.getText("solved.message");
        messageWidth = g2d.getFontMetrics().stringWidth(subMessage);
        g2d.drawString(subMessage, boxX + (boxWidth - messageWidth) / 2, boxY + 95);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // Handle property change events from GraphModel
        String propertyName = evt.getPropertyName();
        
        if (GraphModel.PROPERTY_GAME_SOLVED.equals(propertyName)) {
            Boolean isSolved = (Boolean) evt.getNewValue();
            if (isSolved != null && isSolved) {
                showSolvedMessage = true;
                solvedTime = System.currentTimeMillis();
                System.out.println("SOLVED!");
            }
        }
        
        // Repaint on any graph change
        repaint();
    }
    
    public void setHoveredNode(int index){
        this.hoveredNodeIndex = index;
        repaint();
    }
    
    public void setDraggedNode(int index){
        this.draggedNodeIndex = index;
        repaint();
    }
}

