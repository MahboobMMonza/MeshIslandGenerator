import java.io.*;

import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.*;
import ca.mcmaster.cas.se2aa4.a3.island.creator.*;
import ca.mcmaster.cas.se2aa4.a3.island.biomes.*;
import ca.mcmaster.cas.se2aa4.a3.island.shaper.*;
import ca.mcmaster.cas.se2aa4.a3.island.moisture.*;
import ca.mcmaster.cas.se2aa4.a3.island.elevation.*;
import ca.mcmaster.cas.se2aa4.a3.island.water.lake.*;
import ca.mcmaster.cas.se2aa4.a3.island.water.aquifer.*;

import static ca.mcmaster.cas.se2aa4.a3.island.preprocessor.MeshPreprocessor.*;

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
        int numOfLakes;
        int numOfAquifers;
        String input;
        String output;
        long seed;
        int height;
        int width;
        Mesh inputMesh;
        SoilTypes soilType;
        MeshFactory factory = new MeshFactory();
        cmd.addOptions();
        ModeTypes type = cmd.getModeType(parser, args);
        boolean help = cmd.hasHelpOption(parser, args);

        if (type.equals(ModeTypes.NONE)) {
            if (!help) {
                System.out.println("WARNING: A valid mode type was not provided.");
            }
            cmd.getHelp();
            System.exit(0);
        }
        input = cmd.inputCli(parser, args);
        output = cmd.outputCli(parser, args);
        seed = cmd.getSeed(parser, args);
        inputMesh = factory.read(input);
        height = getHeight(inputMesh);
        width = getWidth(inputMesh);
        soilType = cmd.getSoilType(parser, args);
        numOfLakes = cmd.getNumOfLakes(parser, args);
        numOfAquifers = cmd.getNumOfAquifers(parser, args);
        if (type.equals(ModeTypes.LAGOON)) {
            biomeFactory = new BasicBiomeFactory();
            moistureFactory = new BasicMoistureFactory();
            elevationFactory = new BasicElevationFactory();
            aquiferFactory = new BasicAquiferFactory();
            lakeFactory = new BasicLakeFactory();
            shapeFilterFactory = new LagoonShaperFactory();
            builder.biome(biomeFactory.createBiome())
                    .moisture(moistureFactory.createMoisture(soilType))
                    .elevation(elevationFactory.createElevationProfile())
                    .aquifer(aquiferFactory.createAquifer(seed, numOfAquifers))
                    .lake(lakeFactory.createLake(seed, numOfLakes))
                    .shape(shapeFilterFactory.createShaper(height, width, seed));
        } else {
            System.exit(0);
        }
        System.out.println("Begin island creation");
        System.out.println("The seed used is: " + seed);
        // Add the necessary options to the builder
        IslandCreator island = builder.build();
        factory.write(island.createIsland(inputMesh), output);
        System.out.println("The file is stored as: " + output);
    }

}
