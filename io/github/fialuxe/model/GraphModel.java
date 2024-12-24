package io.github.fialuxe.model;

import java.util.*;

@SuppressWarnings("deprecation")
public class GraphModel extends Observable{
    private List<Point> nodes;
    private List<Edge> edges;

    public GraphModel(){
        nodes = new ArrayList<Point>();
        edges = new ArrayList<Edge>();
    }

    //ノードを追加する関数
    public void addNode(Point node){
        nodes.add(node);
        notifyObservers();
    }

    //ノードの添字を二つ保存し、Edgeとして追加する関数。
    //edgeはnodesの中のListの添字を保存するのでint
    public void addEdge(int start, int end){
        edges.add(new Edge(start, end));
        notifyObservers();
    }

    //indexに保存されているノードの座標をnewPositionへ移動する関数
    public void moveNode(int index, Point newPosition){
        nodes.set(index, newPosition);
        notifyObservers();
    }

    public void moveNode(int index, java.awt.Point newPosition){
        Point p = new Point((int)newPosition.getX(), (int)newPosition.getY());
        nodes.set(index, p);
        System.out.println(isGameSolved());
        if(isGameSolved()){
            notifyObservers("SOLVED");
        }else{
            notifyObservers("UNSOLVED");
        }
    }

    public List<Edge> getIntersectingEdges(){
        Set<Edge> intersectingEdges = new HashSet<Edge>();
        for(int i = 0; i < edges.size(); i++){
            for(int j = i + 1; j < edges.size(); j++){
                if(isIntersecting(edges.get(i), edges.get(j))){
                    intersectingEdges.add(edges.get(i));
                    intersectingEdges.add(edges.get(j));
                }
            }
        }
        return new ArrayList<>(intersectingEdges);
    }

    public boolean isGameSolved(){
        List<Edge> intersectList = getIntersectingEdges();
        if(intersectList == null || intersectList.size() == 0 ){
            return true;
        }else{
            return false;
        }
    }

    private boolean isIntersecting(Edge e1, Edge e2){
        //e1,e2の辺を作るノードを取得する
        Point e1Start = nodes.get(e1.getStartIndex());
        Point e1End = nodes.get(e1.getEndIndex());
        Point e2Start = nodes.get(e2.getStartIndex());
        Point e2End = nodes.get(e2.getEndIndex());

        if (e1.getStartIndex() == e2.getStartIndex() ||
            e1.getStartIndex() == e2.getEndIndex() ||
            e1.getEndIndex() == e2.getStartIndex() ||
            e1.getEndIndex() == e2.getEndIndex()) {
            return false;
        }

        return isCrossing(e1Start, e1End, e2Start, e2End);
    }

    //a,bとc,dにより結ばれる線分が交わるかを検証する
    private boolean isCrossing(Point a, Point b, Point c, Point d) {
        double cross1 = crossProduct(b, a, c) * crossProduct(b, a, d);
        double cross2 = crossProduct(d, c, a) * crossProduct(d, c, b);
    
        // 条件1: 線分の端点が異なる側にある
        boolean differentSides = cross1 < 0 && cross2 < 0;
    
        // 条件2: 端点が線分上にある場合を考慮
        boolean endpointOnLine = (crossProduct(b, a, c) == 0 && isOnSegment(a, b, c)) ||
                                 (crossProduct(b, a, d) == 0 && isOnSegment(a, b, d)) ||
                                 (crossProduct(d, c, a) == 0 && isOnSegment(c, d, a)) ||
                                 (crossProduct(d, c, b) == 0 && isOnSegment(c, d, b));
    
        return differentSides || endpointOnLine;
    }
    
    // 点pが線分ab上に存在するか判定
    private boolean isOnSegment(Point a, Point b, Point p) {
        return Math.min(a.getX(), b.getX()) <= p.getX() && p.getX() <= Math.max(a.getX(), b.getX()) &&
               Math.min(a.getY(), b.getY()) <= p.getY() && p.getY() <= Math.max(a.getY(), b.getY());
    }

    private double crossProduct(Point p1, Point p2, Point p3){
        return (p3.getX() - p1.getX()) * (p2.getY() - p1.getY()) - (p3.getY() - p1.getY()) * (p2.getX() - p1.getX());
    }

    // ノードとエッジの取得
    public List<Point> getNodes() {
        return nodes;
    }
    
    public List<Edge> getEdges() {
        return edges;
    }
    
}