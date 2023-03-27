package ca.mcmaster.cas.se2aa4.a3.island.shaper;

import java.util.*;

import ca.mcmaster.cas.se2aa4.a3.island.components.ComponentCollections;
import ca.mcmaster.cas.se2aa4.a3.island.components.TileTypes;

/**
 * RoundShaper creates a round island.
 */
public class RoundShaper implements Shaper {

    static final double GUARANTEED_MODIFIER = 0.55, OUTER_MODIFIER = 0.8;
    private double centreY, centreX, guaranteedRadius;

    public RoundShaper(int height, int width) {
        // Centres also serve as midpoints since we start from (0,0)
        // Finding the minimum of the centres gives us the radius of the largest circle
        // enclosed in the given rectangle
        centreY = ((double) height) / 2.0;
        centreX = ((double) width) / 2.0;
        guaranteedRadius = Math.min(centreX, centreY) * GUARANTEED_MODIFIER;
    }

    public RoundShaper(int height, int width, long seed) {
        this(height, width);
        Random guaranteedRadiusModifier = new Random(seed);
        double delta = guaranteedRadiusModifier.nextDouble(GUARANTEED_MODIFIER, OUTER_MODIFIER + Double.MIN_VALUE);
        guaranteedRadius = Math.min(centreX, centreY) * delta;
    }

    private TileTypes assignTileType(final int tileIdx, final ComponentCollections collection) {
        double squareDist = distSquare(collection.getCentreX(tileIdx), collection.getCentreY(tileIdx));
        // square the radii and use them as distance square comparison
        double guaranteedRadius2 = Math.pow(guaranteedRadius, 2);
        if (squareDist < guaranteedRadius2) {
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

    private double distSquare(double x, double y) {
        return Math.pow((x - centreX), 2) + Math.pow((y - centreY), 2);
    }

    public static double getGuaranteedModifier() {
        return GUARANTEED_MODIFIER;
    }

    public static double getOuterModifier() {
        return OUTER_MODIFIER;
    }

    public double getCentreY() {
        return centreY;
    }

    public double getCentreX() {
        return centreX;
    }

    public double getGuaranteedRadius() {
        return guaranteedRadius;
    }
}
