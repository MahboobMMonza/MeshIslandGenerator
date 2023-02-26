import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.visualizer.GraphicRenderer;
import ca.mcmaster.cas.se2aa4.a2.visualizer.MeshDump;
import ca.mcmaster.cas.se2aa4.a2.visualizer.SVGCanvas;
import cli.Debug;

import java.awt.*;
import java.io.IOException;

import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;

public class Main {

    public static void main(String[] args) throws IOException {
        Debug debugOption = new Debug();
        CommandLineParser parser = new DefaultParser();
        // Extracting command line parameters
        String input = args[0];
        String output = args[1];
        // Getting width and height for the canvas
        Structs.Mesh aMesh = new MeshFactory().read(input);
        double max_x = Double.MIN_VALUE;
        double max_y = Double.MIN_VALUE;
        for (Structs.Vertex v: aMesh.getVerticesList()) {
            max_x = (Double.compare(max_x, v.getX()) < 0? v.getX(): max_x);
            max_y = (Double.compare(max_y, v.getY()) < 0? v.getY(): max_y);
        }
        // Creating the Canvas to draw the mesh
        Graphics2D canvas = SVGCanvas.build((int) Math.ceil(max_x), (int) Math.ceil(max_y));
        GraphicRenderer renderer = new GraphicRenderer(debugOption.hasDebugOption(parser, args));
        // Painting the mesh on the canvas
        renderer.render(aMesh, canvas);
        // Storing the result in an SVG file
        SVGCanvas.write(canvas, output);
        // Dump the mesh to stdout
        MeshDump dumper = new MeshDump();
        dumper.dump(aMesh);
    }
}
