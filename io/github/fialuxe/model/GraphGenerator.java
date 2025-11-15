package io.github.fialuxe.model;

import java.util.Random;
import java.util.HashSet;
import java.util.Set;

public class GraphGenerator {
    private GraphModel model;
    private Random random;

    public GraphGenerator(GraphModel model){
        this.model = model;
        this.random = new Random();
    }
    
    // デフォルトのランダムグラフ生成（後方互換性のため）
    public void generateRandomGraph() {
        generateRandomGraph(7, 800, 600);
    }
    
    // ノード数と画面サイズを指定してランダムグラフを生成
    public void generateRandomGraph(int numNodes, int width, int height) {
        // 既存のグラフをクリア
        model.getNodes().clear();
        model.getEdges().clear();
        
        // 安全なマージンを設定
        int margin = 80;
        int usableWidth = Math.max(width - 2 * margin, 200);
        int usableHeight = Math.max(height - 2 * margin, 200);
        
        // ノードを円形に配置（より綺麗な配置）
        double centerX = width / 2.0;
        double centerY = height / 2.0;
        double radius = Math.min(usableWidth, usableHeight) / 2.5;
        
        for (int i = 0; i < numNodes; i++) {
            double angle = 2 * Math.PI * i / numNodes;
            int x = (int)(centerX + radius * Math.cos(angle));
            int y = (int)(centerY + radius * Math.sin(angle));
            model.addNode(new Point(x, y));
        }
        
        // エッジを追加（重複を避ける）
        Set<String> addedEdges = new HashSet<>();
        int targetEdgeCount = Math.min(numNodes * 2, numNodes * (numNodes - 1) / 2);
        
        // まず全てのノードが接続されるようにする
        for (int i = 0; i < numNodes - 1; i++) {
            int next = (i + 1) % numNodes;
            addEdge(i, next, addedEdges);
        }
        
        // 追加のランダムエッジを生成
        int attempts = 0;
        while (addedEdges.size() < targetEdgeCount && attempts < targetEdgeCount * 10) {
            int start = random.nextInt(numNodes);
            int end = random.nextInt(numNodes);
            
            if (start != end) {
                addEdge(start, end, addedEdges);
            }
            attempts++;
        }
        
        // 初期位置をシャッフルして難易度を上げる
        shuffleNodes(width, height);
    }
    
    // エッジを追加（重複チェック付き）
    private void addEdge(int start, int end, Set<String> addedEdges) {
        String edgeKey1 = start + "-" + end;
        String edgeKey2 = end + "-" + start;
        
        if (!addedEdges.contains(edgeKey1) && !addedEdges.contains(edgeKey2)) {
            model.addEdge(start, end);
            addedEdges.add(edgeKey1);
        }
    }
    
    // ノードの位置をシャッフル（難易度調整）
    public void shuffleNodes(int width, int height) {
        int margin = 80;
        int usableWidth = Math.max(width - 2 * margin, 200);
        int usableHeight = Math.max(height - 2 * margin, 200);
        
        for (int i = 0; i < model.getNodes().size(); i++) {
            int x = random.nextInt(usableWidth) + margin;
            int y = random.nextInt(usableHeight) + margin;
            model.moveNode(i, new Point(x, y));
        }
    }
}
