package ca.mcmaster.cas.se2aa4.a3.island.moisture;

import ca.mcmaster.cas.se2aa4.a3.island.components.*;


/**
 * BasicMoisture
 */
public class BasicMoisture implements Moisture {

    @Override
    public void moisturizeAllTiles(ComponentCollections collection) {
        for (Tile tile : collection.getAllTiles().values()) {
            tile.setMoisture(MoistureLevels.DRY_MOISTURE);
            if (tile.getTileType().equals(TileTypes.OCEAN)) {
                tile.setMoisture(MoistureLevels.OCEAN_MOISTURE);
            } else if (tile.getTileType().equals(TileTypes.LAGOON)) {
                tile.setMoisture(MoistureLevels.LAGOON_MOISTURE);
            }
        }
    }

}
