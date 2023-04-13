import static ca.mcmaster.cas.se2aa4.a3.island.preprocessor.MeshPreprocessor.*;

import java.io.*;
import java.util.Random;

import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.*;
import ca.mcmaster.cas.se2aa4.a3.island.creator.*;
import ca.mcmaster.cas.se2aa4.a3.island.biomes.*;
import ca.mcmaster.cas.se2aa4.a3.island.city.CityPopulator;
import ca.mcmaster.cas.se2aa4.a3.island.shaper.*;
import ca.mcmaster.cas.se2aa4.a3.island.moisture.*;
import ca.mcmaster.cas.se2aa4.a3.island.elevation.*;
import ca.mcmaster.cas.se2aa4.a3.island.water.lake.*;
import ca.mcmaster.cas.se2aa4.a3.island.water.river.River;
import ca.mcmaster.cas.se2aa4.a3.island.water.aquifer.*;

import cli.IslandCommandLine;
import cli.ModeTypes;

import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;

// import org.apache.logging.log4j.Logger;
// import org.apache.logging.log4j.LogManager;

public class Main {
    // private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws IOException {
        CommandLineParser parser = new DefaultParser();
        IslandCommandLine cmd = new IslandCommandLine();
        IslandCreatorBuilder builder = new IslandCreatorBuilder();
        AbstractBiomeFactory biomeFactory;
        AbstractAquiferFactory aquiferFactory;
        AbstractLakeFactory lakeFactory;
        AbstractElevationFactory elevationFactory;
        AbstractMoistureFactory moistureFactory;
        AbstractShaperFactory shapeFilterFactory;
        ModeTypes mode;
        int numOfRivers;
        int numOfLakes;
        int numOfAquifers;
        int numOfCities;
        String input;
        String output;
        long seed;
        int height;
        int width;
        Mesh inputMesh;
        SoilTypes soilType;
        MeshFactory factory = new MeshFactory();
        cmd.addOptions();
        biomeFactory = cmd.getBiomeType(parser, args);
        boolean help = cmd.hasHelpOption(parser, args);
        mode = cmd.getModeType(parser, args);
        if (mode == ModeTypes.NONE) {
            if (!help) {
                System.out.println("WARNING: A valid mode type was not provided.");
            }
            cmd.getHelp();
            System.exit(0);
        }
        input = cmd.inputCli(parser, args);
        output = cmd.outputCli(parser, args);
        seed = cmd.getSeed(parser, args);
        if (seed == 0) {
            if (mode != ModeTypes.LAGOON)
                System.out.println("WARNING: A valid seed was not provided. A random seed will be used.");
            seed = new Random().nextLong(0, Long.MAX_VALUE);
        }
        inputMesh = factory.read(input);
        height = getHeight(inputMesh);
        width = getWidth(inputMesh);
        soilType = cmd.getSoilType(parser, args);
        numOfLakes = cmd.getNumOfLakes(parser, args);
        numOfAquifers = cmd.getNumOfAquifers(parser, args);
        numOfCities = cmd.getNumOfCities(parser, args);
        shapeFilterFactory = cmd.getShapeType(parser, args);
        moistureFactory = cmd.getMoistureType(parser, args);
        numOfRivers = cmd.getNumOfRivers(parser, args);
        elevationFactory = cmd.getElevationType(parser, args);
        aquiferFactory = cmd.getAquiferType(parser, args);
        lakeFactory = cmd.getLakeType(parser, args);
        if (mode == ModeTypes.LAGOON) {
            shapeFilterFactory = new LagoonShaperFactory();
            moistureFactory = new BasicMoistureFactory();
            biomeFactory = new BasicBiomeFactory();
            elevationFactory = new BasicElevationFactory();
            numOfRivers = numOfAquifers = numOfLakes = 0;
        }
        builder.biome(biomeFactory.createBiome())
                .moisture(moistureFactory.createMoisture(soilType))
                .elevation(elevationFactory.createElevationProfile())
                .aquifer(aquiferFactory.createAquifer(seed, numOfAquifers))
                .lake(lakeFactory.createLake(seed, numOfLakes))
                .shape(shapeFilterFactory.createShaper(height, width, seed))
                .height(height)
                .width(width)
                .cities(new CityPopulator(numOfCities, seed))
                .river(new River(seed, numOfRivers));
        System.out.println(":: Begin island creation ::");
        System.out.println("The seed used is: " + seed);
        System.out.println("The maximum number of lakes is: " + numOfLakes);
        System.out.println("The maximum number of aquifers is: " + numOfAquifers);
        System.out.println("The maximum number of rivers is: " + numOfRivers);
        System.out.println("The maximum number of cities is: " + numOfCities);
        System.out.println("The input file is: " + input);
        // Add the necessary options to the builder
        IslandCreator island = builder.build();
        factory.write(island.createIsland(inputMesh), output);
        System.out.println("The file is stored as: " + output);
    }

}
