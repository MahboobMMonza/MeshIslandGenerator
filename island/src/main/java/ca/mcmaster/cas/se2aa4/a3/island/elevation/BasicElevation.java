package ca.mcmaster.cas.se2aa4.a3.island.elevation;

import ca.mcmaster.cas.se2aa4.a3.island.components.*;

/**
 * BasicElevation
 */
public class BasicElevation implements Elevation {

    public static final int OCEAN_ELEV = -2, WATER_ELEV = -1, SHORE_ELEV = 0,
            DEFAULT_ELEV = 1;

    @Override
    public void elevateAllTiles(ComponentCollections collection) {
        for (Tile tile : collection.getAllTiles().values()) {
            if (tile.getTileType().equals(TileTypes.OCEAN)) {
                tile.setElevationLevel(OCEAN_ELEV);
            } else if (tile.getTileType().equals(TileTypes.LAGOON)) {
                tile.setElevationLevel(WATER_ELEV);
            } else if (collection.getShores().contains(tile.getIndex())) {
                tile.setElevationLevel(SHORE_ELEV);
            } else {
                tile.setElevationLevel(DEFAULT_ELEV);
            }
        }
    }

}
