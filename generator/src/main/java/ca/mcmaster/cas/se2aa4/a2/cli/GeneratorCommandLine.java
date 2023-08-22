package ca.mcmaster.cas.se2aa4.a2.cli;

import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ca.mcmaster.cas.se2aa4.a2.generator.GeneratorTypes;

public class GeneratorCommandLine {
    static final int LINE_WIDTH = 120;

    static final String HEADER = String.format("%nGenerate a PNG image of a grid or voronoi mesh with a given length"
            + "%n%nOptions%n%n");
    static final String FOOTER = String.format("%nColour must be in RGBA hex format. E.g.: Fully opaque orange would"
            + " be entered as \"FFA500FF\", and semi-transparent orange as \"FFA50080\";"
            + " see https://rgbacolorpicker.com/rgba-to-hex for more examples and for hex conversions.");

    private static final Logger logger = LogManager.getLogger(GeneratorCommandLine.class);

    // Options that is used for the command line functionality
    private static Option sideLength = new Option("sl", "side-length", true,
            "Side Length for Grid Mesh :: MIN = 20");
    private static Option relaxationLevel = new Option("rl", "relaxation-level", true,
            "Relaxation Level for Voronoi Mesh :: MIN = 0");
    private static Option startPoints = new Option("sp", "start-points", true,
            "Number of Start Points for Voronoi Mesh :: MIN = 20");
    private static Option height = new Option("dh", "height", true,
            "Dimension for the Mesh Height :: MIN = 300");
    private static Option segmentThickness = new Option("st", "segment-thickness", true,
            "Segment Thickness :: MIN = 0.25, Max = 30");
    private static Option vertexThickness = new Option("vt", "vertex-thickness", true,
            "Vertex Thickness :: MIN = 0.25, Max = 30");
    private static Option polygonFillColour = new Option("pf", "polygon-fill", true,
            "Polygon Fill Colour (See Footer for more information");
    private static Option vertexColour = new Option("vc", "vertex-colour", true,
            "Vertex Colour (See Footer for more information)");
    private static Option segmentColour = new Option("sc", "segment-colour", true,
            "Segment Colour (See Footer for more information)");
    private static Option width = new Option("dw", "width", true,
            "Dimension for the Mesh Width :: MIN = 300");
    private static Option meshType = new Option("mt", "mesh-type", true,
            "Mesh type (Grid or Vornoi) :: THIS IS A MANDATORY OPTION");
    private static Option help = new Option("h", "help", false, "Show usage help");
    private static Option polygonBorderColour = new Option("pb", "polygon-border", true,
            "Polygon Border Colour (See Footer for more information)");
    private static Option polygonBorderThickness = new Option("bt", "border-thickness", true,
            "Polygon Border Thickness :: MIN = 0, MAX = 30");
    private static Option fileName = new Option("f", "file-name", true, "Sets File Name");
    public static final Options passiveOpts;
    public static final Options runOpts;
    private static HelpFormatter formatter = new HelpFormatter();

    static {
        // Change max width of formatter.
        formatter.setWidth(LINE_WIDTH);
        // Adds the different options to the type Options
        passiveOpts = new Options();
        runOpts = new Options();
        meshType.setRequired(true);
        passiveOpts.addOption(help);
        runOpts.addOption(help);
        runOpts.addOption(meshType);
        runOpts.addOption(width);
        runOpts.addOption(height);
        runOpts.addOption(segmentThickness);
        runOpts.addOption(segmentColour);
        runOpts.addOption(vertexColour);
        runOpts.addOption(vertexThickness);
        runOpts.addOption(startPoints);
        runOpts.addOption(relaxationLevel);
        runOpts.addOption(sideLength);
        runOpts.addOption(polygonBorderColour);
        runOpts.addOption(polygonFillColour);
        runOpts.addOption(fileName);
        runOpts.addOption(polygonBorderThickness);
    }

