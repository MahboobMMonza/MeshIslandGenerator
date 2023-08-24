package ca.mcmaster.cas.se2aa4.a2.decorator;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import ca.mcmaster.cas.se2aa4.a2.components.*;

public class FixedDecoratorTest {

    private FixedDecorator decorator;

    @Test
    public void testSetPolyFillColour() {
        // Test to check if the polygon fill colour takes the right inputs
        decorator = new FixedDecorator();
        int targetColour = Integer.parseUnsignedInt("ff0000ff", 16);
        int otherTargetColour = Integer.parseUnsignedInt("ff1111ff", 16);
        assertAll("Assert that only valid colour formats can be set for polygon fill colour",
                () -> assertFalse(decorator.setPolyFillColour("222")),
                () -> assertFalse(decorator.setPolyFillColour("ff0000ff0")),
                () -> assertEquals(FixedDecorator.DEFAULT_POLY_FILL_COLOUR, decorator.polyFillColour),
                () -> assertTrue(decorator.setPolyFillColour(Integer.toHexString(targetColour))),
                () -> assertFalse(decorator.setPolyFillColour("bleach")),
                () -> assertEquals(targetColour, decorator.polyFillColour),
                () -> assertTrue(decorator.setPolyFillColour(Integer.toHexString(otherTargetColour).toUpperCase())),
                () -> assertEquals(otherTargetColour, decorator.polyFillColour));
    }

    @Test
    public void testSetPolyBorderColour() {
        // Test to check if the polygon border colour takes the right inputs
        decorator = new FixedDecorator();
        int targetColour = Integer.parseUnsignedInt("0000ffff", 16);
        int otherTargetColour = Integer.parseUnsignedInt("1111ffff", 16);
        assertAll("Assert that only valid colour formats can be set for polygon border colour",
                () -> assertFalse(decorator.setPolyBorderColour("222")),
                () -> assertFalse(decorator.setPolyBorderColour("ff0000ff0")),
                () -> assertEquals(FixedDecorator.DEFAULT_POLY_BORDER_COLOUR, decorator.polyBorderColour),
                () -> assertTrue(decorator.setPolyBorderColour(String.format("%08x", targetColour))),
                () -> assertFalse(decorator.setPolyBorderColour("bleach")),
                () -> assertEquals(targetColour, decorator.polyBorderColour),
                () -> assertTrue(decorator.setPolyBorderColour(Integer.toHexString(otherTargetColour).toUpperCase())),
                () -> assertEquals(otherTargetColour, decorator.polyBorderColour));
    }

    @Test
    public void testSetSegColour() {
        // Test to check if the segment colour takes the right inputs
        decorator = new FixedDecorator();
        int targetColour = Integer.parseUnsignedInt("ff00ffff", 16);
        int otherTargetColour = Integer.parseUnsignedInt("ff11ffff", 16);
        assertAll("Assert that only valid colour formats can be set for segment colour",
                () -> assertFalse(decorator.setSegColour("222")),
                () -> assertFalse(decorator.setSegColour("ff0000ff0")),
                () -> assertEquals(FixedDecorator.DEFAULT_SEG_COLOUR, decorator.segColour),
                () -> assertTrue(decorator.setSegColour(Integer.toHexString(targetColour))),
                () -> assertFalse(decorator.setSegColour("bleach")),
                () -> assertEquals(targetColour, decorator.segColour),
                () -> assertTrue(decorator.setSegColour(Integer.toHexString(otherTargetColour).toUpperCase())),
                () -> assertEquals(otherTargetColour, decorator.segColour));
    }

    @Test
    public void testSetVertColour() {
        // Test to check if the vertex colour takes the right inputs
        decorator = new FixedDecorator();
        int targetColour = Integer.parseUnsignedInt("ffffffff", 16);
        int otherTargetColour = Integer.parseUnsignedInt("ffddddff", 16);
        assertAll("Assert that only valid colour formats can be set for vertex colour",
                () -> assertFalse(decorator.setVertColour("222")),
                () -> assertFalse(decorator.setVertColour("ff0000ff0")),
                () -> assertEquals(FixedDecorator.DEFAULT_VERT_COLOUR, decorator.vertColour),
                () -> assertTrue(decorator.setVertColour(Integer.toHexString(targetColour))),
                () -> assertFalse(decorator.setVertColour("bleach")),
                () -> assertEquals(targetColour, decorator.vertColour),
                () -> assertTrue(decorator.setVertColour(Integer.toHexString(otherTargetColour).toUpperCase())),
                () -> assertEquals(otherTargetColour, decorator.vertColour));
    }

