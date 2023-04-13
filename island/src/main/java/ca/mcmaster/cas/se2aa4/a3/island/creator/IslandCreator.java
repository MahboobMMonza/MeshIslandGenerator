package ca.mcmaster.cas.se2aa4.a3.island.creator;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a3.island.biomes.*;
import ca.mcmaster.cas.se2aa4.a3.island.city.CityPopulator;
import ca.mcmaster.cas.se2aa4.a3.island.components.ComponentCollections;
import static ca.mcmaster.cas.se2aa4.a3.island.convertor.Convertor.*;
import ca.mcmaster.cas.se2aa4.a3.island.elevation.*;
import ca.mcmaster.cas.se2aa4.a3.island.moisture.*;
import ca.mcmaster.cas.se2aa4.a3.island.shaper.*;
import ca.mcmaster.cas.se2aa4.a3.island.water.aquifer.*;
import ca.mcmaster.cas.se2aa4.a3.island.water.lake.*;
import ca.mcmaster.cas.se2aa4.a3.island.water.river.*;

/**
 * IslandCreator
 */
public class IslandCreator {

    private Lake lake;
    private Aquifer aquifer;
    private Elevation elevation;
    private Moisture moisture;
    private Biome biome;
    private Shaper shape;
    private int height;
    private int width;
    private River river;
    private CityPopulator populator;

    IslandCreator(Lake lake, Aquifer aquifer, Elevation elevation, Moisture moisture, Biome biome, Shaper shape, River river,
            CityPopulator populator, int height, int width) {
        this.lake = lake;
        this.aquifer = aquifer;
        this.elevation = elevation;
        this.moisture = moisture;
        this.biome = biome;
        this.shape = shape;
        this.river = river;
        this.populator = populator;
        this.height = height;
        this.width = width;
    }

    public Mesh createIsland(final Mesh mesh) {
        ComponentCollections collection = new ComponentCollections();
        System.out.println("Converting mesh into COLLECTION");
        collection.setup(mesh);
        System.out.println("Shaping tiles");
        collection.updateTileTypes(shape.shapeAllTiles(collection));
        // Update lakes
        collection.updateLakes(lake.assignLakeTiles(collection));
        // Update aquifers
        collection.updateAquifers(aquifer.assignAquiferTiles(collection));
        // Update elevation
        System.out.println("Assigning elevation");
        collection.updateElevationLevels(elevation.elevateAllTiles(collection));
        // Update rivers
        collection.updateRivers(river.assignRiverTiles(collection));
        // Update moisture
        System.out.println("Assigning moisture");
        collection.updateMoistureLevels(moisture.moisturizeAllTiles(collection));
        // Assign biome colours
        System.out.println("Assigning biomes");
        collection.updateTileColours(biome.assignTileBiomeColours(collection));
        collection.updateEdgeColours(biome.assignEdgeBiomeColours(collection));
        System.out.println("Configuring cities and their paths.");
        populator.findPaths(collection);
        collection.updateCityTiles(populator.getCityCentroids());
        collection.updateRoads(populator.getCentroidPairs());
        // Make the necessary modifications to the island, then convert it with
        // convertor and return
        collection.setReady(true);
        System.out.println("Converting back to mesh");
        return convert(collection, height, width);
    }
}
