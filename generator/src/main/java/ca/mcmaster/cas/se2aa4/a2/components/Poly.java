package ca.mcmaster.cas.se2aa4.a2.components;

import java.util.List;

public interface Poly extends Comparable<Poly> {

    /**
     * Sets the centroid location of this Poly in 2D-space using double precision.
     *
     * @param x the X-coordintate of the centroid.
     * @param y the Y-coordinate of the centroid.
     */
    void setCentroid(double x, double y);

    /**
     * Sets the vertices of this Poly as a list of 2-element double arrays such that
     * the first element stores the X-coordintate, and the second stores the
     * Y-coordinate of the current vertex. The points must be stored such that each
     *
     * <p>
     * point is adjacent to the one before it and the one after it, and the first
     * and last points are adjacent to each other.
     * </p>
     *
     * @param verticesList the list of vertex coordinates.
     */
    void setVertices(List<double[]> verticesList);

    /**
     * Sets the centroids of the neighbouring Polys to this Poly as a list of
     * 2-element double array such that the first element is the X-coordinate and
     * the second element is the Y-coordinate of the centroid.
     *
     * @param neighbourCentroidList the list of neighbouring centroids of this Poly.
     */
    void setNeighbours(List<double[]> neighbourCentroidList);

    /**
     * Sets the details for the fill colour of this Poly.
     *
     * <p>
     * The values are stored in 8-bit RGBA colour value format. For the alpha value,
     * the higher the value, the more opaque it is.
     * </p>
     *
     * @param r the <em>red</em> value for the colour.
     * @param g the <em>green</em> value for the colour.
     * @param b the <em>blue</em> value for the colour.
     * @param a the <em>alpha</em> (transparency) value for the colour.
     */
    void setFillColour(int r, int g, int b, int a);

    /**
     * Sets the fill colour property to Java's sRGB colour representation with the
     * given colour value.
     *
     * @param rgba the colour value to set to.
     */
    void setFillColour(int rgba);

    /**
     * Sets the details for the border colour of this Poly.
     *
     * <p>
     * The values are stored in 8-bit RGBA colour value format. For the alpha value,
     * the higher the value, the more opaque it is.
     * </p>
     *
     * @param r the <em>red</em> value for the colour.
     * @param g the <em>green</em> value for the colour.
     * @param b the <em>blue</em> value for the colour.
     * @param a the <em>alpha</em> (transparency) value for the colour.
     */
    void setBorderColour(int r, int g, int b, int a);

    /**
     * Sets the border colour property to Java's sRGB colour representation with the
     * given colour value.
     *
     * @param rgba the colour value to set to.
     */
    void setBorderColour(int rgba);

    /**
     * Sets the thickness of the border of this Poly.
     *
     * @param t the thickness value to set to.
     */
    void setBorderThickness(float t);

    /**
     * Gets the list of 2-element double arrays storing the X and Y coordinates of
     * this Poly's vertices.
     *
     * @return the list of vertex coordinates.
     */
    List<double[]> getVertexList();

    /**
     * Gets the list of 2-element double arrays storing the X and Y coordinates of
     * the centroids of the Poly which neighbours this Poly.
     *
     * @return the list of neighbouring centroid coordinates.
     */
    List<double[]> getNeigbourList();

    /**
     * Gets the X-coordintate of this Poly's centroid.
     *
     * @return the X-coodrinate of the centroid.
     */
    double getCentroidX();

    /**
     * Gets the Y-coordintate of this Poly's centroid.
     *
     * @return the Y-coodrinate of the centroid.
     */
    double getCentroidY();

    /**
     * Gets the thickness of the borders of this Poly.
     *
     * @return the thickness.
     */
    float getBorderThickness();

    /**
     * Gets the fill colour information of this Poly as an integer with its bits
     * representing the colour in the format specified by Java's
     * {@link java.awt.Color.getRGB} method.
     *
     * @return the fill colour information of this Poly.
     */
    int getFillColour();

    /**
     * Gets the border colour information of this Poly as an integer with its bits
     * representing the colour in the format specified by Java's
     * {@link java.awt.Color.getRGB} method.
     *
     * @return the border colour information of this Poly.
     */
    int getBorderColour();

}
