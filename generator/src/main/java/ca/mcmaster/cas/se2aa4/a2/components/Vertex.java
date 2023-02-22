package ca.mcmaster.cas.se2aa4.a2.components;

/**
 * Vertex
 */
public class Vertex implements Vert, Comparable<Vertex> {

    private int[] colour;

    private float thickness;

    private double x, y;

    public Vertex() {
        colour = new int[4];
    }

    @Override
    public int compareTo(Vertex v) {
        int comp = Double.compare(x, v.x);
        return (comp != 0) ? comp : Double.compare(y, v.y);
    }

    @Override
    public void setColour(int r, int g, int b, int a) {
        colour[0] = r;
        colour[1] = g;
        colour[2] = b;
        colour[3] = a;
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
    public int[] getColour() {
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

}
