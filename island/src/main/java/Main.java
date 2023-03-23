import java.io.*;

// import cli.GeneratorCommandLine;
import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.*;
import static ca.mcmaster.cas.se2aa4.a3.island.creator.IslandCreator.*;
import static ca.mcmaster.cas.se2aa4.a3.island.preprocessor.MeshPreprocessor.*;

import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;

// import org.apache.logging.log4j.Logger;
// import org.apache.logging.log4j.LogManager;

public class Main {
    // private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws IOException {
        // CommandLineParser parser = new DefaultParser();
        // GeneratorCommandLine cmd = new GeneratorCommandLine();
        // cmd.addOptions();
        if (args.length != 2) {
            args = new String[] { "input.mesh", "output.mesh" };
        }
        MeshFactory factory = new MeshFactory();
        // if (cmd.hasHelpOption(parser, args) || type.equals(GeneratorTypes.NONE)) {
        // cmd.getHelp();
        // System.exit(0);
        // } // Otherwise make the required generator
        // Generate the mesh and convert it
        Mesh inputMesh = factory.read(args[0]);
        // for the time being
        // Find the max height and width of the mesh
        int height = getHeight(inputMesh), width = getWidth(inputMesh);
        System.out.println("Begin island creation");
        factory.write(createIsland(inputMesh, height, width), args[1]);
        System.out.println("The file is stored as: " + args[1]);
    }

}
