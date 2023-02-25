package ca.mcmaster.cas.se2aa4.a2.decorator;

import ca.mcmaster.cas.se2aa4.a2.components.Poly;
import ca.mcmaster.cas.se2aa4.a2.components.Seg;
import ca.mcmaster.cas.se2aa4.a2.components.Vert;

/**
 * FixedDecorator
 */
public class FixedDecorator implements Decorator {

    static final int DEFAULT_POLY_FILL_COLOUR, DEFAULT_POLY_BORDER_COLOUR, DEFAULT_SEG_COLOUR, DEFAULT_VERT_COLOUR,
            DEFAULT_CENTROID_COLOUR;
    private static final int NUM_COLOURS, COLOUR_BIT_SIZE, TOTAL_BITS, LEFT_SHIFT;
    static final float DEFAULT_POLY_BORDER_THICKNESS, DEFAULT_SEG_THICKNESS, DEFAULT_VERT_THICKNESS,
            DEFAULT_CENTROID_THICKNESS, MAX_SEG_THICKNESS, MAX_VERT_THICKNESS, MAX_POLY_BORDER_THICKNESS,
            MIN_SEG_THICKNESS, MIN_VERT_THICKNESS, MIN_POLY_BORDER_THICKNESS;

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
        MAX_SEG_THICKNESS = 10f;
        MAX_VERT_THICKNESS = 10f;
        MAX_POLY_BORDER_THICKNESS = 10f;

        // Set colour-convertor-related constants
        NUM_COLOURS = 4;
        COLOUR_BIT_SIZE = 8;
        TOTAL_BITS = 32;
        LEFT_SHIFT = 24;
    }

    /**
     * Extracts the desired colour value when decorating a component.
     *
     * <h3>COLOUR INFORMATION STORAGE</h3>
     *
     * Colours are stored in integers by leveraging bit manipulation. The colours
     * stored here follow the following bit and index pattern:
     *
     * <p>
     * 31 - 24 : RED [0]
     * <p>
     * 23 - 16 : GREEN [1]
     * <p>
     * 15 - 08 : BLUE [2]
     * <p>
     * 07 - 00 : ALPHA [3]
     *
     * @param colour the the colour whose properties are being extracted.
     * @param index  the index of the property being extracted.
     * @return the value of the colour at the given index.
     */
    static int extractColourValue(final int colour, final int index) {
        int rightShift = TOTAL_BITS - (COLOUR_BIT_SIZE * (NUM_COLOURS - index));
        // Shift the colour so that the rightmost desired bit is at index 24, then shift
        // so that the rightmost bit is then at index 0 (big-endian)
        return (colour << rightShift) >>> LEFT_SHIFT;
    }

    private static int[] tryConversion(final String colourString, final int defaultValue) {
        int colour;
        final int[] result = new int[2];
        if (colourString.length() != 8) {
            result[0] = 0;
            result[1] = defaultValue;
        } else {
            try {
                colour = Integer.parseInt(colourString, 16);
                result[0] = 1;
                result[1] = colour;
            } catch (Exception e) {
                result[0] = 0;
                result[1] = defaultValue;
            }
        }
        return result;
    }

    private static float[] tryConversion(final String colourString, final float defaultValue, final float minValue,
            final float maxValue) {
        float thickness;
        final float[] result = new float[2];
        try {
            thickness = Float.parseFloat(colourString);
            if (thickness < minValue || thickness > maxValue) {
                result[0] = -1;
                result[1] = defaultValue;
            } else {
                result[0] = 1;
                result[1] = thickness;
            }
        } catch (final Exception e) {
            result[0] = -1;
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
        final int[] conversion = tryConversion(colourString, DEFAULT_POLY_FILL_COLOUR);
        polyFillColour = conversion[1];
        return conversion[0] == 1;
    }

    @Override
    public boolean setPolyBorderColour(final String colourString) {
        final int[] conversion = tryConversion(colourString, DEFAULT_POLY_BORDER_COLOUR);
        polyBorderColour = conversion[1];
        return conversion[0] == 1;
    }

    @Override
    public boolean setSegColour(final String colourString) {
        final int[] conversion = tryConversion(colourString, DEFAULT_SEG_COLOUR);
        segColour = conversion[1];
        return conversion[0] == 1;
    }

    @Override
    public boolean setVertColour(final String colourString) {
        final int[] conversion = tryConversion(colourString, DEFAULT_VERT_COLOUR);
        vertColour = conversion[1];
        return conversion[0] == 1;
    }

    @Override
    public boolean setSegThickness(final String thickness) {
        final float[] conversion = tryConversion(thickness, DEFAULT_SEG_THICKNESS, MIN_SEG_THICKNESS,
                MAX_SEG_THICKNESS);
        segThickness = conversion[1];
        return Float.compare(conversion[0], 0) > 0;
    }

    @Override
    public boolean setVertThickness(final String thickness) {
        final float[] conversion = tryConversion(thickness, DEFAULT_VERT_THICKNESS, MIN_VERT_THICKNESS,
                MAX_VERT_THICKNESS);
        vertThickness = conversion[1];
        return Float.compare(conversion[0], 0f) > 0;
    }

    @Override
    public boolean setPolyBorderThickness(final String thickness) {
        final float[] conversion = tryConversion(thickness, DEFAULT_POLY_BORDER_THICKNESS, MIN_POLY_BORDER_THICKNESS,
                MAX_POLY_BORDER_THICKNESS);
        polyBorderThickness = conversion[1];
        return Float.compare(conversion[0], 0) > 0;
    }

    @Override
    public void decoratePoly(final Poly p) {
        // Get colours in rgba format
        final int[] colourValues = new int[NUM_COLOURS];
        for (int i = 0; i < NUM_COLOURS; i++) {
            colourValues[i] = extractColourValue(polyFillColour, i);
        }
        p.setColour(colourValues[0], colourValues[1], colourValues[2], colourValues[3]);
        // Prepare colour value setting for border colour and thickness.
        // for (int i = 0; i < NUM_COLOURS; i++) {
        // colourValues[i] = extractColourValue(polyBorderColour, i);
        // }
        // p.setBorderColour(colourValues[0], colourValues[1], colourValues[2],
        // colourValues[3]);
        // p.setBorderThickness(polyBorderThickness);
    }

    @Override
    public void decorateSeg(final Seg s) {
        // Get colours in rgba format
        final int[] colourValues = new int[NUM_COLOURS];
        for (int i = 0; i < NUM_COLOURS; i++) {
            colourValues[i] = extractColourValue(segColour, i);
        }
        s.setColour(colourValues[0], colourValues[1], colourValues[2], colourValues[3]);
        s.setThickness(segThickness);
    }

    @Override
    public void decorateVert(final Vert v) {
        // Get colours in rgba format
        final int[] colourValues = new int[NUM_COLOURS];
        for (int i = 0; i < NUM_COLOURS; i++) {
            // Make sure to get the default centroid colour if the vertex is marked as a centroid
            colourValues[i] = extractColourValue((v.isCentroid()) ? DEFAULT_CENTROID_COLOUR : vertColour, i);
        }
        v.setColour(colourValues[0], colourValues[1], colourValues[2], colourValues[3]);
        v.setThickness((v.isCentroid()) ? DEFAULT_CENTROID_THICKNESS : vertThickness);
    }

}
