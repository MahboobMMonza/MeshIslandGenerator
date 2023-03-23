package ca.mcmaster.cas.se2aa4.a2.components;

public interface Seg extends Comparable<Seg> {

    /**
     * Sets the details for the colour of this Seg.
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
    void setColour(int r, int g, int b, int a);

    /**
     * Sets the colour property to Java's sRGB colour representation with the
     * given colour value.
     *
     * @param rgba the colour value to set to.
     */
    void setColour(int rgba);

    /**
     * Gets the thickness of this Seg.
     *
     * @return the thickness.
     */
    void setThickness(float t);

    /**
     * Sets the endpoints of this Seg to the given coordintate values in double
     * precicion in 2D-space.
     *
     * @param x1 The X-coordinate of the first endpoint.
     * @param y1 The Y-coordinate of the first endpoint.
     * @param x2 The X-coordinate of the second endpoint.
     * @param y2 The Y-coordinate of the second endpoint.
     */
    void setEndpoints(double x1, double y1, double x2, double y2);

    /**
     * Sets the border colour property to Java's sRGB colour representation with the
     * given colour value.
     *
     * @param rgba the colour value to set to.
     */
    int getColour();

    /**
     * Gets the X-coordinate of the first endpoint of this Seg.
     *
     * @return the X-coordinate of enpoint 1.
     */
    double getX1();

    /**
     * Gets the X-coordinate of the second endpoint of this Seg.
     *
     * @return the X-coordinate of enpoint 2.
     */
    double getX2();

    /**
     * Gets the Y-coordinate of the first endpoint of this Seg.
     *
     * @return the Y-coordinate of enpoint 1.
     */
    double getY1();

    /**
     * Gets the Y-coordinate of the second endpoint of this Seg.
     *
     * @return the Y-coordinate of enpoint 2.
     */
    double getY2();

    /**
     * Gets the thickness of the borders of this Seg.
     *
     * @return the thickness.
     */
    float getThickness();

}
