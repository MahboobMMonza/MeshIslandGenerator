import static ca.mcmaster.cas.se2aa4.a2.creator.MeshCreator.createMesh;

import java.io.IOException;

import ca.mcmaster.cas.se2aa4.a2.decorator.*;
import ca.mcmaster.cas.se2aa4.a2.generator.*;
import ca.mcmaster.cas.se2aa4.a2.io.*;
import ca.mcmaster.cas.se2aa4.a2.mesh.*;
import cli.*;
import org.apache.commons.cli.*;
import org.apache.logging.log4j.*;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws IOException {
        CommandLineParser parser = new DefaultParser();
        Generator gen;
        Decorator decorator = new FixedDecorator();
        MeshFactory factory = new MeshFactory();
        CommandLine passive, run;
        GeneratorTypes type;
        ParsedReturnPair parsed;
        boolean extraHelp = false;
        int height, width, relaxationLevel, numPoints, sideLength;
        String vertColour, segColour, polyFillColour, polyBorderColour, vertThickness, segThickness,
                polyBorderThickness, fileName;
        Mesh fMesh;
        // Print help and exit if help was asked for
        try {
            passive = parser.parse(GeneratorCommandLine.passiveOpts, args, true);
            if (GeneratorCommandLine.hasHelpOption(passive)) {
                GeneratorCommandLine.getHelp();
                System.exit(0);
            }
        } catch (ParseException e) {
            logger.error("Error while attempting to parse arguments for the Generator with message: {}",
                    e.getMessage());
            GeneratorCommandLine.getHelp();
            System.exit(1);
        }
        // Otherwise get the required generator type
        try {
            run = parser.parse(GeneratorCommandLine.runOpts, args);
            type = GeneratorCommandLine.getMeshType(run);
            // Warn users if generator type is not provided and exit
            if (type.equals(GeneratorTypes.NONE)) {
                GeneratorCommandLine.getHelp();
                System.exit(1);
            }
            // Otherwise make the required generator
            parsed = GeneratorCommandLine.getDimeH(run);
            height = parsed.returnVal;
            extraHelp |= parsed.needsHelp;
            parsed = GeneratorCommandLine.getDimeW(run);
            width = parsed.returnVal;
            extraHelp |= parsed.needsHelp;
            fMesh = new FixedMesh(height, width);
            vertColour = GeneratorCommandLine.getVertColour(run);
            segColour = GeneratorCommandLine.getSegColour(run);
            polyFillColour = GeneratorCommandLine.getPolyFillColour(run);
            polyBorderColour = GeneratorCommandLine.getPolyBorderColour(run);
            vertThickness = GeneratorCommandLine.getVertThickness(run);
            segThickness = GeneratorCommandLine.getSegThickness(run);
            polyBorderThickness = GeneratorCommandLine.getPolyBorderThickness(run);
            // Set required decorator properties for any type
            decorator.setVertColour(vertColour);
            decorator.setSegColour(segColour);
            decorator.setPolyFillColour(polyFillColour);
            decorator.setPolyBorderColour(polyBorderColour);
            decorator.setVertThickness(vertThickness);
            decorator.setSegThickness(segThickness);
            decorator.setPolyBorderThickness(polyBorderThickness);
            // Get necessary variables for generators
            parsed = GeneratorCommandLine.getSideLength(run);
            sideLength = parsed.returnVal;
            extraHelp |= parsed.needsHelp;
            fileName = GeneratorCommandLine.setFileName(run);
            parsed = GeneratorCommandLine.getNumOfPoints(run);
            numPoints = parsed.returnVal;
            extraHelp |= parsed.needsHelp;
            parsed = GeneratorCommandLine.getRelaxationLevel(run);
            relaxationLevel = parsed.returnVal;
            extraHelp |= parsed.needsHelp;
            if (type.equals(GeneratorTypes.VORONOI)) {
                logger.info("Creating Mesh Type: Voronoi");
                logger.info("Interpreted Voronoi Relaxation Level: {}", relaxationLevel);
                logger.info("Interpreted Voronoi Number of Start Points: {}", numPoints);
                gen = new VoronoiGenerator(numPoints, relaxationLevel);
            } else {
                logger.info("Creating Mesh Type: Grid");
                gen = new GridGenerator(sideLength);
                logger.info("Interpreted Grid Side Length: {}", sideLength);
            }
            if (extraHelp) {
                GeneratorCommandLine.getHelp();
            }
            logger.info("Interpreted Vertex Colour: {}", decorator.getVertThickness());
            logger.info("Interpreted Segment Colour: {}", decorator.getSegColour());
            logger.info("Interpreted Segment Thickness: {}", decorator.getSegThickness());
            logger.info("Interpreted Polygon Fill Colour: {}", decorator.getPolyFillColour());
            logger.info("Interpreted Polygon Border Colour: {}", decorator.getPolyBorderColour());
            logger.info("Interpreted Polygon Border Thickness: {}", decorator.getPolyBorderThickness());
            logger.info("File Name: " + fileName);
            // Generate the mesh and convert it
            factory.write(createMesh(fMesh, gen, decorator), fileName);
            logger.info("File {} has been created.", fileName);
        } catch (ParseException e) {
            logger.error("Error while attempting to parse arguments for the Generator with message: {}",
                    e.getMessage());
            GeneratorCommandLine.getHelp();
            System.exit(1);
        }
    }

}