package ca.mcmaster.cas.se2aa4.a3.island.biomes;

import java.util.*;

import ca.mcmaster.cas.se2aa4.a3.island.components.*;
import ca.mcmaster.cas.se2aa4.a3.island.elevation.*;
import ca.mcmaster.cas.se2aa4.a3.island.moisture.*;

/**
 * BasicBiome
 */
public class RainforestBiome implements Biome {

    public static final int OCEAN_COLOUR = 0xFF253E92, LAGOON_COLOUR = 0xFF3F7FB8,
            SUPER_DRY_COLOUR = 0xFF8C8C5C, LOW_LOW_COLOUR = 0xFF8EBF22, WATER_COLOUR = 0xFF60637C,
            LOW_HUMID_COLOUR = 0xFF5FB020, DENSE_FOREST_COLOUR = 0xFF00A33C, MED_LOW_COLOUR = 0xFFAEB448,
            MED_HUMID_COLOUR = 0xFF09A90C, HIGH_DRY_COLOUR = 0xFFE8C38F, HIGH_LOW_COLOUR = 0xFF7F7F48,
            HIGH_HUMID_COLOUR = 0xFF7AAD36, HIGH_WET_COLOUR = 0xFF9EC43A;

    public static final Map<Integer, Map<Integer, Integer>> RAINFOREST_COLOUR_PALETTE;

    static {
        RAINFOREST_COLOUR_PALETTE = new HashMap<>();
        Map<Integer, Integer> oceanColours = new HashMap<>();
        Map<Integer, Integer> lagoonColours = new HashMap<>();
        Map<Integer, Integer> waterColours = new HashMap<>();
        Map<Integer, Integer> lowColours = new HashMap<>();
        Map<Integer, Integer> medColours = new HashMap<>();
        Map<Integer, Integer> highColours = new HashMap<>();
        for (MoistureLevels levels : MoistureLevels.values()) {
            oceanColours.put(levels.getMoistureID(), OCEAN_COLOUR);
            lagoonColours.put(levels.getMoistureID(), LAGOON_COLOUR);
            waterColours.put(levels.getMoistureID(), WATER_COLOUR);
        }
        RAINFOREST_COLOUR_PALETTE.put(ElevationLevels.OCEAN_ELEVATION.getElevationID(), oceanColours);
        RAINFOREST_COLOUR_PALETTE.put(ElevationLevels.LAGOON_ELEVATION.getElevationID(), lagoonColours);
        RAINFOREST_COLOUR_PALETTE.put(ElevationLevels.WATER_ELEVATION.getElevationID(), waterColours);
        lowColours.put(MoistureLevels.OCEAN_MOISTURE.getMoistureID(), OCEAN_COLOUR);
        lowColours.put(MoistureLevels.LAGOON_MOISTURE.getMoistureID(), LAGOON_COLOUR);
        lowColours.put(MoistureLevels.WATER_MOISTURE.getMoistureID(), WATER_COLOUR);
        lowColours.put(MoistureLevels.DRY_MOISTURE.getMoistureID(), SUPER_DRY_COLOUR);
        lowColours.put(MoistureLevels.LOW_MOISTURE.getMoistureID(), LOW_LOW_COLOUR);
        lowColours.put(MoistureLevels.HUMID_MOISTURE.getMoistureID(), LOW_HUMID_COLOUR);
        lowColours.put(MoistureLevels.WET_MOISTURE.getMoistureID(), DENSE_FOREST_COLOUR);
        medColours.put(MoistureLevels.OCEAN_MOISTURE.getMoistureID(), OCEAN_COLOUR);
        medColours.put(MoistureLevels.LAGOON_MOISTURE.getMoistureID(), LAGOON_COLOUR);
        medColours.put(MoistureLevels.WATER_MOISTURE.getMoistureID(), WATER_COLOUR);
        medColours.put(MoistureLevels.DRY_MOISTURE.getMoistureID(), SUPER_DRY_COLOUR);
        medColours.put(MoistureLevels.LOW_MOISTURE.getMoistureID(), MED_LOW_COLOUR);
        medColours.put(MoistureLevels.HUMID_MOISTURE.getMoistureID(), MED_HUMID_COLOUR);
        medColours.put(MoistureLevels.WET_MOISTURE.getMoistureID(), DENSE_FOREST_COLOUR);
        highColours.put(MoistureLevels.OCEAN_MOISTURE.getMoistureID(), OCEAN_COLOUR);
        highColours.put(MoistureLevels.LAGOON_MOISTURE.getMoistureID(), LAGOON_COLOUR);
        highColours.put(MoistureLevels.WATER_MOISTURE.getMoistureID(), WATER_COLOUR);
        highColours.put(MoistureLevels.DRY_MOISTURE.getMoistureID(), HIGH_DRY_COLOUR);
        highColours.put(MoistureLevels.LOW_MOISTURE.getMoistureID(), HIGH_LOW_COLOUR);
        highColours.put(MoistureLevels.HUMID_MOISTURE.getMoistureID(), HIGH_HUMID_COLOUR);
        highColours.put(MoistureLevels.WET_MOISTURE.getMoistureID(), HIGH_WET_COLOUR);
        RAINFOREST_COLOUR_PALETTE.put(ElevationLevels.LOW_ELEVATION.getElevationID(), lowColours);
        RAINFOREST_COLOUR_PALETTE.put(ElevationLevels.MEDIUM_ELEVATION.getElevationID(), medColours);
        RAINFOREST_COLOUR_PALETTE.put(ElevationLevels.HIGH_ELEVATION.getElevationID(), highColours);
    }

    @Override
    public Map<Integer, Integer> assignTileBiomeColours(ComponentCollections collection) {
        Map<Integer, Integer> tileColours = new HashMap<>();
        collection.getAllTileIdxs()
                .forEach((tileIdx) -> tileColours.put(tileIdx,
                        RAINFOREST_COLOUR_PALETTE.get(collection.getTileElevationLevel(tileIdx).getElevationID())
                                .get(collection.getTileMoistureLevel(tileIdx).getMoistureID())));
        return tileColours;
    }

    @Override
    public Map<Integer, Integer> assignEdgeBiomeColours(ComponentCollections collection) {
        Map<Integer, Integer> edgeColours = new HashMap<>();
        collection.getAllEdgeIdxs().forEach((idx) -> edgeColours.put(idx, WATER_COLOUR));
        return edgeColours;
    }

}

/*
 * (low, dry) : (0xFF8C8C5C)
 * (low, low) : (0xFF8EBF22)
 * (low, humid) : (0xFF5FB020)
 * (low, wet) : (0xFF00A33C)
 * (medium, dry) : (0xFF8C8C5C)
 * (medium, low) : (0xFFAEB448)
 * (medium, humid) : (0xFF09A90C)
 * (medium, wet) : (0xFF00A33C)
 * (high, dry) : (0xFFE8C38F)
 * (high, low) : (0xFF7F7F48)
 * (high, humid) : (0xFF7AAD36)
 * (high, wet) : (0xFF9EC43A)
 */