    /**
     * Prints the Help information
     */
    public static void getHelp() {
        formatter.printHelp("java -jar generator.jar", HEADER, runOpts, FOOTER, true);
    }

    /**
     * Determines if the user wants the Help command
     *
     * @param cmd Parsed command line args
     * @return Descision as a boolean
     */
    public static boolean hasHelpOption(CommandLine cmd) {
        return cmd.hasOption(help);
    }

    /**
     * Gets the Mesh Type from user and is associated to the enum value
     *
     * @param cmd Parsed command line args
     * @return Mesh Type as a GeneratorTypes type
     */
    public static GeneratorTypes getMeshType(CommandLine cmd) {
        GeneratorTypes type = GeneratorTypes.NONE;
        try {
            String meshtype = cmd.getOptionValue(meshType).toUpperCase();
            type = GeneratorTypes.valueOf(meshtype);
        } catch (IllegalArgumentException | NullPointerException e) {
            logger.warn("A valid mesh type was not provided. No mesh will be created.");
            type = GeneratorTypes.NONE;
        }
        return type;
    }

    /**
     * Gets the Vertex Thickness from user
     *
     * @param cmd Parsed command line args
     * @return Vertex Thickness as a String
     */
    public static String getVertThickness(CommandLine cmd) {
        String vertexThicknessValue = new String();
        if (cmd.hasOption(vertexThickness)) {
            vertexThicknessValue = cmd.getOptionValue(vertexThickness);
        }
        return vertexThicknessValue;
    }

    /**
     * Gets the Segment Thickness from user
     *
     * @param cmd Parsed command line args
     * @return Segment Thickness as a String
     */
    public static String getSegThickness(CommandLine cmd) {
        String segmentThicknessValue = new String();
        if (cmd.hasOption(segmentThickness)) {
            segmentThicknessValue = cmd.getOptionValue(segmentThickness);
        }
        return segmentThicknessValue;
    }

    /**
     * Gets the Height dimension from the user
     *
     * @param cmd Parsed command line args
     * @return Height as an integer
     */
    public static ParsedReturnPair getDimeH(CommandLine cmd) {
        int dimh = 0;
        boolean needsHelp = false;
        try {
            if (cmd.hasOption(height)) {
                dimh = Integer.parseInt(cmd.getOptionValue(height));
            }
        } catch (NumberFormatException e) {
            logger.warn("An invalid argument value was given for the option '{}', and will be interpreted as 0 and"
                    + " later processed to minimum mesh bounds.", height.getLongOpt());
        }
        return new ParsedReturnPair(dimh, needsHelp);
    }

    /**
     * Gets the Width dimension from the user
     *
     * @param cmd Parsed command line args
     * @return Width as an integer
     */
    public static ParsedReturnPair getDimeW(CommandLine cmd) {
        int dimw = 0;
        boolean needsHelp = false;
        try {
            if (cmd.hasOption(width)) {
                dimw = Integer.parseInt(cmd.getOptionValue(width));
            }
        } catch (NumberFormatException e) {
            logger.warn("An invalid argument value was given for the option '{}', and will be interpreted as 0 and"
                    + " later processed to minimum mesh bounds.", width.getLongOpt());
            getHelp();
        }
        return new ParsedReturnPair(dimw, needsHelp);
    }

    /**
     * Gets the Side Length of the Grid from the user
     *
     * @param cmd Parsed command line args
     * @return Side Length as an integer
     */
    public static ParsedReturnPair getSideLength(CommandLine cmd) {
        int sl = 0;
        boolean needsHelp = false;
        try {
            if (cmd.hasOption(sideLength)) {
                sl = Integer.parseInt(cmd.getOptionValue(sideLength));
            }
        } catch (NumberFormatException e) {
            logger.warn("An invalid argument value was given for the option '{}', and will be interpreted as 0 and"
                    + " later processed to minimum mesh bounds.", sideLength.getLongOpt());
            getHelp();
        }
        return new ParsedReturnPair(sl, needsHelp);
    }

