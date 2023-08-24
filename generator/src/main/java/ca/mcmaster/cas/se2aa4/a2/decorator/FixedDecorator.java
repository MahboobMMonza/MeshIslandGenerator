package ca.mcmaster.cas.se2aa4.a2.decorator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ca.mcmaster.cas.se2aa4.a2.components.Poly;
import ca.mcmaster.cas.se2aa4.a2.components.Seg;
import ca.mcmaster.cas.se2aa4.a2.components.Vert;

/**
 * FixedDecorator
 */
public class FixedDecorator implements Decorator {
    private static final Logger logger = LogManager.getLogger(FixedDecorator.class);

    public static final int DEFAULT_POLY_FILL_COLOUR, DEFAULT_POLY_BORDER_COLOUR, DEFAULT_SEG_COLOUR,
            DEFAULT_VERT_COLOUR, DEFAULT_CENTROID_COLOUR;
    private static final int COLOUR_BIT_SIZE, ALPHA_LEFT_SHIFT, SUCCESSFUL_CONVERSION, UNSUCCESSFUL_CONVERSION;
    public static final float DEFAULT_POLY_BORDER_THICKNESS, DEFAULT_SEG_THICKNESS, DEFAULT_VERT_THICKNESS,
            DEFAULT_CENTROID_THICKNESS, MAX_SEG_THICKNESS, MAX_VERT_THICKNESS, MAX_POLY_BORDER_THICKNESS,
            MIN_SEG_THICKNESS, MIN_VERT_THICKNESS, MIN_POLY_BORDER_THICKNESS;
    static final String HEX_FORMAT_STRING = "#%08X";

    static {
        // Set default colours
        DEFAULT_POLY_FILL_COLOUR = 0xffff00ff;
        DEFAULT_POLY_BORDER_COLOUR = 0xffffff00;
        DEFAULT_SEG_COLOUR = 0xffffffff;
        DEFAULT_VERT_COLOUR = 0xff0000ff;
        DEFAULT_CENTROID_COLOUR = 0xffffff00;

        // Set default thicknesses
        DEFAULT_POLY_BORDER_THICKNESS = 0f;
        DEFAULT_SEG_THICKNESS = 3f;
        DEFAULT_VERT_THICKNESS = 2f;
        DEFAULT_CENTROID_THICKNESS = 0f;

        // Set min and max thicknesses
        MIN_SEG_THICKNESS = 0.25f;
        MIN_VERT_THICKNESS = 0.25f;
        MIN_POLY_BORDER_THICKNESS = 0f;
        MAX_SEG_THICKNESS = 30f;
        MAX_VERT_THICKNESS = 30f;
        MAX_POLY_BORDER_THICKNESS = 30f;

        // Set rgba to srgb colour-convertor-related constants
        COLOUR_BIT_SIZE = 8;
        ALPHA_LEFT_SHIFT = 24;

        // Set string to rgba formatted int conversion-related constants
        SUCCESSFUL_CONVERSION = 1;
        UNSUCCESSFUL_CONVERSION = 0;
    }

    /**
     * Converts the desired colour value (first given in RGBA format) when
     * decorating a component to follow Java's sRGB model.
     *
     * @param colour the the colour whose properties are being extracted.
     * @return the value of the converted.
     */
    static int rgbaTosRGB(final int colour) {
        // Shift the alpha values out of the given value, leaving the first 8 bits 0, to
        // which the alpha value will be masked into.
        return (colour >>> COLOUR_BIT_SIZE) | (colour << ALPHA_LEFT_SHIFT);
    }

    private static void warnUser(final String given, final String defaultValue, final String property) {
        logger.warn(String.format("The interpreted value of '%1$s' for the property %2$s is invalid. Property %2$s will"
                + " be set to %3$s instead.", given, property.toUpperCase(), defaultValue));
    }

    private static int[] tryConversion(final String colourString, final String property, final int defaultValue) {
        int colour;
        final int[] result = new int[2];
        if (colourString.length() != 8) {
            result[0] = UNSUCCESSFUL_CONVERSION;
            result[1] = defaultValue;
            warnUser(colourString, String.format(HEX_FORMAT_STRING, defaultValue), property);
        } else {
            try {
                colour = Integer.parseUnsignedInt(colourString, 16);
                result[0] = SUCCESSFUL_CONVERSION;
                result[1] = colour;
            } catch (Exception e) {
                result[0] = UNSUCCESSFUL_CONVERSION;
                result[1] = defaultValue;
                warnUser(colourString, String.format(HEX_FORMAT_STRING, defaultValue), property);
            }
        }
        return result;
    }

    private static float[] tryConversion(final String colourString, final String property, final float defaultValue,
            final float minValue,
            final float maxValue) {
        float thickness;
        final float[] result = new float[2];
        try {
            thickness = Float.parseFloat(colourString);
            if (thickness < minValue || thickness > maxValue) {
                result[0] = UNSUCCESSFUL_CONVERSION;
                result[1] = defaultValue;
                warnUser(colourString, String.format("%f", defaultValue), property);
            } else {
                result[0] = SUCCESSFUL_CONVERSION;
                result[1] = thickness;
            }
        } catch (final Exception e) {
            result[0] = UNSUCCESSFUL_CONVERSION;
            result[1] = defaultValue;
        }
        return result;
    }

