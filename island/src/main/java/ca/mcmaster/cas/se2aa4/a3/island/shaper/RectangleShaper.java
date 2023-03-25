package ca.mcmaster.cas.se2aa4.a3.island.shaper;

import java.util.*;

import ca.mcmaster.cas.se2aa4.a3.island.components.ComponentCollections;
import ca.mcmaster.cas.se2aa4.a3.island.components.Tile;
import ca.mcmaster.cas.se2aa4.a3.island.components.TileTypes;

/**
 * RectangularShaper creates a rectangular island.
 */
public class RectangleShaper implements ShapeFilter {

    static final double GUARANTEED_MODIFIER = 0.3, OUTER_MODIFIER = 0.6, DELTA_BOUND = 0.10;
    private double centreY, centreX, outerRadius, guaranteedRadius;
    private double islandWidth, islandHeight;

    public RectangleShaper(int height, int width) {
        // Centres also serve as midpoints since we start from (0,0)
        // Finding the minimum of the centres gives us the radius of the largest
        // rectangle
        // enclosed in the given rectangle
        centreY = ((double) height) / 2.0;
        centreX = ((double) width) / 2.0;
        outerRadius = Math.min(centreX, centreY) * OUTER_MODIFIER;
        guaranteedRadius = Math.min(centreX, centreY) * GUARANTEED_MODIFIER;
        islandWidth = generateIslandDimension(width);
        islandHeight = generateIslandDimension(height);
    }

    public RectangleShaper(int height, int width, long seed) {
        this(height, width);
        Random guaranteedRadiusModifier = new Random(seed);
        double delta = guaranteedRadiusModifier.nextDouble() * DELTA_BOUND;
        guaranteedRadius = Math.min(centreX, centreY) * (GUARANTEED_MODIFIER + delta);
    }

    private double generateIslandDimension(int dimension) {
        Random random = new Random();
        double ratio = 0.65 + 0.15 * random.nextDouble();
        return ratio * dimension;
    }

    private void assignTileType(final Tile tile) {
        double left = centreX - islandWidth / 2.0;
        double right = centreX + islandWidth / 2.0;
        double top = centreY - islandHeight / 2.0;
        double bottom = centreY + islandHeight / 2.0;
        if (tile.getCentreX() >= left && tile.getCentreX() <= right && tile.getCentreY() >= top
                && tile.getCentreY() <= bottom) {
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

    public double getIslandWidth() {
        return islandWidth;
    }

    public double getIslandHeight() {
        return islandHeight;
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

    public static double getGuaranteedModifier() {
        return GUARANTEED_MODIFIER;
    }

    public static double getOuterModifier() {
        return OUTER_MODIFIER;
    }

    public static double getDeltaBound() {
        return DELTA_BOUND;
    }
}