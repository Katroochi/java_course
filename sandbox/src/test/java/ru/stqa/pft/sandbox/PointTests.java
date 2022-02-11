package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;


public class PointTests {
    @Test
    public void testDistance(){
        Point p1 = new Point(24,50);
        Point p2 = new Point(240,900);
        Assert.assertEquals(p1.distance(p2), 877.0153932514526);
    }
    @Test
    public void testDistanceFailure(){
        Point p1 = new Point(24,50);
        Point p2 = new Point(240,900);
        Assert.assertNotEquals(p1.distance(p2), 877);
    }
}
