import java.io.*;

import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.*;
import static ca.mcmaster.cas.se2aa4.a3.island.creator.IslandCreator.*;
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
        cmd.addOptions();
        MeshFactory factory = new MeshFactory();
        ModeTypes type = cmd.getModeType(parser, args);
        if (cmd.hasHelpOption(parser, args) || type.equals(ModeTypes.NONE)) {
            cmd.getHelp();
            System.exit(0);
        }
        String input = cmd.inputCli(parser, args);
        String output = cmd.outputCli(parser, args);
        Mesh inputMesh = factory.read(input);
        int height = getHeight(inputMesh), width = getWidth(inputMesh);
        System.out.println("Begin island creation");
        factory.write(createIsland(inputMesh, height, width), output);
        System.out.println("The file is stored as: " + output);
    }

}
