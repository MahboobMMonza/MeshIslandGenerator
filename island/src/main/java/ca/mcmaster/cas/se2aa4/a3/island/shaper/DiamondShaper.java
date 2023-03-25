package ca.mcmaster.cas.se2aa4.a3.island.shaper;

import java.util.*;

import ca.mcmaster.cas.se2aa4.a3.island.components.ComponentCollections;
import ca.mcmaster.cas.se2aa4.a3.island.components.Tile;
import ca.mcmaster.cas.se2aa4.a3.island.components.TileTypes;

/**
 * DiamondShaper creates a diamond-shaped island.
 */
public class DiamondShaper implements ShapeFilter {

    static final double GUARANTEED_MODIFIER = 0.7, OUTER_MODIFIER = 0.8, DELTA_BOUND = 0.10;
    private double centreY, centreX, outerRadius, guaranteedRadius;

    public DiamondShaper(int height, int width) {
        // Centres also serve as midpoints since we start from (0,0)
        centreY = ((double) height) / 2.0;
        centreX = ((double) width) / 2.0;
        // Radius is half the distance between the center and the furthest edge of the
        // diamond
        outerRadius = Math.min(centreX, centreY) * OUTER_MODIFIER / Math.sqrt(2);
        guaranteedRadius = Math.min(centreX, centreY) * GUARANTEED_MODIFIER / Math.sqrt(2);
    }

    public DiamondShaper(int height, int width, long seed) {
        this(height, width);
        Random guaranteedRadiusModifier = new Random(seed);
        double delta = guaranteedRadiusModifier.nextDouble() * DELTA_BOUND;
        guaranteedRadius = Math.min(centreX, centreY) * (GUARANTEED_MODIFIER + delta) / Math.sqrt(2);
    }

    private void assignTileType(final Tile tile) {
        double x = tile.getCentreX();
        double y = tile.getCentreY();
        double deltaX = Math.abs(x - centreX);
        double deltaY = Math.abs(y - centreY);
        // Check if the tile is within the diamond-shaped area
        if (deltaX / outerRadius + deltaY / outerRadius <= 1
                && deltaX / guaranteedRadius + deltaY / guaranteedRadius <= 1) {
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

    public static double getGuaranteedModifier() {
        return GUARANTEED_MODIFIER;
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

    public double getGuaranteedRadius() {
        return guaranteedRadius;
    }
}
