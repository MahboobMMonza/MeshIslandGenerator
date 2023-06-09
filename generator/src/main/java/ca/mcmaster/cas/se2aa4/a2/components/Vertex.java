package ca.mcmaster.cas.se2aa4.a2.components;

/**
 * Vertex class which serves as a high-level recorder for the data generated by
 * the Generator and any properties from teh Mesh when finalized before
 * conversion.
 */
public class Vertex implements Vert {

    static int COLOUR_BIT_SIZE = 8, MAX_COLOUR_VALUE = 0xff, MIN_COLOUR_VALUE = 0x00;

    /**
     * Converts the given red, green, blue, and alpha values into a single integer
     * following Java's sRGB model.
     *
     * @param r
     * @param g
     * @param b
     * @param a
     * @return the colour in Java's sRGB format.
     */
    private static int toSRGB(int r, int g, int b, int a) {
        r = rangeRestrict(r);
        g = rangeRestrict(g);
        b = rangeRestrict(b);
        a = rangeRestrict(a);
        return (((((a << COLOUR_BIT_SIZE) | r) << COLOUR_BIT_SIZE) | g) << COLOUR_BIT_SIZE) | b;
    }

    private static int rangeRestrict(int val) {
        return Math.max(Math.min(MAX_COLOUR_VALUE, val), MIN_COLOUR_VALUE);
    }

    private int colour;

    private float thickness;

    private double x, y;

    private boolean centroid;

    public Vertex() {
        centroid = false;
    }

    public Vertex(double x, double y) {
        this();
        setPosition(x, y);
    }

    @Override
    public int compareTo(Vert v) {
        int comp = Double.compare(x, v.getX());
        return (comp != 0) ? comp : Double.compare(y, v.getY());
    }

    @Override
    public void setColour(int r, int g, int b, int a) {
        colour = toSRGB(r, g, b, a);
    }

    @Override
    public void setColour(int rgba) {
        colour = rgba;
    }

    @Override
    public void setThickness(float t) {
        thickness = t;
    }

    @Override
    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void setCentroid() {
        centroid = true;
    }

    @Override
    public int getColour() {
        return colour;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public float getThickness() {
        return thickness;
    }

    @Override
    public boolean isCentroid() {
        return centroid;
    }

}
