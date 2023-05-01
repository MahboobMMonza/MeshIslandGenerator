import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.visualizer.renderer.*;
import ca.mcmaster.cas.se2aa4.a2.visualizer.MeshDump;
import ca.mcmaster.cas.se2aa4.a2.visualizer.SVGCanvas;
import cli.VisualizerCommandLine;

import java.awt.*;
import java.io.IOException;

import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws IOException {
        try {
            CommandLineParser parser = new DefaultParser();
            VisualizerCommandLine cmd = new VisualizerCommandLine();
            cmd.addOptions();
            if (cmd.hasHelpOption(parser, args)) {
                cmd.getHelp();
                System.exit(0);
            } else {
                String input = cmd.inputCli(parser, args);
                String output = cmd.outputCli(parser, args);
                logger.info("Input File: " + input);
                logger.info("Output File: " + output);
                // Getting width and width for the canvas
                Structs.Mesh aMesh = new MeshFactory().read(input);
                int height = 0, width = 0;
                for (Property p : aMesh.getPropertiesList()) {
                    if (p.getKey().equals("height")) {
                        height = Integer.parseInt(p.getValue());
                    }
                    if (p.getKey().equals("width")) {
                        width = Integer.parseInt(p.getValue());
                    }
                }

                if (height <= 0 || width <= 0) {
                    logger.warn("WARNING: The height or width values were not set to dimension values");
                    for (Structs.Vertex vertex : aMesh.getVerticesList()) {
                        height = (int) Math.ceil(Math.max(height, vertex.getY()));
                        width = (int) Math.ceil(Math.max(height, vertex.getX()));
                    }
                }
                // Creating the Canvas to draw the mesh
                Graphics2D canvas = SVGCanvas.build(width, height);
                Renderer renderer = RendererFactory.createRenderer(cmd.hasDebugOption(parser, args));
                // Painting the mesh on the canvas
                renderer.render(aMesh, canvas);
                // Storing the result in an SVG file
                SVGCanvas.write(canvas, output);
                // Dump the mesh to stdout
                MeshDump dumper = new MeshDump();
                dumper.dump(aMesh);
                logger.info("File " + output + " has been created");
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }
}
