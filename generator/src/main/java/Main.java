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

public class Main {

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
            cmd.getHelp(parser, args);
            System.exit(0);
        } // Otherwise make the required generator
        if (type.equals(GeneratorTypes.VORONOI)) {
            System.out.println("Creating Mesh Type: Voronoi");
            System.out.println("Voronoi Relaxation Level: " + relaxationLevel);
            System.out.println("Voronoi Number of Start Points: "+ numPoints);
            gen = new VoronoiGenerator(numPoints, relaxationLevel);
        } else {
            System.out.println("Creating Mesh Type: Grid");
            System.out.println("Grid Side Length: "+sideLength);
            gen = new GridGenerator(sideLength);
        }
        System.out.println("Mesh Vertex Colour: " + decorator.getVertThickness());
        System.out.println("Mesh Segment Colour: " + decorator.getSegColour());
        System.out.println("Mesh Segment Thickness: " + decorator.getSegThickness());
        System.out.println("Mesh Polygon Fill Colour: " + decorator.getPolyFillColour());
        System.out.println("Mesh Polygon Border Colour: " + decorator.getPolyBorderColour());
        System.out.println("Mesh Polygon Border Thickness: " + decorator.getPolyBorderThickness());
        System.out.println("File Name: "+ fileName);
        // Generate the mesh and convert it
        factory.write(createMesh(fMesh, gen, decorator), fileName);
        System.out.println("File " + fileName + " has been created");
    }

}
