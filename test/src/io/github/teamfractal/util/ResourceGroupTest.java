/**
 * SEPR project inherited from DRTN.
 * Any changes are marked by preceding comments.
 * 
 * Executables availabe at: https://seprated.github.io/Assessment4/Executables.zip
**/
package io.github.teamfractal.util;

import io.github.teamfractal.TesterFile;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by tjb545 on 26/04/2017.
 */
public class ResourceGroupTest extends TesterFile {

    /**
     * Test group 1
     */
    ResourceGroupInteger testGroup1;
    /**
     * Test group 2
     */
    ResourceGroupInteger testGroup2;

    /**
     * Setup this instance.
     */
    @Before
    public void setUp() {
        testGroup1 = new ResourceGroupInteger(10, 10, 10);
        testGroup2 = new ResourceGroupInteger(25, 25, 25);
    }

    /**
     * Addition Test
     * Checks that the resource addition is correct
     */
    @Test
    public void resourceAdditionTest() {
        ResourceGroupInteger result = ResourceGroupInteger.add(testGroup1, testGroup2);
        ResourceGroupInteger expected = new ResourceGroupInteger(35, 35, 35);
        assertEquals(result.getFood(), expected.getFood());
        assertEquals(result.getEnergy(), expected.getEnergy());
        assertEquals(result.getOre(), expected.getOre());
    }

    /**
     * Minus Test
     * Checks that subtracting resources is correct
     */
    @Test
    public void resourceMinusTest() {
        ResourceGroupInteger result = ResourceGroupInteger.sub(testGroup2, testGroup1);
        ResourceGroupInteger expected = new ResourceGroupInteger(15, 15, 15);
        assertEquals(result.getFood(), expected.getFood());
        assertEquals(result.getEnergy(), expected.getEnergy());
        assertEquals(result.getOre(), expected.getOre());
    }

    /**
     * Multiplication Test
     * Checks that resource multiplication is correct
     */
    @Test
    public void resourceMultiplicationTest() {
        ResourceGroupInteger result = ResourceGroupInteger.mult(testGroup1, testGroup2);
        ResourceGroupInteger expected = new ResourceGroupInteger(250, 250, 250);
        assertEquals(result.getFood(), expected.getFood());
        assertEquals(result.getEnergy(), expected.getEnergy());
        assertEquals(result.getOre(), expected.getOre());
    }

    /**
     * Float multiplication test
     * Checks that resources can be multiplied by a float
     */
    @Test
    public void resourceMultiplicationFloat() {
        ResourceGroupInteger result = ResourceGroupInteger.mult(testGroup1, 1.5F);
        ResourceGroupInteger expected = new ResourceGroupInteger(15, 15, 15);
        assertEquals(result.getFood(), expected.getFood());
        assertEquals(result.getEnergy(), expected.getEnergy());
        assertEquals(result.getOre(), expected.getOre());
    }

    /**
     * Integer Multiplication Test
     * Checks that resources can be multiplied by an integer
     */
    @Test
    public void resourceMultiplicationInteger() {
        ResourceGroupInteger result = ResourceGroupInteger.mult(testGroup1, 3);
        ResourceGroupInteger expected = new ResourceGroupInteger(30, 30, 30);
        assertEquals(result.getFood(), expected.getFood());
        assertEquals(result.getEnergy(), expected.getEnergy());
        assertEquals(result.getOre(), expected.getOre());
    }
}