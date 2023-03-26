package ca.mcmaster.cas.se2aa4.a3.island.shaper;

import java.util.*;

import ca.mcmaster.cas.se2aa4.a3.island.components.ComponentCollections;
import ca.mcmaster.cas.se2aa4.a3.island.components.TileTypes;

/**
 * RectangularShaper creates a rectangular island.
 */
public class RectangleShaper implements Shaper {

    static final double GUARANTEED_MODIFIER = 0.65, OUTER_MODIFIER = 0.8, DELTA_BOUND = 0.10;
    private double centreY, centreX, minX, maxX, minY, maxY;
    private double islandWidth, islandHeight;
    private Random rand;

    public RectangleShaper(int height, int width, long seed) {
        centreY = ((double) height) / 2.0;
        centreX = ((double) width) / 2.0;
        rand = new Random(seed);
        islandWidth = generateIslandDimension(width);
        islandHeight = generateIslandDimension(height);
        minX = centreX - islandWidth;
        maxX = centreX + islandWidth;
        minY = centreY - islandHeight;
        maxY = centreY + islandHeight;
    }

    private double generateIslandDimension(int dimension) {
        double ratio = rand.nextDouble(GUARANTEED_MODIFIER, OUTER_MODIFIER + Double.MIN_VALUE);
        return ratio * (dimension / 2.0);
    }

    private TileTypes assignTileType(final int tileIdx, final ComponentCollections collection) {
        if (collection.getCentreX(tileIdx) >= minX && collection.getCentreX(tileIdx) <= maxX
                && collection.getCentreY(tileIdx) >= minY
                && collection.getCentreY(tileIdx) <= maxY) {
            return TileTypes.LAND;
        } else {
            return TileTypes.OCEAN;
        }
    }

    @Override
    public Map<Integer, TileTypes> shapeAllTiles(final ComponentCollections collection) {
        Map<Integer, TileTypes> tileTypes = new HashMap<>();
        collection.getAllTileIdxs().forEach((tileIdx) -> tileTypes.put(tileIdx, assignTileType(tileIdx, collection)));
        return tileTypes;
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
