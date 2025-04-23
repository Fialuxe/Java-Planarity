package io.github.fialuxe.model;

import java.util.*;

// NOTE: Observableは非推奨だが、講義の都合で使用（デモ目的）
// グラフ構造の状態変更を通知するために使用
@SuppressWarnings("deprecation") 
public class GraphModel extends Observable {

    // ノード（点）一覧
    private List<Point> nodes;

    // 辺（エッジ）の一覧。各Edgeはノードのインデックスのペア
    private List<Edge> edges;

    public GraphModel() {
        nodes = new ArrayList<Point>();
        edges = new ArrayList<Edge>();
    }

    // ノードをグラフに追加し、オブザーバに変更を通知
    public void addNode(Point node) {
        nodes.add(node);
        notifyObservers(); // 状態変更の通知（描画更新など）
    }

    // 指定されたノードインデックスのペアでエッジを作成し、追加
    public void addEdge(int start, int end) {
        edges.add(new Edge(start, end));
        notifyObservers(); // 状態変更の通知
    }

    // 指定されたインデックスのノードを新しい位置に更新（Point使用版）
    public void moveNode(int index, Point newPosition) {
        nodes.set(index, newPosition);
        notifyObservers(); // 再描画や状態更新のトリガー
    }

    // java.awt.Pointに対応するオーバーロード関数
    // ゲームの「解決済み/未解決」状態に応じて通知内容を切り替え
    public void moveNode(int index, java.awt.Point newPosition) {
        Point p = new Point((int)newPosition.getX(), (int)newPosition.getY());
        nodes.set(index, p);

        System.out.println(isGameSolved());

        // ゲーム解決状態に応じた通知
        if (isGameSolved()) {
            notifyObservers("SOLVED");
        } else {
            notifyObservers("UNSOLVED");
        }
    }

    // 線分同士が交差しているすべての辺を検出
    public List<Edge> getIntersectingEdges() {
        Set<Edge> intersectingEdges = new HashSet<Edge>();
        for (int i = 0; i < edges.size(); i++) {
            for (int j = i + 1; j < edges.size(); j++) {
                if (isIntersecting(edges.get(i), edges.get(j))) {
                    intersectingEdges.add(edges.get(i));
                    intersectingEdges.add(edges.get(j));
                }
            }
        }
        return new ArrayList<>(intersectingEdges);
    }

    // ゲームが「解決済み」かを判定（＝交差している辺がない状態）
    public boolean isGameSolved() {
        List<Edge> intersectList = getIntersectingEdges();
        return intersectList == null || intersectList.size() == 0;
    }

    // 2つの辺が交差しているかを判定
    private boolean isIntersecting(Edge e1, Edge e2) {
        // 各辺の始点・終点のノードを取得
        Point e1Start = nodes.get(e1.getStartIndex());
        Point e1End = nodes.get(e1.getEndIndex());
        Point e2Start = nodes.get(e2.getStartIndex());
        Point e2End = nodes.get(e2.getEndIndex());

        // 同じノードを共有している場合は交差しないと判定
        if (e1.getStartIndex() == e2.getStartIndex() ||
            e1.getStartIndex() == e2.getEndIndex() ||
            e1.getEndIndex() == e2.getStartIndex() ||
            e1.getEndIndex() == e2.getEndIndex()) {
            return false;
        }

        return isCrossing(e1Start, e1End, e2Start, e2End);
    }

    // 線分abとcdが交差しているかを判定する
    private boolean isCrossing(Point a, Point b, Point c, Point d) {
        double cross1 = crossProduct(b, a, c) * crossProduct(b, a, d);
        double cross2 = crossProduct(d, c, a) * crossProduct(d, c, b);

        // 条件1: それぞれの線分が他方の両側に点を持つ → 真の交差
        boolean differentSides = cross1 < 0 && cross2 < 0;

        // 条件2: 一方の点が他方の線分上にある → 接触としての交差も含む
        boolean endpointOnLine = (crossProduct(b, a, c) == 0 && isOnSegment(a, b, c)) ||
                                 (crossProduct(b, a, d) == 0 && isOnSegment(a, b, d)) ||
                                 (crossProduct(d, c, a) == 0 && isOnSegment(c, d, a)) ||
                                 (crossProduct(d, c, b) == 0 && isOnSegment(c, d, b));

        return differentSides || endpointOnLine;
    }

    // 点pが線分ab上にあるかを判定（境界を含む）
    private boolean isOnSegment(Point a, Point b, Point p) {
        return Math.min(a.getX(), b.getX()) <= p.getX() && p.getX() <= Math.max(a.getX(), b.getX()) &&
               Math.min(a.getY(), b.getY()) <= p.getY() && p.getY() <= Math.max(a.getY(), b.getY());
    }

    // ベクトル積（交差判定に用いる）
    private double crossProduct(Point p1, Point p2, Point p3) {
        return (p3.getX() - p1.getX()) * (p2.getY() - p1.getY()) -
               (p3.getY() - p1.getY()) * (p2.getX() - p1.getX());
    }

    // 現在のノード一覧を取得
    public List<Point> getNodes() {
        return nodes;
    }

    // 現在のエッジ一覧を取得
    public List<Edge> getEdges() {
        return edges;
    }
}