    int polyFillColour, polyBorderColour, segColour, vertColour;

    float polyBorderThickness, segThickness, vertThickness;

    public FixedDecorator() {
        // Set colours to their defaults in case they are not changed
        polyFillColour = DEFAULT_POLY_FILL_COLOUR;
        polyBorderColour = DEFAULT_POLY_BORDER_COLOUR;
        segColour = DEFAULT_SEG_COLOUR;
        vertColour = DEFAULT_VERT_COLOUR;
        polyBorderThickness = DEFAULT_POLY_BORDER_THICKNESS;
        segThickness = DEFAULT_SEG_THICKNESS;
        vertThickness = DEFAULT_VERT_THICKNESS;
    }

    @Override
    public boolean setPolyFillColour(final String colourString) {
        final int[] conversion = tryConversion(colourString, "polygon_fill_colour", DEFAULT_POLY_FILL_COLOUR);
        if (conversion[0] == SUCCESSFUL_CONVERSION) {
            polyFillColour = conversion[1];
        }
        return conversion[0] == SUCCESSFUL_CONVERSION;
    }

    @Override
    public boolean setPolyBorderColour(final String colourString) {
        final int[] conversion = tryConversion(colourString, "polygon_border_colour", DEFAULT_POLY_BORDER_COLOUR);
        if (conversion[0] == SUCCESSFUL_CONVERSION) {
            polyBorderColour = conversion[1];
        }
        return conversion[0] == SUCCESSFUL_CONVERSION;
    }

    @Override
    public boolean setSegColour(final String colourString) {
        final int[] conversion = tryConversion(colourString, "segment_colour", DEFAULT_SEG_COLOUR);
        if (conversion[0] == SUCCESSFUL_CONVERSION) {
            segColour = conversion[1];
        }
        return conversion[0] == SUCCESSFUL_CONVERSION;
    }

    @Override
    public boolean setVertColour(final String colourString) {
        final int[] conversion = tryConversion(colourString, "vertex_colour", DEFAULT_VERT_COLOUR);
        if (conversion[0] == SUCCESSFUL_CONVERSION) {
            vertColour = conversion[1];
        }
        return conversion[0] == SUCCESSFUL_CONVERSION;
    }

    @Override
    public boolean setSegThickness(final String thickness) {
        final float[] conversion = tryConversion(thickness, "segment_thickness", DEFAULT_SEG_THICKNESS,
                MIN_SEG_THICKNESS,
                MAX_SEG_THICKNESS);
        if ((int) conversion[0] == SUCCESSFUL_CONVERSION) {
            segThickness = conversion[1];
        }
        return (int) conversion[0] == SUCCESSFUL_CONVERSION;
    }

    @Override
    public boolean setVertThickness(final String thickness) {
        final float[] conversion = tryConversion(thickness, "vertex_thickness", DEFAULT_VERT_THICKNESS,
                MIN_VERT_THICKNESS,
                MAX_VERT_THICKNESS);
        if ((int) conversion[0] == SUCCESSFUL_CONVERSION) {
            vertThickness = conversion[1];
        }
        return (int) conversion[0] == SUCCESSFUL_CONVERSION;
    }

    @Override
    public boolean setPolyBorderThickness(final String thickness) {
        final float[] conversion = tryConversion(thickness, "polygon_border_thickness", DEFAULT_POLY_BORDER_THICKNESS,
                MIN_POLY_BORDER_THICKNESS,
                MAX_POLY_BORDER_THICKNESS);
        if ((int) conversion[0] == SUCCESSFUL_CONVERSION) {
            polyBorderThickness = conversion[1];
        }
        return (int) conversion[0] == SUCCESSFUL_CONVERSION;
    }

    @Override
    public String getPolyFillColour() {
        return String.format(HEX_FORMAT_STRING, polyFillColour);
    }

    @Override
    public String getPolyBorderColour() {
        return String.format(HEX_FORMAT_STRING, polyBorderColour);
    }

    @Override
    public String getSegColour() {
        return String.format(HEX_FORMAT_STRING, segColour);
    }

    @Override
    public String getVertColour() {
        return String.format(HEX_FORMAT_STRING, vertColour);
    }

    @Override
    public float getPolyBorderThickness() {
        return polyBorderThickness;
    }

    @Override
    public float getSegThickness() {
        return segThickness;
    }

    @Override
    public float getVertThickness() {
        return vertThickness;
    }

    @Override
    public void decoratePoly(final Poly p) {
        // Get colours in rgba format for fill
        p.setFillColour(rgbaTosRGB(polyFillColour));
        p.setBorderColour(rgbaTosRGB(polyBorderColour));
        p.setBorderThickness(polyBorderThickness);
    }

    @Override
    public void decorateSeg(final Seg s) {
        // Get colours in rgba format
        s.setColour(rgbaTosRGB(segColour));
        s.setThickness(segThickness);
    }

    @Override
    public void decorateVert(final Vert v) {
        // Get colours in rgba format
        v.setColour((v.isCentroid()) ? rgbaTosRGB(DEFAULT_CENTROID_COLOUR) : rgbaTosRGB(vertColour));
        v.setThickness((v.isCentroid()) ? DEFAULT_CENTROID_THICKNESS : vertThickness);
    }

}