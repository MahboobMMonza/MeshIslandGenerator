package ca.mcmaster.cas.se2aa4.a3.island.elevation;

import java.util.HashMap;
import java.util.Map;

import ca.mcmaster.cas.se2aa4.a3.island.components.*;

/**
 * BasicElevation
 */
public class BasicElevation implements Elevation {

    @Override
    public Map<Integer, ElevationLevels> elevateAllTiles(ComponentCollections collection) {
        Map<Integer, ElevationLevels> elevationLevels = new HashMap<>();
        for (int tileIdx : collection.getAllTileIdxs()) {
            if (collection.getTileType(tileIdx).equals(TileTypes.OCEAN)) {
                elevationLevels.put(tileIdx, ElevationLevels.OCEAN_ELEVATION);
            } else if (collection.getTileType(tileIdx).equals(TileTypes.LAGOON)) {
                elevationLevels.put(tileIdx, ElevationLevels.LAGOON_ELEVATION);
            } else if (collection.isShoreTile(tileIdx)) {
                elevationLevels.put(tileIdx, ElevationLevels.LOW_ELEVATION);
            } else if (collection.isLakeTile(tileIdx)) {
                elevationLevels.put(tileIdx, ElevationLevels.WATER_ELEVATION);
            } else {
                elevationLevels.put(tileIdx, ElevationLevels.MEDIUM_ELEVATION);
            }
        }
        return elevationLevels;
    }

}
