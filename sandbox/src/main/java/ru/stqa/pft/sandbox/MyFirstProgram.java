package ru.stqa.pft.sandbox;

public class MyFirstProgram{
	public static void main(String[] args){
		System.out.println("Hello, World!");
		Point p1 = new Point(204,598);
     	Point p2 = new Point(2999,7650);
		System.out.println("Расстояние между первой и второй точкой = " + p1.distance(p2));
    }

}