package ru.stqa.pft.sandbox;

public class MyFirstProgram{
	public static void main(String[] args){
		System.out.println("Hello, World!");
		Point p1 = new Point(24,50);
		Point p2 = new Point(240,900);
		System.out.println("Расстояние между первой и второй точкой = " + distance(p1,p2));
    }

	public static double distance(Point p1, Point p2){
		return Math.sqrt(Math.pow((p2.x - p1.x), 2)+(Math.pow((p2.y - p1.y), 2)));
	}
}