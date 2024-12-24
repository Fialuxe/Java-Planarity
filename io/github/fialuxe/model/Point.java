package io.github.fialuxe.model;

public class Point {
    int x, y;
    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }
    //マウスポインタあたりののキャストが怠くてこの実装になっている
    public int distance(java.awt.Point r){
        return (int)Math.sqrt((x-(int)r.getX())*(x-(int)r.getX()) + (y-(int)r.getY())*(y-(int)r.getY()));
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
}
