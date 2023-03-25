package ca.mcmaster.cas.se2aa4.a3.island.biomes;

import java.util.*;

import ca.mcmaster.cas.se2aa4.a3.island.components.*;
import ca.mcmaster.cas.se2aa4.a3.island.elevation.*;
import ca.mcmaster.cas.se2aa4.a3.island.moisture.*;

/**
 * BasicBiome
 */
public class ArcticBiome implements Biome {

    public static final int OCEAN_COLOUR = 0xFF253E92, LAGOON_COLOUR = 0xFF0096FF,
            BARE_COLOUR = 0xFFBBBBBB, WATER_COLOUR = 0xFF82EEFD,
            TUNDRA_COLOUR = 0xFFDDDDBB, TAIGA_COLOUR = 0xFFCBD4BB, HIGH_ROCK_COLOUR = 0xFF999999,
            SNOW_COLOUR = 0xFFFFFFFF, MEDIUM_WET_COLOUR = 0xFFDDE5F4, MEDIUM_HUMID_COLOUR = 0xFFBAD4E9,
            LOW_SNOW_COLOUR = 0xFFFCEDED;

    public static final Map<Integer, Map<Integer, Integer>> ARCTIC_COLOUR_PALETTE;

    static {
        ARCTIC_COLOUR_PALETTE = new HashMap<>();
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
        ARCTIC_COLOUR_PALETTE.put(ElevationLevels.OCEAN_ELEVATION.getElevationID(), oceanColours);
        ARCTIC_COLOUR_PALETTE.put(ElevationLevels.LAGOON_ELEVATION.getElevationID(), lagoonColours);
        ARCTIC_COLOUR_PALETTE.put(ElevationLevels.WATER_ELEVATION.getElevationID(), waterColours);
        lowColours.put(MoistureLevels.OCEAN_MOISTURE.getMoistureID(), OCEAN_COLOUR);
        lowColours.put(MoistureLevels.LAGOON_MOISTURE.getMoistureID(), LAGOON_COLOUR);
        lowColours.put(MoistureLevels.WATER_MOISTURE.getMoistureID(), WATER_COLOUR);
        lowColours.put(MoistureLevels.DRY_MOISTURE.getMoistureID(), BARE_COLOUR);
        lowColours.put(MoistureLevels.LOW_MOISTURE.getMoistureID(), LOW_SNOW_COLOUR);
        lowColours.put(MoistureLevels.HUMID_MOISTURE.getMoistureID(), TAIGA_COLOUR);
        lowColours.put(MoistureLevels.WET_MOISTURE.getMoistureID(), TAIGA_COLOUR);
        medColours.put(MoistureLevels.OCEAN_MOISTURE.getMoistureID(), OCEAN_COLOUR);
        medColours.put(MoistureLevels.LAGOON_MOISTURE.getMoistureID(), LAGOON_COLOUR);
        medColours.put(MoistureLevels.WATER_MOISTURE.getMoistureID(), WATER_COLOUR);
        medColours.put(MoistureLevels.DRY_MOISTURE.getMoistureID(), BARE_COLOUR);
        medColours.put(MoistureLevels.LOW_MOISTURE.getMoistureID(), TUNDRA_COLOUR);
        medColours.put(MoistureLevels.HUMID_MOISTURE.getMoistureID(), TAIGA_COLOUR);
        medColours.put(MoistureLevels.WET_MOISTURE.getMoistureID(), TAIGA_COLOUR);
        highColours.put(MoistureLevels.OCEAN_MOISTURE.getMoistureID(), OCEAN_COLOUR);
        highColours.put(MoistureLevels.LAGOON_MOISTURE.getMoistureID(), LAGOON_COLOUR);
        highColours.put(MoistureLevels.WATER_MOISTURE.getMoistureID(), WATER_COLOUR);
        highColours.put(MoistureLevels.DRY_MOISTURE.getMoistureID(), HIGH_ROCK_COLOUR);
        highColours.put(MoistureLevels.LOW_MOISTURE.getMoistureID(), TAIGA_COLOUR);
        highColours.put(MoistureLevels.HUMID_MOISTURE.getMoistureID(), SNOW_COLOUR);
        highColours.put(MoistureLevels.WET_MOISTURE.getMoistureID(), SNOW_COLOUR);
        ARCTIC_COLOUR_PALETTE.put(ElevationLevels.LOW_ELEVATION.getElevationID(), lowColours);
        ARCTIC_COLOUR_PALETTE.put(ElevationLevels.MEDIUM_ELEVATION.getElevationID(), medColours);
        ARCTIC_COLOUR_PALETTE.put(ElevationLevels.HIGH_ELEVATION.getElevationID(), highColours);
    }

    @Override
    public Map<Integer, Integer> assignTileBiomeColours(ComponentCollections collection) {
        Map<Integer, Integer> tileColours = new HashMap<>();
        collection.getAllTileIdxs()
                .forEach((tileIdx) -> tileColours.put(tileIdx,
                        ARCTIC_COLOUR_PALETTE.get(collection.getTileElevationLevel(tileIdx).getElevationID())
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
