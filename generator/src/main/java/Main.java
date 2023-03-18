import static ca.mcmaster.cas.se2aa4.a2.creator.MeshCreator.createMesh;

import java.io.IOException;

import ca.mcmaster.cas.se2aa4.a2.decorator.Decorator;
import ca.mcmaster.cas.se2aa4.a2.decorator.FixedDecorator;
import ca.mcmaster.cas.se2aa4.a2.generator.Generator;
import ca.mcmaster.cas.se2aa4.a2.generator.GeneratorTypes;
import ca.mcmaster.cas.se2aa4.a2.generator.GridGenerator;
import ca.mcmaster.cas.se2aa4.a2.generator.VoronoiGenerator;
import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.mesh.FixedMesh;
import ca.mcmaster.cas.se2aa4.a2.mesh.Mesh;
import cli.GeneratorCommandLine;

import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    

    public static void main(String[] args) throws IOException {
        CommandLineParser parser = new DefaultParser();
        GeneratorCommandLine cmd = new GeneratorCommandLine();
        Generator gen = null;
        cmd.addOptions();
        Decorator decorator = new FixedDecorator();
        MeshFactory factory = new MeshFactory();
        int height = cmd.getDimeH(parser, args), width = cmd.getDimeW(parser, args);
        Mesh fMesh = new FixedMesh(height, width);
        String vertColour = cmd.getVertColour(parser, args);
        String segColour = cmd.getSegColour(parser, args);
        String polyFillColour = cmd.getPolyFillColour(parser, args);
        String polyBorderColour = cmd.getPolyBorderColour(parser, args);
        String vertThickness = cmd.getVertThickness(parser, args);
        String segThickness = cmd.getSegThickness(parser, args);
        String polyBorderThickness = cmd.getPolyBorderThickness(parser, args);
        // Set required decorator properties for any type
        decorator.setVertColour(vertColour);
        decorator.setSegColour(segColour);
        decorator.setPolyFillColour(polyFillColour);
        decorator.setPolyBorderColour(polyBorderColour);
        decorator.setVertThickness(vertThickness);
        decorator.setSegThickness(segThickness);
        decorator.setPolyBorderThickness(polyBorderThickness);
        // Get necessary variables for generators
        int numPoints = cmd.getNumOfPoints(parser, args);
        int relaxationLevel = cmd.getRelaxationLevel(parser, args);
        int sideLength = cmd.getSideLength(parser, args);
        String fileName = cmd.setFileName(parser, args);
        GeneratorTypes type = cmd.getMeshType(parser, args);
        // Determine help option or if mesh type was not given or not valid then show
        // help and exit
        if (cmd.hasHelpOption(parser, args) || type.equals(GeneratorTypes.NONE)) {
            cmd.getHelp();
            System.exit(0);
        } // Otherwise make the required generator
        if (type.equals(GeneratorTypes.VORONOI)) {
            logger.info("Creating Mesh Type: Voronoi");
            logger.info("Voronoi Relaxation Level: " + relaxationLevel);
            logger.info("Voronoi Number of Start Points: "+ numPoints);
            gen = new VoronoiGenerator(numPoints, relaxationLevel);
        } else {
            logger.info("Creating Mesh Type: Grid");
            logger.info("Grid Side Length: "+sideLength);
            gen = new GridGenerator(sideLength);
        }
        logger.info("Mesh Vertex Colour: " + decorator.getVertThickness());
        logger.info("Mesh Segment Colour: " + decorator.getSegColour());
        logger.info("Mesh Segment Thickness: " + decorator.getSegThickness());
        logger.info("Mesh Polygon Fill Colour: " + decorator.getPolyFillColour());
        logger.info("Mesh Polygon Border Colour: " + decorator.getPolyBorderColour());
        logger.info("Mesh Polygon Border Thickness: " + decorator.getPolyBorderThickness());
        logger.info("File Name: "+ fileName);
        // Generate the mesh and convert it
        factory.write(createMesh(fMesh, gen, decorator), fileName);
        logger.info("File " + fileName + " has been created");
    }

}