    /**
     * Gets the Voronoi Relaxation Level from the user
     *
     * @param cmd Parsed command line args
     * @return Relaxation Level as an integer
     */
    public static ParsedReturnPair getRelaxationLevel(CommandLine cmd) {
        int relaxation = 0;
        boolean needsHelp = false;
        try {
            if (cmd.hasOption(relaxationLevel)) {
                relaxation = Integer.parseInt(cmd.getOptionValue(relaxationLevel));
            }
        } catch (NumberFormatException e) {
            logger.warn("An invalid argument value was given for the option '{}', and will be interpreted as 0 and"
                    + " later processed to minimum mesh bounds.", relaxationLevel.getLongOpt());
            needsHelp = true;
        }
        return new ParsedReturnPair(relaxation, needsHelp);
    }

    /**
     * Gets the Voronoi number of Start Points from the user
     *
     * @param cmd Parsed command line args
     * @return Number of Start Points as an integer
     */
    public static ParsedReturnPair getNumOfPoints(CommandLine cmd) {
        int numOfPoints = 0;
        boolean needsHelp = false;
        try {
            if (cmd.hasOption(startPoints)) {
                numOfPoints = Integer.parseInt(cmd.getOptionValue(startPoints));
            }
        } catch (NumberFormatException e) {
            logger.warn("An invalid argument value was given for the option '{}', and will be interpreted as 0 and"
                    + " later processed to minimum mesh bounds.", startPoints.getLongOpt());
            needsHelp = true;
        }
        return new ParsedReturnPair(numOfPoints, needsHelp);
    }

    /**
     * Gets the Segment Colour from the user
     *
     * @param cmd Parsed command line args
     * @return Segment Colour as a String
     */
    public static String getSegColour(CommandLine cmd) {
        String segColour = new String();
        if (cmd.hasOption(segmentColour)) {
            segColour = cmd.getOptionValue(segmentColour);
        }
        return segColour;
    }

    /**
     * Gets the Vertex Colour from the user
     *
     * @param cmd Parsed command line args
     * @return Vertex Colour as a String
     */
    public static String getVertColour(CommandLine cmd) {
        String vertColour = new String();
        if (cmd.hasOption(vertexColour)) {
            vertColour = cmd.getOptionValue(vertexColour);
        }
        return vertColour;
    }

    /**
     * Gets the Polygon Fill Colour from the user
     *
     * @param cmd Parsed command line args
     * @return Polygon Fill Colour as a String
     */
    public static String getPolyFillColour(CommandLine cmd) {
        String pfc = new String();
        if (cmd.hasOption(polygonFillColour)) {
            pfc = cmd.getOptionValue(polygonFillColour);
        }
        return pfc;
    }

    /**
     * Gets the Polygon Border Colour from the user
     *
     * @param cmd Parsed command line args
     * @return Polygon Border Colour as a String
     */
    public static String getPolyBorderColour(CommandLine cmd) {
        String pbc = new String();
        if (cmd.hasOption(polygonBorderColour)) {
            pbc = cmd.getOptionValue(polygonBorderColour);
        }
        return pbc;
    }

    /**
     * Gets the Polygon Border Thickness from the user
     *
     * @param cmd Parsed command line args
     * @return Polygon Border Thickness as a String
     */
    public static String getPolyBorderThickness(CommandLine cmd) {
        String pbt = new String();
        if (cmd.hasOption(polygonBorderThickness)) {
            pbt = cmd.getOptionValue(polygonBorderThickness);
        }
        return pbt;
    }

    /**
     * Gets the File Name from the user
     *
     * @param cmd Parsed command line args
     * @return File Name as a String
     */
    public static String setFileName(CommandLine cmd) {
        String filename = "sample.mesh";
        if (cmd.hasOption(fileName)) {
            filename = cmd.getOptionValue(fileName);
        } else {
            logger.warn("No file name was given. The default file name 'sample.mesh' will be used and the file will be"
                    + " created in the default directory.");
        }
        return filename;
    }

}