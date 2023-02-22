package ca.mcmaster.cas.se2aa4.a2.components;

/**
 * Segment
 */
public class Segment implements Seg, Comparable<Segment> {

    private double x1, y1, x2, y2;
    private int[] colour;
    private float thickness;

    @Override
    public int compareTo(Segment s) {
        int comp = Double.compare(x1, s.x1);
        if (comp != 0)
            return comp;
        comp = Double.compare(y1, s.y1);
        if (comp != 0)
            return comp;
        comp = Double.compare(x2, s.x2);
        if (comp != 0)
            return comp;
        return Double.compare(y2, s.y2);
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
    public void setEndpoints(double x1, double y1, double x2, double y2) {
        if (Double.compare(x1, x2) > 0 || (Double.compare(x1, x2) == 0 && Double.compare(y1, y2) > 0)) {
            this.x1 = x2;
            this.y1 = y2;
            this.x2 = x1;
            this.y2 = y1;
        } else {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }
    }

    @Override
    public int[] getColour() {
        return colour;
    }

    @Override
    public double getX1() {
        return x1;
    }

    @Override
    public double getX2() {
        return x2;
    }

    @Override
    public double getY1() {
        return y1;
    }

    @Override
    public double getY2() {
        return y2;
    }

    @Override
    public float getThickness() {
        return thickness;
    }

}
