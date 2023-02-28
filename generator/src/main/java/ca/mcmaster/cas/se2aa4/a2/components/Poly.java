package ca.mcmaster.cas.se2aa4.a2.components;

import java.util.List;

public interface Poly extends Comparable<Poly> {

    void setCentroid(double x, double y);

    void setVertices(List<double[]> verticesList);

    void setNeighbours(List<double[]> neighbourCentroidList);

    void setFillColour(int r, int g, int b, int a);

    void setBorderColour(int r, int g, int b, int a);

    void setBorderThickness(float t);

    List<double[]> getVertexList();

    List<double[]> getNeigbourList();

    double getCentroidX();

    double getCentroidY();

    float getBorderThickness();

    int[] getFillColour();

    int[] getBorderColour();

}
