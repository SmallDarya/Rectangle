/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smolienko.areacalculator;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author d_smolienko
 */
public class RectangleTest {
    
    public RectangleTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

      @Test
    public void twoParallelRectangles() {
        Rectangle r1 = new Rectangle(new Point(1, 1), new Point(1, 4), 8);
        Rectangle r2 = new Rectangle(new Point(3, 1), new Point(3, 4), 2);

        double result = r1.getCrossArea(r2);

        assertEquals(6.0, result, 0.01);

    }

    @Test
    public void firstInSecond() {
        Rectangle r1 = new Rectangle(new Point(1, 1), new Point(1, 7), 8);
        Rectangle r2 = new Rectangle(new Point(2, 2), new Point(2, 5), 2);

        double result = r1.getCrossArea(r2);

        assertEquals(6.0, result, 0.01);

    }

    @Test
    public void oneCornerinRectangle() {
        Rectangle r1 = new Rectangle(new Point(1, 1), new Point(1, 4), 8);
        Rectangle r2 = new Rectangle(new Point(4, -1), new Point(12, 5), 3);

        double result = r1.getCrossArea(r2);
        assertEquals(2.041, result, 0.01);
    }

    @Test
    public void twoCornersInRectangle() {
        Rectangle r1 = new Rectangle(new Point(1, 1), new Point(1, 4), 8);
        Rectangle r2 = new Rectangle(new Point(5, 2.5), new Point(12, 3.5), 4);

        double result = r1.getCrossArea(r2);
        assertEquals(6.982, result, 0.01);
    }

    @Test
    public void threeCornersInRectangle() {
        Rectangle r1 = new Rectangle(new Point(1, 1), new Point(1, 4), 8);
        Rectangle r2 = new Rectangle(new Point(-1, 1), new Point(9, 5), 5);

        double result = r1.getCrossArea(r2);
        assertEquals(17.95, result, 0.01);
    }

    @Test
    public void sixCrosspoints() {
        Rectangle r1 = new Rectangle(new Point(1, 1), new Point(1, 4), 8);
        Rectangle r2 = new Rectangle(new Point(-1, 3), new Point(2.5, 7.5), 9.5);

        double result = r1.getCrossArea(r2);
         assertEquals(22.31, result, 0.01);
    }
    
     @Test
    public void sevenCrosspoints() {
        Rectangle r1 = new Rectangle(new Point(1, 1), new Point(1, 4), 8);
        Rectangle r2 = new Rectangle(new Point(-1, 3), new Point(2.5, 7.5), 8.5);

        double result = r1.getCrossArea(r2);
         assertEquals(21.91, result, 0.01);
    }


    @Test
    public void oneRectangleIsPoint() {
        Rectangle r1 = new Rectangle(new Point(1, 1), new Point(1, 4), 3);
        Rectangle r2 = new Rectangle(new Point(2, 2), new Point(2, 2), 0);

        double result = r1.getCrossArea(r2);
        assertEquals(0.0, result, 0.01);
    }

    @Test
    public void  oneRectangleIsLine() {
        Rectangle r1 = new Rectangle(new Point(1, 1), new Point(1, 4), 3);
        Rectangle r2 = new Rectangle(new Point(-1, 2), new Point(3, 2), 0);

        double result = r1.getCrossArea(r2);
        assertEquals(0.0, result, 0.01);

        Rectangle r3 = new Rectangle(new Point(1, 1), new Point(1, 4), 3);
        Rectangle r4 = new Rectangle(new Point(2, 2), new Point(2, 2), 5);

        result = r3.getCrossArea(r4);
        assertEquals(0.0, result, 0.01);
    }
    
    @Test
    public void haveNoCommonPoints() {
        Rectangle r1 = new Rectangle(new Point(1, 1), new Point(1, 4), 3);
        Rectangle r2 = new Rectangle(new Point(8, -2), new Point(10, 2), 1);

        double result = r1.getCrossArea(r2);
        assertEquals(0.0, result, 0.01);
    }

    @Test
    public void calculateAreaOfOneRectangle() {
        Rectangle r1 = new Rectangle(new Point(1, 1), new Point(1, 4), 3);

        double result = r1.getArea();
        assertEquals(9.0, result, 0.01);
    }
}
