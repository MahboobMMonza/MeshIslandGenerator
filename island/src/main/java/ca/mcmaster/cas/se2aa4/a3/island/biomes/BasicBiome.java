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
    public void assignBiomes(ComponentCollections collection) {
        for (Tile tile : collection.getAllTiles().values()) {
            tile.setColour(LAND_COLOUR);
            if (tile.getElevation() == ElevationLevels.OCEAN_ELEVATION) {
                tile.setColour(OCEAN_COLOUR);
            } else if (tile.getElevation() == ElevationLevels.LAGOON_ELEVATION) {
                tile.setColour(LAGOON_COLOUR);
            } else if (tile.getElevation() == ElevationLevels.LOW_ELEVATION) {
                tile.setColour(SHORE_COLOUR);
            } else if (tile.getElevation() == ElevationLevels.WATER_ELEVATION) {
                tile.setColour(WATER_COLOUR);
            }
        }

        for (Edge edge : collection.getAllEdges().values()) {
            edge.setColour(LAGOON_COLOUR);
        }
    }

}
