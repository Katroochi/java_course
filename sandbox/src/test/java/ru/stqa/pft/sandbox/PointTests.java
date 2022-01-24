package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

import static ru.stqa.pft.sandbox.MyFirstProgram.distance;


public class PointTests {
    @Test
    public void testDistance(){
        Point p1 = new Point(24,50);
        Point p2 = new Point(240,900);
        Assert.assertEquals(distance(p1,p2), 877.0153932514526);
    }
    @Test
    public void testDistanceFailure(){
        Point p1 = new Point(24,50);
        Point p2 = new Point(240,900);
        Assert.assertNotEquals(distance(p1,p2), 877);
    }
}
