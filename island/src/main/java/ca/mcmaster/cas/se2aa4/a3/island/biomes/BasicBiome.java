package ca.mcmaster.cas.se2aa4.a3.island.biomes;

import java.util.*;

import ca.mcmaster.cas.se2aa4.a3.island.components.*;

/**
 * BasicBiome
 */
public class BasicBiome implements Biome {

    public static final int OCEAN_COLOUR = 0xFF000046, WATER_COLOUR = 0xFF354CDE,
            SHORE_COLOUR = 0xFFFBEAB5, LAND_COLOUR = 0xFFFFFFED;

    @Override
    public void assignBiomes(ComponentCollections collection) {
        for (Tile tile : collection.getAllTiles().values()) {
            tile.setColour(LAND_COLOUR);
            /* if (tile.getTileType().equals(TileTypes.OCEAN)) {
                tile.setColour(OCEAN_COLOUR);
            } else if (tile.getTileType().equals(TileTypes.LAGOON)) {
                tile.setColour(WATER_COLOUR);
            } else if (collection.getShores().contains(tile.getIndex())) {
                tile.setColour(SHORE_COLOUR);
            } */
            if (tile.getElevationLevel() == -2) {
                tile.setColour(OCEAN_COLOUR);
            } else if (tile.getElevationLevel() == -1) {
                tile.setColour(WATER_COLOUR);
            } else if (tile.getElevationLevel() == 0) {
                tile.setColour(SHORE_COLOUR);
            }
        }

        for (Edge edge : collection.getAllEdges().values()) {
            edge.setColour(WATER_COLOUR);
        }
    }

}
