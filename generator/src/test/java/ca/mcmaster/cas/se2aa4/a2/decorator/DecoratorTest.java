package ca.mcmaster.cas.se2aa4.a2.decorator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


public class DecoratorTest {

    private FixedDecorator decorator;

    // Test to check if the polygon fill colour takes the right inputs
    @Test
    public void testSetPolyFillColour() {
        decorator = new FixedDecorator();
        assertTrue(decorator.setPolyFillColour("ff0000ff"));
        assertFalse(decorator.setPolyFillColour("222"));
        assertFalse(decorator.setPolyFillColour("ff0000ff0"));
    }

    // Test to check if the polygon border colour takes the right inputs
    @Test
    public void testSetPolyBorderColour() {
        decorator = new FixedDecorator();
        assertTrue(decorator.setPolyBorderColour("ff0000ff"));
        assertFalse(decorator.setPolyBorderColour("222"));
        assertFalse(decorator.setPolyBorderColour("ff0000ff0"));
    }

    // Test to check if the segment colour takes the right inputs
    @Test
    public void testSetSegColour() {
        decorator = new FixedDecorator();
        assertTrue(decorator.setSegColour("ff0000ff"));
        assertFalse(decorator.setSegColour("222"));
        assertFalse(decorator.setSegColour("ff0000ff0"));
    }

    // Test to check if the vertex colour takes the right inputs
    @Test
    public void testSetVertColour() {
        decorator = new FixedDecorator();
        assertTrue(decorator.setVertColour("ff0000ff"));
        assertFalse(decorator.setVertColour("222"));
        assertFalse(decorator.setVertColour("ff0000ff0"));
    }

    // Test to check if the polygon border thickness takes the right inputs
    @Test
    public void testSetPolyBorderThickness() {
        decorator = new FixedDecorator();
        assertTrue(decorator.setPolyBorderThickness("5"));
        assertEquals(5.0f, decorator.polyBorderThickness, 0.001f);

        assertFalse(decorator.setPolyBorderThickness("-1"));
        assertEquals(FixedDecorator.DEFAULT_POLY_BORDER_THICKNESS, decorator.polyBorderThickness, 0.001f); 

        assertTrue(decorator.setPolyBorderThickness("11")); 
        assertEquals(11f, decorator.polyBorderThickness, 0.001f); 

        assertFalse(decorator.setPolyBorderThickness("abc")); 
        assertEquals(FixedDecorator.DEFAULT_POLY_BORDER_THICKNESS, decorator.polyBorderThickness, 0.001f); 
    }

    // Test to check if the segment thickness takes the right inputs
    @Test
    public void testSetSegThickness() {
        decorator = new FixedDecorator();
        assertTrue(decorator.setSegThickness("7"));
        assertEquals(7.0f, decorator.segThickness, 0.001f);

        assertFalse(decorator.setSegThickness("-1")); 
        assertEquals(FixedDecorator.DEFAULT_SEG_THICKNESS, decorator.segThickness, 0.001f); 

        assertTrue(decorator.setSegThickness("11")); 
        assertEquals(11f, decorator.segThickness, 0.001f); 

        assertFalse(decorator.setSegThickness("abc")); 
        assertEquals(FixedDecorator.DEFAULT_SEG_THICKNESS, decorator.segThickness, 0.001f); 
    }

    // Test to check if the vertex thickness takes the right inputs
    @Test
    public void testSetVertThickness() {
        decorator = new FixedDecorator();
        assertTrue(decorator.setVertThickness("2.5"));
        assertEquals(2.5f, decorator.vertThickness, 0.001f);
        
        assertFalse(decorator.setVertThickness("-1")); 
        assertEquals(FixedDecorator.DEFAULT_VERT_THICKNESS, decorator.vertThickness, 0.001f); 

        assertTrue(decorator.setVertThickness("11")); 
        assertEquals(11f, decorator.vertThickness, 0.001f); 

        assertFalse(decorator.setVertThickness("abc")); 
        assertEquals(FixedDecorator.DEFAULT_VERT_THICKNESS, decorator.vertThickness, 0.001f); 
    }
}