    @Test
    public void testGetSetPolyBorderThickness() {
        // Test to check if the polygon border thickness takes the right inputs
        decorator = new FixedDecorator();
        float validThickness1, invalidThickness1, validThickness2, invalidThickness2;
        validThickness1 = 5.0f;
        invalidThickness1 = -1f;
        validThickness2 = 11f;
        invalidThickness2 = 0.0001f + 30f;
        assertAll("Assert that only valid thicknesses can be provided for polygon border thickness",
                () -> assertTrue(decorator.setPolyBorderThickness(Float.toString(validThickness1))),
                () -> assertEquals(validThickness1, decorator.getPolyBorderThickness(), 0.001f),
                () -> assertFalse(decorator.setPolyBorderThickness(Float.toString(invalidThickness1))),
                () -> assertEquals(validThickness1, decorator.getPolyBorderThickness(), 0.001f),
                () -> assertTrue(decorator.setPolyBorderThickness(Float.toString(validThickness2))),
                () -> assertEquals(validThickness2, decorator.getPolyBorderThickness(), 0.001f),
                () -> assertFalse(decorator.setPolyBorderThickness(Float.toString(invalidThickness2))),
                () -> assertEquals(validThickness2, decorator.getPolyBorderThickness(), 0.001f),
                () -> assertFalse(decorator.setPolyBorderThickness("abc")),
                () -> assertEquals(validThickness2, decorator.getPolyBorderThickness(), 0.001f));
    }

    @Test
    public void testGetSetSegThickness() {
        // Test to check if the segment thickness takes the right inputs
        decorator = new FixedDecorator();
        float validThickness1, invalidThickness1, validThickness2, invalidThickness2;
        validThickness1 = 7.0f;
        invalidThickness1 = -1f;
        validThickness2 = 8f;
        invalidThickness2 = 30f + 0.0001f;
        assertAll("Assert that only valid thicknesses can be provided for segment thickness",
                () -> assertTrue(decorator.setSegThickness(Float.toString(validThickness1))),
                () -> assertEquals(validThickness1, decorator.getSegThickness(), 0.001f),
                () -> assertFalse(decorator.setSegThickness(Float.toString(invalidThickness1))),
                () -> assertEquals(validThickness1, decorator.getSegThickness(), 0.001f),
                () -> assertTrue(decorator.setSegThickness(Float.toString(validThickness2))),
                () -> assertEquals(validThickness2, decorator.getSegThickness(), 0.001f),
                () -> assertFalse(decorator.setSegThickness(Float.toString(invalidThickness2))),
                () -> assertEquals(validThickness2, decorator.getSegThickness(), 0.001f),
                () -> assertFalse(decorator.setSegThickness("abc")),
                () -> assertEquals(validThickness2, decorator.getSegThickness(), 0.001f));
    }

    @Test
    public void testGetSetVertThickness() {
        // Test to check if the vertex thickness takes the right inputs
        decorator = new FixedDecorator();
        float validThickness1, invalidThickness1, validThickness2, invalidThickness2;
        validThickness1 = 8.0f;
        invalidThickness1 = -1f;
        validThickness2 = 15f;
        invalidThickness2 = 30f + 0.0001f;
        assertAll("Assert that only valid thicknesses can be provided for vertex thickness",
                () -> assertTrue(decorator.setVertThickness(Float.toString(validThickness1))),
                () -> assertEquals(validThickness1, decorator.getVertThickness(), 0.001f),
                () -> assertFalse(decorator.setVertThickness(Float.toString(invalidThickness1))),
                () -> assertEquals(validThickness1, decorator.getVertThickness(), 0.001f),
                () -> assertTrue(decorator.setVertThickness(Float.toString(validThickness2))),
                () -> assertEquals(validThickness2, decorator.getVertThickness(), 0.001f),
                () -> assertFalse(decorator.setVertThickness(Float.toString(invalidThickness2))),
                () -> assertEquals(validThickness2, decorator.getVertThickness(), 0.001f),
                () -> assertFalse(decorator.setVertThickness("abc")),
                () -> assertEquals(validThickness2, decorator.getVertThickness(), 0.001f));
    }

    @Test
    public void testGetColourStrings() {
        decorator = new FixedDecorator();
        int polyFillColour, polyBorderColour, segColour, vertColour;
        String polyFillColourString, polyBorderColourString, segColourString, vertColourString;
        polyFillColour = 0x8e074480;
        polyBorderColour = 0x446910ff;
        segColour = 0x93bfe0a8;
        vertColour = 0xfd807b43;
        polyFillColourString = "#8E074480";
        polyBorderColourString = "#446910FF";
        segColourString = "#93BFE0A8";
        vertColourString = "#FD807B43";
        assertAll("Assert default colour strings are formatted correctly",
                () -> assertEquals("#FFFF00FF", decorator.getPolyFillColour()),
                () -> assertEquals("#FFFFFF00", decorator.getPolyBorderColour()),
                () -> assertEquals("#FFFFFFFF", decorator.getSegColour()),
                () -> assertEquals("#FF0000FF", decorator.getVertColour()));
        assertAll("Assert set colour strings are formatted correctly",
                () -> assertTrue(decorator.setPolyFillColour(Integer.toHexString(polyFillColour))),
                () -> assertTrue(decorator.setPolyBorderColour(Integer.toHexString(polyBorderColour))),
                () -> assertTrue(decorator.setSegColour(Integer.toHexString(segColour))),
                () -> assertTrue(decorator.setVertColour(Integer.toHexString(vertColour))),
                () -> assertEquals(polyFillColourString, decorator.getPolyFillColour()),
                () -> assertEquals(polyBorderColourString, decorator.getPolyBorderColour()),
                () -> assertEquals(segColourString, decorator.getSegColour()),
                () -> assertEquals(vertColourString, decorator.getVertColour()));
    }

