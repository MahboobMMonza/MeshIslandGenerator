package ca.mcmaster.cas.se2aa4.a3.island.moisture;

import ca.mcmaster.cas.se2aa4.a3.island.components.ComponentCollections;
import ca.mcmaster.cas.se2aa4.a3.island.components.Tile;
import ca.mcmaster.cas.se2aa4.a3.island.components.TileTypes;

/**
 * BasicMoisture
 */
public class BasicMoisture implements Moisture {

    public static final int DEFAULT_MOIST = 0, OCEAN_MOIST = -2, WATER_MOIST = -1;

    @Override
    public void moisturizeAllTiles(ComponentCollections collection) {
        for (Tile tile : collection.getAllTiles().values()) {
            tile.setMoistureLevel(DEFAULT_MOIST);
            if (tile.getTileType().equals(TileTypes.OCEAN)) {
                tile.setMoistureLevel(OCEAN_MOIST);
            } else if (tile.getTileType().equals(TileTypes.LAGOON)) {
                tile.setMoistureLevel(WATER_MOIST);
            }
        }
    }

}
