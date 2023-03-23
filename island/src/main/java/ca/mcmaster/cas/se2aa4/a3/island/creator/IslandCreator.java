package ca.mcmaster.cas.se2aa4.a3.island.creator;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a3.island.biomes.BasicBiome;
import ca.mcmaster.cas.se2aa4.a3.island.biomes.Biome;
import ca.mcmaster.cas.se2aa4.a3.island.components.ComponentCollections;
import static ca.mcmaster.cas.se2aa4.a3.island.convertor.Convertor.*;
import ca.mcmaster.cas.se2aa4.a3.island.elevation.BasicElevation;
import ca.mcmaster.cas.se2aa4.a3.island.elevation.Elevation;
import ca.mcmaster.cas.se2aa4.a3.island.moisture.BasicMoisture;
import ca.mcmaster.cas.se2aa4.a3.island.moisture.Moisture;
import ca.mcmaster.cas.se2aa4.a3.island.noise.FlatNoise;
import ca.mcmaster.cas.se2aa4.a3.island.noise.NoiseTiler;
import ca.mcmaster.cas.se2aa4.a3.island.shaper.LagoonShaper;
import ca.mcmaster.cas.se2aa4.a3.island.shaper.ShapeFilter;

/**
 * IslandCreator
 */
public class IslandCreator {

    public static Mesh createIsland(final Mesh mesh, final int height, final int width) {
        ShapeFilter shape = new LagoonShaper(height, width);
        NoiseTiler noise = new FlatNoise();
        ComponentCollections collection = ComponentCollections.COLLECTION;
        Elevation elev = new BasicElevation();
        Moisture moist = new BasicMoisture();
        Biome biome = new BasicBiome();
        System.out.println("Converting mesh into COLLECTION");
        collection.setup(mesh);
        System.out.println("Shaping tiles");
        shape.shapeAllTiles(collection);
        System.out.println("Updating tracked sets");
        collection.updateOceans();
        collection.updateShores();
        collection.updateLagoons();
        System.out.println("Adding noise");
        noise.overwriteTileTypes(collection);
        System.out.println("Updating tracked sets");
        collection.updateOceans();
        collection.updateShores();
        collection.updateLagoons();
        // Update lakes
        // Update aquifers
        // Update elevation
        System.out.println("Assigning elevation");
        elev.elevateAllTiles(collection);
        // Update rivers
        // Update moisture
        System.out.println("Assigning moisture");
        moist.moisturizeAllTiles(collection);
        // Assign biome colours
        System.out.println("Assigning biomes");
        biome.assignBiomes(collection);
        // Make the necessary modifications to the island, then convert it with
        // convertor and return
        System.out.println("Converting back to mesh");
        return convert(collection, height, width);
    }
}
