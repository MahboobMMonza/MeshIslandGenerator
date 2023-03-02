package ca.mcmaster.cas.se2aa4.a2.components;

public interface Vert extends Comparable<Vert> {

    /**
     * Sets the details of the colour of this Vert.
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
     * Gets the thickness of this Vert.
     *
     * @return the thickness.
     */
    void setThickness(float t);

    /**
     * Sets the X and Y coordinate positions of this Vert in 2D-space with double
     * precision.
     *
     * @param x the X-coordinate of this Vert.
     * @param y the Y-coordinate of this Vert.
     */
    void setPosition(double x, double y);

    /**
     * Sets the colour property to Java's sRGB colour representation with the
     * given colour value.
     *
     * @param rgba the colour value to set to.
     */
    int getColour();

    /**
     * Gets the X-coordinate of this Vert.
     *
     * @return the X-coordinate.
     */
    double getX();

    /**
     * Gets the Y-coordinate of this Vert.
     *
     * @return the Y-coordinate.
     */
    double getY();

    /**
     * Gets the thickness of this Vert.
     *
     * @return the thickness.
     */
    float getThickness();

    /**
     * Sets this Vert as a centroid.
     */
    void setCentroid();

    /**
     * Determines if this Vert is a centroid.
     *
     * @return whether this Vert is a centroid.
     */
    boolean isCentroid();
}
