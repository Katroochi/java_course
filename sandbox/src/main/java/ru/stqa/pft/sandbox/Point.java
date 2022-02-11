package ru.stqa.pft.sandbox;

public class Point {
    public double x;
    public double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public Point(Point p2){
        x = p2.x;
        y = p2.y;
    }
    public double distance(Point p2){
        return Math.sqrt(Math.pow((p2.x- this.x), 2)+(Math.pow((p2.y - this.y), 2)));
    }
}
