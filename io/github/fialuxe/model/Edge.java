package io.github.fialuxe.model;

//グラフの辺を表すクラス。グラフは始点と終点を持つ辺として管理される。
public class Edge {
    int startIndex, endIndex;
    public Edge(int start, int end){
        this.startIndex = start;
        this.endIndex = end;
    }
    public int getStartIndex(){
        return startIndex;
    }
    public int getEndIndex(){
        return endIndex;
    }
}
