package ca.mcmaster.cas.se2aa4.a3.island.shaper;

import java.util.*;

import ca.mcmaster.cas.se2aa4.a3.island.components.ComponentCollections;
import ca.mcmaster.cas.se2aa4.a3.island.components.Tile;
import ca.mcmaster.cas.se2aa4.a3.island.components.TileTypes;

/**
 * LagoonShaper creates a lagoon.
 */
public class LagoonShaper implements ShapeFilter {

    static final double INNER_MODIFIER = 0.40, OUTER_MODIFIER = 0.8, DELTA_BOUND = 0.05;
    private double centreY, centreX, outerRadius, innerRadius;

    public LagoonShaper(int height, int width) {
        // Centres also serve as midpoints since we start from (0,0)
        // Finding the minimum of the centres gives us the radius of the largest circle
        // enclosed in the given rectangle
        centreY = ((double) height) / 2.0;
        centreX = ((double) width) / 2.0;
        outerRadius = Math.min(centreX, centreY) * OUTER_MODIFIER;
        innerRadius = Math.min(centreX, centreY) * INNER_MODIFIER;
    }

    public LagoonShaper(int height, int width, long seed) {
        this(height, width);
        Random innerRadiusModifier = new Random(seed);
        double delta = innerRadiusModifier.nextDouble() * DELTA_BOUND;
        innerRadius = Math.min(centreX, centreY) * (INNER_MODIFIER + delta);
    }

    private void assignTileType(final Tile tile) {
        double squareDist = distSquare(tile.getCentreX(), tile.getCentreY());
        // square the radii and use them as distance square comparison
        double innerRadius2 = Math.pow(innerRadius, 2);
        double outerRadius2 = Math.pow(outerRadius, 2);
        if (squareDist < innerRadius2) {
            tile.setTileType(TileTypes.LAGOON);
        } else if (squareDist > innerRadius2 && squareDist < outerRadius2) {
            tile.setTileType(TileTypes.LAND);
        } else {
            tile.setTileType(TileTypes.OCEAN);
        }
    }

    @Override
    public void shapeAllTiles(final ComponentCollections collection) {
        for (Tile tile : collection.getAllTiles().values()) {
            assignTileType(tile);
        }
    }

    private double distSquare(double x, double y) {
        return Math.pow((x - centreX), 2) + Math.pow((y - centreY), 2);
    }

    public static double getInnerModifier() {
        return INNER_MODIFIER;
    }

    public static double getOuterModifier() {
        return OUTER_MODIFIER;
    }

    public static double getDeltaBound() {
        return DELTA_BOUND;
    }

    public double getCentreY() {
        return centreY;
    }

    public double getCentreX() {
        return centreX;
    }

    public double getOuterRadius() {
        return outerRadius;
    }

    public double getInnerRadius() {
        return innerRadius;
    }
}