    @Test
    public void testRgbaTosRGB() {
        int polyFillColour, polyBorderColour, segColour, vertColour;
        int polyFillsRGB, polyBordersRGB, segsRGB, vertsRGB;
        polyFillColour = 0x8e074480;
        polyBorderColour = 0x446910ff;
        segColour = 0x93bfe0a8;
        vertColour = 0xfd807b43;
        polyFillsRGB = 0x808e0744;
        polyBordersRGB = 0xff446910;
        segsRGB = 0xa893bfe0;
        vertsRGB = 0x43fd807b;
        assertAll("Assert that RGBA to sRGB conversion works correctly",
                () -> assertEquals(polyFillsRGB, FixedDecorator.rgbaTosRGB(polyFillColour)),
                () -> assertEquals(polyBordersRGB, FixedDecorator.rgbaTosRGB(polyBorderColour)),
                () -> assertEquals(segsRGB, FixedDecorator.rgbaTosRGB(segColour)),
                () -> assertEquals(vertsRGB, FixedDecorator.rgbaTosRGB(vertColour)));
    }

    @Test
    public void testDecoratePoly() {
        int polyFillColour, polyBorderColour;
        float polyBorderThickness = 12.0f;
        Poly p = new Polygon(3, 4);
        decorator = new FixedDecorator();
        polyFillColour = 0x8e074480;
        polyBorderColour = 0x446910ff;
        decorator.decoratePoly(p);
        assertAll("Assert that default values are decorated for polygons",
                () -> assertEquals(FixedDecorator.rgbaTosRGB(FixedDecorator.DEFAULT_POLY_FILL_COLOUR),
                        p.getFillColour()),
                () -> assertEquals(FixedDecorator.rgbaTosRGB(FixedDecorator.DEFAULT_POLY_BORDER_COLOUR),
                        p.getBorderColour()),
                () -> assertEquals(FixedDecorator.DEFAULT_POLY_BORDER_THICKNESS, p.getBorderThickness(), 0.0001f));
        decorator.setPolyFillColour(String.format("%08X", polyFillColour));
        decorator.setPolyBorderColour(String.format("%08X", polyBorderColour));
        decorator.setPolyBorderThickness(String.format("%.0f", polyBorderThickness));
        decorator.decoratePoly(p);
        assertAll("Assert that defined values are decorated for polygons",
                () -> assertEquals(FixedDecorator.rgbaTosRGB(polyFillColour), p.getFillColour()),
                () -> assertEquals(FixedDecorator.rgbaTosRGB(polyBorderColour), p.getBorderColour()),
                () -> assertEquals(polyBorderThickness, p.getBorderThickness(), 0.0001f));
    }

    @Test
    public void testDecorateSeg() {
        int segColour;
        float segThickness = 9.0f;
        Seg s = new Segment(3, 4, 5, 6);
        decorator = new FixedDecorator();
        segColour = 0x93bfe0a8;
        decorator.decorateSeg(s);
        assertAll("Assert that default values are decorated for segments",
                () -> assertEquals(FixedDecorator.rgbaTosRGB(FixedDecorator.DEFAULT_SEG_COLOUR), s.getColour()),
                () -> assertEquals(FixedDecorator.DEFAULT_SEG_THICKNESS, s.getThickness(), 0.0001f));
        decorator.setSegColour(String.format("%08X", segColour));
        decorator.setSegThickness(String.format("%.0f", segThickness));
        decorator.decorateSeg(s);
        assertAll("Assert that defined values are decorated for segments",
                () -> assertEquals(FixedDecorator.rgbaTosRGB(segColour), s.getColour()),
                () -> assertEquals(segThickness, s.getThickness(), 0.0001f));
    }

    @Test
    public void testDecorateVert() {
        int vertColour;
        float vertThickness = 9.0f;
        Vert v = new Vertex(6, 29);
        decorator = new FixedDecorator();
        vertColour = 0xfd807b43;
        decorator.decorateVert(v);
        assertAll("Assert that default values are decorated for vertices",
                () -> assertEquals(FixedDecorator.rgbaTosRGB(FixedDecorator.DEFAULT_VERT_COLOUR), v.getColour()),
                () -> assertEquals(FixedDecorator.DEFAULT_VERT_THICKNESS, v.getThickness(), 0.0001f));
        decorator.setVertColour(String.format("%08X", vertColour));
        decorator.setVertThickness(String.format("%.0f", vertThickness));
        decorator.decorateVert(v);
        assertAll("Assert that defined values are decorated for vertices",
                () -> assertEquals(FixedDecorator.rgbaTosRGB(vertColour), v.getColour()),
                () -> assertEquals(vertThickness, v.getThickness(), 0.0001f));
    }
}