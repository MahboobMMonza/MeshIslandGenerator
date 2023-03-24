package ca.mcmaster.cas.se2aa4.a3.island.elevation;

import ca.mcmaster.cas.se2aa4.a3.island.components.*;

/**
 * BasicElevation
 */
public class BasicElevation implements Elevation {

    @Override
    public void elevateAllTiles(ComponentCollections collection) {
        for (Tile tile : collection.getAllTiles().values()) {
            if (tile.getTileType().equals(TileTypes.OCEAN)) {
                tile.setElevation(ElevationLevels.OCEAN_ELEVATION);
            } else if (tile.getTileType().equals(TileTypes.LAGOON)) {
                tile.setElevation(ElevationLevels.LAGOON_ELEVATION);
            } else if (collection.getShores().contains(tile.getIndex())) {
                tile.setElevation(ElevationLevels.LOW_ELEVATION);
            } else if (collection.getLakes().contains(tile.getIndex())) {
                tile.setElevation(ElevationLevels.WATER_ELEVATION);
            } else {
                tile.setElevation(ElevationLevels.MEDIUM_ELEVATION);
            }
        }
    }

}
