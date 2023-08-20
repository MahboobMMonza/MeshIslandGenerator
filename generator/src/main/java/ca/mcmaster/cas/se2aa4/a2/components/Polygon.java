package ca.mcmaster.cas.se2aa4.a2.components;

import java.util.ArrayList;
import java.util.List;

/**
 * Polygon class which serves as a high-level recorder for the data generated by
 * the Generator and any properties from the Mesh when finalized before
 * conversion.
 */
public class Polygon implements Poly {

    static int COLOUR_BIT_SIZE = 8, MAX_COLOUR_VALUE = 0xff, MIN_COLOUR_VALUE = 0x00;

    /**
     * Converts the given red, green, blue, and alpha values into a single integer
     * following Java's sRGB model.
     *
     * @param r colour's red value.
     * @param g colour's green value.
     * @param b colour's blue value.
     * @param a colour's alpha (transparency) value.
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

    private double centroidX, centroidY;

    private float borderThickness;

    private int fillColour, borderColour;

    private List<double[]> verticesList;

    private List<double[]> neighbourList;

    public Polygon() {
        verticesList = new ArrayList<>();
        neighbourList = new ArrayList<>();
    }

    public Polygon(double x, double y) {
        this();
        setCentroid(x, y);
    }

    @Override
    public int compareTo(Poly p) {
        int comp = Double.compare(centroidX, p.getCentroidX());
        return (comp != 0) ? comp : Double.compare(centroidY, p.getCentroidY());
    }

    @Override
    public List<double[]> getNeigbourList() {
        return neighbourList;
    }

    @Override
    public List<double[]> getVertexList() {
        return verticesList;
    }

    @Override
    public double getCentroidX() {
        return centroidX;
    }

    @Override
    public double getCentroidY() {
        return centroidY;
    }

    @Override
    public float getBorderThickness() {
        return borderThickness;
    }

    @Override
    public int getFillColour() {
        return fillColour;
    }

    @Override
    public int getBorderColour() {
        return borderColour;
    }

    @Override
    public void setCentroid(double x, double y) {
        centroidX = x;
        centroidY = y;
    }

    @Override
    public void setBorderThickness(float t) {
        borderThickness = t;
    }

    @Override
    public void setNeighbours(List<double[]> neighbourCentroidList) {
        neighbourList = neighbourCentroidList;
    }

    @Override
    public void setVertices(List<double[]> verticesList) {
        this.verticesList = verticesList;
    }

    @Override
    public void setFillColour(int r, int g, int b, int a) {
        fillColour = toSRGB(r, g, b, a);
    }

    @Override
    public void setBorderColour(int r, int g, int b, int a) {
        borderColour = toSRGB(r, g, b, a);
    }

    @Override
    public void setFillColour(int rgba) {
        fillColour = rgba;
    }

    @Override
    public void setBorderColour(int rgba) {
        borderColour = rgba;
    }

}