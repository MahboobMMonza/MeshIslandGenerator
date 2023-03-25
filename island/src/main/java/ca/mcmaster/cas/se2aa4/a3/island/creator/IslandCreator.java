package ca.mcmaster.cas.se2aa4.a3.island.creator;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a3.island.biomes.*;
import ca.mcmaster.cas.se2aa4.a3.island.components.ComponentCollections;
import static ca.mcmaster.cas.se2aa4.a3.island.convertor.Convertor.*;
import ca.mcmaster.cas.se2aa4.a3.island.elevation.*;
import ca.mcmaster.cas.se2aa4.a3.island.moisture.*;
import ca.mcmaster.cas.se2aa4.a3.island.shaper.*;
import ca.mcmaster.cas.se2aa4.a3.island.water.*;

/**
 * IslandCreator
 */
public class IslandCreator {

    public static Mesh createIsland(final Mesh mesh, final int height, final int width, final long seed) {
        ShapeFilter shape = new LagoonShaper(height, width, seed);
        ComponentCollections collection = ComponentCollections.COLLECTION;
        Elevation elev = new BasicElevation();
        Moisture moist = new BasicMoisture();
        Biome biome = new BasicBiome();
        System.out.println("Converting mesh into COLLECTION");
        collection.setup(mesh);
        System.out.println("Shaping tiles");
        collection.updateTileTypes(shape.shapeAllTiles(collection));
        // Update lakes
        // Update aquifers
        // Update elevation
        System.out.println("Assigning elevation");
        collection.updateElevationLevels(elev.elevateAllTiles(collection));
        // Update rivers
        // Update moisture
        System.out.println("Assigning moisture");
        collection.updateMoistureLevels(moist.moisturizeAllTiles(collection));
        // Assign biome colours
        System.out.println("Assigning biomes");
        collection.updateTileColours(biome.assignTileBiomeColours(collection));
        // Make the necessary modifications to the island, then convert it with
        // convertor and return
        collection.ready();
        System.out.println("Converting back to mesh");
        return convert(collection, height, width);
    }
}
