package ca.mcmaster.cas.se2aa4.a3.island.moisture;

import java.util.*;

import ca.mcmaster.cas.se2aa4.a3.island.components.*;

/**
 * BasicMoisture
 */
public class BasicMoisture implements Moisture {

    private MoistureLevels assignMoisture(TileTypes type) {
        if (type.equals(TileTypes.OCEAN)) {
            return MoistureLevels.OCEAN_MOISTURE;
        } else if (type.equals(TileTypes.LAGOON)) {
            return MoistureLevels.LAGOON_MOISTURE;
        }
        return MoistureLevels.LOW_MOISTURE;
    }

    @Override
    public Map<Integer, MoistureLevels> moisturizeAllTiles(ComponentCollections collection) {
        Map<Integer, MoistureLevels> tileMoistures = new HashMap<>();
        collection.getAllTileIdxs()
                .forEach((tileIdx) -> tileMoistures.put(tileIdx, assignMoisture(collection.getTileType(tileIdx))));
        return tileMoistures;
    }

}
