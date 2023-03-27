package ca.mcmaster.cas.se2aa4.a3.island.moisture;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import ca.mcmaster.cas.se2aa4.a3.island.components.ComponentCollections;
import ca.mcmaster.cas.se2aa4.a3.island.water.river.River;

public class NormalMoisture implements Moisture {

    static final double DEFAULT_MOISTURE_LEVEL = 1.0, RIVER_MODIFIER = 1.5;

    private SoilTypes soilType;

    public NormalMoisture(SoilTypes soilType) {
        this.soilType = soilType;
    }

    public static Map<Double, MoistureLevels> getMoistureRanges() {
        Map<Double, MoistureLevels> MOISTURE_RANGES = new TreeMap<>();
        MOISTURE_RANGES.put(Double.MAX_VALUE, MoistureLevels.DRY_MOISTURE);
        MOISTURE_RANGES.put(-2.0, MoistureLevels.LAGOON_MOISTURE);
        MOISTURE_RANGES.put(-1.0, MoistureLevels.WATER_MOISTURE);
        MOISTURE_RANGES.put(-3.0, MoistureLevels.OCEAN_MOISTURE);
        MOISTURE_RANGES.put(0.1, MoistureLevels.WET_MOISTURE);
        MOISTURE_RANGES.put(0.2, MoistureLevels.HUMID_MOISTURE);
        MOISTURE_RANGES.put(0.4, MoistureLevels.LOW_MOISTURE);
        return MOISTURE_RANGES;
    }

    private static MoistureLevels assignMositure(double moist) {
        for (Map.Entry<Double, MoistureLevels> entry : getMoistureRanges().entrySet()) {
            if (Double.compare(moist, entry.getKey()) <= 0) {
                return entry.getValue();
            }
        }
        return MoistureLevels.DRY_MOISTURE;
    }

    private static double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2.0) + Math.pow(y2 - y1, 2.0));
    }

    private static double riverMoistureCalculation(double x1, double y1, double x2, double y2) {
        return Math.pow(Math.pow(x2 - x1, 2.0) + Math.pow(y2 - y1, 2.0), RIVER_MODIFIER);
    }

    @Override
    public Map<Integer, MoistureLevels> moisturizeAllTiles(ComponentCollections collection) {
        double moist, maxMoist = -Double.MAX_VALUE;
        double riverThickness;
        Iterable<Integer> tiles = collection.getAllTileIdxs();
        final double finalMaxMoist;
        Iterable<Integer> water = collection.getFreshWaterPoints(), river = collection.getRiverPoints();
        Map<Integer, Double> moistTiles = new HashMap<>();
        Map<Integer, MoistureLevels> moistureLevels = new HashMap<>();
        for (int tileIdx : tiles) {
            if (collection.isOceanTile(tileIdx)) {
                moistureLevels.put(tileIdx, MoistureLevels.OCEAN_MOISTURE);
            } else if (collection.isLagoonTile(tileIdx)) {
                moistureLevels.put(tileIdx, MoistureLevels.LAGOON_MOISTURE);
            } else if (collection.isLakeTile(tileIdx)) {
                moistureLevels.put(tileIdx, MoistureLevels.WATER_MOISTURE);
            } else {
                moist = Double.MAX_VALUE;
                for (Integer idx : water) {
                    moist = Math.min(distance(collection.getCentreX(tileIdx), collection.getCentreY(tileIdx),
                            collection.getPointX(idx), collection.getPointY(idx)), moist);
                }
                riverThickness = 0;
                for (int riverIdx : river) {
                    for (int edge : collection.getTileEdgeIdxs(riverIdx)) {
                        riverThickness = Math.max(riverThickness, collection.getEdgeThickness(edge));
                    }
                    moist = Math.min(riverMoistureCalculation(collection.getCentreX(tileIdx), collection.getCentreY(tileIdx),
                            collection.getPointX(riverIdx), collection.getPointY(riverIdx)) * (riverThickness / River.MIN_INIT_FLOW), moist);
                }
                maxMoist = Math.max(maxMoist, moist);
                moistTiles.put(tileIdx, moist);
            }
        }
        finalMaxMoist = maxMoist;
        moistTiles.replaceAll((k, v) -> v / finalMaxMoist / soilType.getMultiplier());
        moistTiles.forEach((k, v) -> moistureLevels.put(k, assignMositure(v)));
        return moistureLevels;
    }

}
