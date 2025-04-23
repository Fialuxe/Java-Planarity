package io.github.fialuxe.model;

import java.util.Random;

public class GraphGenerator {
    final int NUM_NODES = 7; //最大ノード数　遊びやすいのはここら辺だった
    final int MAX_EDGES = 4; //最大辺数
    GraphModel model;

    public GraphGenerator(GraphModel model){
        this.model = model;
    }
    public void generateRandomGraph() {
        // ノードの追加
        for (int i = 0; i < NUM_NODES; i++) {
            //NOTE:解けるパズルかどうかまでは判定していない
            int x = new Random().nextInt(400) + 50; // ランダムにX座標を生成 (50-450)
            int y = new Random().nextInt(400) + 50; // ランダムにY座標を生成 (50-450)
            model.addNode(new Point(x, y));
        }
        // エッジの追加
        for (int i = 0; i < NUM_NODES; i++) {
            int numEdges = new Random().nextInt(MAX_EDGES) + 1; // 各ノードから1本か2本のエッジを生成
            for (int j = 0; j < numEdges; j++) {
                int targetNode = new Random().nextInt(NUM_NODES);
                if (i != targetNode) {  // 同じノードにエッジを追加しない
                    model.addEdge(i, targetNode);
                }
            }
        }
    }
}
