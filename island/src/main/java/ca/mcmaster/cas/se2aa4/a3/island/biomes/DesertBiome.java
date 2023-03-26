package ca.mcmaster.cas.se2aa4.a3.island.biomes;

import java.util.*;

import ca.mcmaster.cas.se2aa4.a3.island.components.*;
import ca.mcmaster.cas.se2aa4.a3.island.elevation.*;
import ca.mcmaster.cas.se2aa4.a3.island.moisture.*;

/**
 * DesertBiome
 */
public class DesertBiome implements Biome {

    public static final int OCEAN_COLOUR = 0xFF253E92, LAGOON_COLOUR = 0xFF0096FF,
            SANDWET_COLOUR = 0xFFD89E7A, WATER_COLOUR = 0xFF0092A3,
            SAND_COLOUR = 0xFFF8D19F, DIRT_COLOUR = 0xFFA07040, MOUNTAIN_COLOUR = 0xFF42280E,
            CACTUS_COLOUR = 0xFF9ACD32, PALE_CACTUS_COLOUR = 0xFFAFA77B;

    public static final Map<Integer, Map<Integer, Integer>> DESERT_COLOUR_PALETTE;

    static {
        DESERT_COLOUR_PALETTE = new HashMap<>();
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
        DESERT_COLOUR_PALETTE.put(ElevationLevels.OCEAN_ELEVATION.getElevationID(), oceanColours);
        DESERT_COLOUR_PALETTE.put(ElevationLevels.LAGOON_ELEVATION.getElevationID(), lagoonColours);
        DESERT_COLOUR_PALETTE.put(ElevationLevels.WATER_ELEVATION.getElevationID(), waterColours);
        lowColours.put(MoistureLevels.OCEAN_MOISTURE.getMoistureID(), OCEAN_COLOUR);
        lowColours.put(MoistureLevels.LAGOON_MOISTURE.getMoistureID(), LAGOON_COLOUR);
        lowColours.put(MoistureLevels.WATER_MOISTURE.getMoistureID(), WATER_COLOUR);
        lowColours.put(MoistureLevels.DRY_MOISTURE.getMoistureID(), SAND_COLOUR);
        lowColours.put(MoistureLevels.LOW_MOISTURE.getMoistureID(), SANDWET_COLOUR);
        lowColours.put(MoistureLevels.HUMID_MOISTURE.getMoistureID(), DIRT_COLOUR);
        lowColours.put(MoistureLevels.WET_MOISTURE.getMoistureID(), CACTUS_COLOUR);
        medColours.put(MoistureLevels.OCEAN_MOISTURE.getMoistureID(), OCEAN_COLOUR);
        medColours.put(MoistureLevels.LAGOON_MOISTURE.getMoistureID(), LAGOON_COLOUR);
        medColours.put(MoistureLevels.WATER_MOISTURE.getMoistureID(), WATER_COLOUR);
        medColours.put(MoistureLevels.DRY_MOISTURE.getMoistureID(), SAND_COLOUR);
        medColours.put(MoistureLevels.LOW_MOISTURE.getMoistureID(), SANDWET_COLOUR);
        medColours.put(MoistureLevels.HUMID_MOISTURE.getMoistureID(), PALE_CACTUS_COLOUR);
        medColours.put(MoistureLevels.WET_MOISTURE.getMoistureID(), PALE_CACTUS_COLOUR);
        highColours.put(MoistureLevels.OCEAN_MOISTURE.getMoistureID(), OCEAN_COLOUR);
        highColours.put(MoistureLevels.LAGOON_MOISTURE.getMoistureID(), LAGOON_COLOUR);
        highColours.put(MoistureLevels.WATER_MOISTURE.getMoistureID(), WATER_COLOUR);
        highColours.put(MoistureLevels.DRY_MOISTURE.getMoistureID(), SAND_COLOUR);
        highColours.put(MoistureLevels.LOW_MOISTURE.getMoistureID(), SANDWET_COLOUR);
        highColours.put(MoistureLevels.HUMID_MOISTURE.getMoistureID(), MOUNTAIN_COLOUR);
        highColours.put(MoistureLevels.WET_MOISTURE.getMoistureID(), MOUNTAIN_COLOUR);
        DESERT_COLOUR_PALETTE.put(ElevationLevels.LOW_ELEVATION.getElevationID(), lowColours);
        DESERT_COLOUR_PALETTE.put(ElevationLevels.MEDIUM_ELEVATION.getElevationID(), medColours);
        DESERT_COLOUR_PALETTE.put(ElevationLevels.HIGH_ELEVATION.getElevationID(), highColours);
    }

    @Override
    public Map<Integer, Integer> assignTileBiomeColours(ComponentCollections collection) {
        Map<Integer, Integer> tileColours = new HashMap<>();
        collection.getAllTileIdxs()
                .forEach((tileIdx) -> tileColours.put(tileIdx,
                        DESERT_COLOUR_PALETTE.get(collection.getTileElevationLevel(tileIdx).getElevationID())
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
