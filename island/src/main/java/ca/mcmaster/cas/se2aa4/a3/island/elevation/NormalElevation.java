package ca.mcmaster.cas.se2aa4.a3.island.elevation;

import java.util.*;

import ca.mcmaster.cas.se2aa4.a3.island.components.*;

/**
 * NormalElevation
 */
public class NormalElevation implements Elevation {

    static final double DEFAULT_SHORE_ELEVATION = 0;

    public Map<Double, ElevationLevels> getElevationRanges() {
        // This map is pseudo constant but declared this way for inheritance
        Map<Double, ElevationLevels> ELEVATION_RANGES = new TreeMap<>();
        ELEVATION_RANGES.put(-3.0, ElevationLevels.OCEAN_ELEVATION);
        ELEVATION_RANGES.put(-2.0, ElevationLevels.LAGOON_ELEVATION);
        ELEVATION_RANGES.put(-1.0, ElevationLevels.WATER_ELEVATION);
        ELEVATION_RANGES.put(1.0 / 3.0, ElevationLevels.LOW_ELEVATION);
        ELEVATION_RANGES.put(2.0 / 3.0, ElevationLevels.MEDIUM_ELEVATION);
        ELEVATION_RANGES.put(Double.MAX_VALUE, ElevationLevels.HIGH_ELEVATION);
        return ELEVATION_RANGES;
    }

    private static double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2.0) + Math.pow(y2 - y1, 2.0));
    }

    private ElevationLevels assignLevel(double normDist) {
        for (Map.Entry<Double, ElevationLevels> rangeEntry : getElevationRanges().entrySet()) {
            if (Double.compare(normDist, rangeEntry.getKey()) <= 0) {
                return rangeEntry.getValue();
            }
        }
        return ElevationLevels.HIGH_ELEVATION;
    }

    @Override
    public Map<Integer, ElevationLevels> elevateAllTiles(ComponentCollections collection) {
        double elev, maxElev = -Double.MAX_VALUE;
        final double finalMaxElev;
        Iterable<Integer> shores = collection.getShores();
        Map<Integer, Double> tileElevs = new HashMap<>();
        Map<Integer, ElevationLevels> elevationLevels = new HashMap<>();
        for (int index : collection.getAllTileIdxs()) {
            if (collection.isOceanTile(index)) {
                elevationLevels.put(index, ElevationLevels.OCEAN_ELEVATION);
            } else if (collection.isLagoonTile(index)) {
                elevationLevels.put(index, ElevationLevels.LAGOON_ELEVATION);
            } else if (collection.isLakeTile(index)) {
                elevationLevels.put(index, ElevationLevels.WATER_ELEVATION);
            } else {
                elev = Double.MAX_VALUE;
                for (Integer idx : shores) {
                    elev = Math.max(Math.min(distance(collection.getCentreX(index), collection.getCentreY(index),
                            collection.getCentreX(idx), collection.getCentreY(idx)), elev), Double.MIN_VALUE);
                }
                maxElev = Math.max(maxElev, elev);
                tileElevs.put(index, elev);
            }
        }
        finalMaxElev = maxElev;
        tileElevs.replaceAll(
                (idx, elevValue) -> (Double.compare(finalMaxElev, Double.MIN_VALUE) == 0) ? DEFAULT_SHORE_ELEVATION
                        : elevValue / finalMaxElev);
        tileElevs.forEach((tileIdx, elevation) -> elevationLevels.put(tileIdx, assignLevel(elevation)));
        return elevationLevels;
    }
}
