package ca.mcmaster.cas.se2aa4.a3.island.shaper;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import ca.mcmaster.cas.se2aa4.a3.island.components.ComponentCollections;
import ca.mcmaster.cas.se2aa4.a3.island.components.TileTypes;

/**
 * OvalShaper creates an oval-shaped island.
 */
public class OvalShaper implements Shaper {

    static final double GUARANTEED_MODIFIER = 0.55, OUTER_MODIFIER = 0.8;
    private double centreY, centreX, guaranteedRadiusX, guaranteedRadiusY;

    public OvalShaper(int height, int width) {
        centreY = ((double) height) / 2.0;
        centreX = ((double) width) / 2.0;
        guaranteedRadiusX = Math.min(centreX, centreY) * GUARANTEED_MODIFIER / 2.0;
        guaranteedRadiusY = Math.min(centreX, centreY) * GUARANTEED_MODIFIER;
    }

    public OvalShaper(int height, int width, long seed) {
        this(height, width);
        Random guaranteedRadiusModifier = new Random(seed);
        double delta = guaranteedRadiusModifier.nextDouble(GUARANTEED_MODIFIER, OUTER_MODIFIER + Double.MIN_VALUE);
        guaranteedRadiusX = Math.min(centreX, centreY) * delta / 2.0;
        guaranteedRadiusY = Math.min(centreX, centreY) * delta;
    }

    private TileTypes assignTileType(final int tileIdx, final ComponentCollections collection) {
        double xDist = Math.abs(collection.getCentreX(tileIdx) - centreX);
        double yDist = Math.abs(collection.getCentreY(tileIdx) - centreY);
        double xRadius2 = Math.pow(guaranteedRadiusX, 2);
        double yRadius2 = Math.pow(guaranteedRadiusY, 2);
        if ((Math.pow(xDist, 2) / xRadius2) + (Math.pow(yDist, 2) / yRadius2) < 1) {
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

    public double getGuaranteedRadiusX() {
        return guaranteedRadiusX;
    }

    public double getGuaranteedRadiusY() {
        return guaranteedRadiusY;
    }
}
