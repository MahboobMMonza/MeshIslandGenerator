package ca.mcmaster.cas.se2aa4.a3.island.components;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.*;

/**
 * Point
 */
public class Point {

    private int index;

    private double x, y;

    private boolean centroid, capital, city;

    public Point(int index, Vertex vert) {
        this.index = index;
        x = vert.getX();
        y = vert.getY();
        String centroidVal = new String();
        for (Property property : vert.getPropertiesList()) {
            if (property.getKey().equals("centroid")) {
                centroidVal = property.getValue();
                break;
            }
        }
        centroid = (centroidVal.isEmpty()) ? false : Boolean.parseBoolean(centroidVal);
        capital = city = false;
    }

    public boolean isCity() {
        return city;
    }

    public void setCity(boolean city) {
        this.city = city & centroid;
    }

    public boolean isCapital() {
        return capital;
    }

    public void setCapital(boolean capital) {
        this.capital = capital & centroid;
    }

    public int getIndex() {
        return index;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public boolean isCentroid() {
        return centroid;
    }
}
