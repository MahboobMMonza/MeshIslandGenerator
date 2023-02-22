package ca.mcmaster.cas.se2aa4.a2.components;

import java.util.List;

/**
 * Polygon
 */
public class Polygon implements Poly, Comparable<Polygon> {

    private double centroidX, centroidY;
    private int[] colour;
    private List<double[]> verticesList;
    private List<double[]> neighbourList;

    @Override
    public double getCentroidX() {
        return centroidX;
    }

    @Override
    public double getCentroidY() {
        return centroidY;
    }

    @Override
    public int[] getColour() {
        return colour;
    }

    @Override
    public List<double[]> getVertexList() {
        return null;
    }

    @Override
    public void setCentroid(double x, double y) {
        centroidX = x;
        centroidY = y;
    }

    @Override
    public void setColour(int r, int g, int b, int a) {
        colour[0] = r;
        colour[1] = g;
        colour[2] = b;
        colour[3] = a;
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
    public int compareTo(Polygon p) {
        int comp = Double.compare(centroidX, p.centroidX);
        if (comp != 0) return comp;
        return Double.compare(centroidY, p.centroidY);
    }

}
