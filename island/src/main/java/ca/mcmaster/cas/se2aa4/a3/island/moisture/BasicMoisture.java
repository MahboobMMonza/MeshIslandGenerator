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
            tile.setMoistureLevel(MoistureLevels.DRY_MOISTURE);
            if (tile.getTileType().equals(TileTypes.OCEAN)) {
                tile.setMoistureLevel(MoistureLevels.OCEAN_MOISTURE);
            } else if (tile.getTileType().equals(TileTypes.LAGOON)) {
                tile.setMoistureLevel(MoistureLevels.LAGOON_MOISTURE);
            }
        }
    }

}
