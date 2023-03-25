package ca.mcmaster.cas.se2aa4.a3.island.biomes;

import java.util.*;

import ca.mcmaster.cas.se2aa4.a3.island.components.*;
import ca.mcmaster.cas.se2aa4.a3.island.elevation.ElevationLevels;

/**
 * BasicBiome
 */
public class BasicBiome implements Biome {

    public static final int OCEAN_COLOUR = 0xFF2140A3, LAGOON_COLOUR = 0xFF2488D5,
            SHORE_COLOUR = 0xFFFBEAB5, LAND_COLOUR = 0xFFFFFFED, WATER_COLOUR = 0xFF1AA9D0;

    @Override
    public Map<Integer, Integer> assignTileBiomeColours(ComponentCollections collection) {
        Map<Integer, Integer> tileColours = new HashMap<>();
        for (int tileIdx : collection.getAllTileIdxs()) {
            tileColours.put(tileIdx, LAND_COLOUR);
            if (collection.getTileElevationLevel(tileIdx) == ElevationLevels.OCEAN_ELEVATION) {
                tileColours.put(tileIdx, OCEAN_COLOUR);
            } else if (collection.getTileElevationLevel(tileIdx) == ElevationLevels.LAGOON_ELEVATION) {
                tileColours.put(tileIdx, LAGOON_COLOUR);
            } else if (collection.getTileElevationLevel(tileIdx) == ElevationLevels.LOW_ELEVATION) {
                tileColours.put(tileIdx, SHORE_COLOUR);
            } else if (collection.getTileElevationLevel(tileIdx) == ElevationLevels.WATER_ELEVATION) {
                tileColours.put(tileIdx, WATER_COLOUR);
            }
        }
        return tileColours;
    }

    @Override
    public Map<Integer, Integer> assignEdgeBiomeColours(ComponentCollections collection) {
        Map<Integer, Integer> edgeColours =  new HashMap<>();
        collection.getAllEdgeIdxs().forEach((idx) -> edgeColours.put(idx, WATER_COLOUR));
        return edgeColours;
    }

}
