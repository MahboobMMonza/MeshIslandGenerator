package cli;

import org.apache.commons.cli.*;

import ca.mcmaster.cas.se2aa4.a2.generator.GeneratorTypes;

public class GeneratorCommandLine {

    // Options that is used for the command line functionality
    private static Option sideLength = new Option("sl", "sidelength", true,
            "Side Lengths :: MIN = 20");
    private static Option relaxationLevel = new Option("rl", "relaxationlevel", true,
            "Relaxation Level :: MIN = 0");
    private static Option startpoints = new Option("sp", "startpoints", true,
            "Number of Start Points :: MIN = 20");
    private static Option dimensionh = new Option("dh", "dimensionh", true,
            "Dimension for the height :: MIN = 300");
    private static Option segmentThickness = new Option("st", "segmentthickness", true,
            "Segment Thickness :: MIN = 0.25, Max = 30 )");
    private static Option vertexThickness = new Option("vt", "vertexthickness", true,
            "Vertex Thickness :: MIN = 0.25, Max = 30");
    private static Option polygonFillColour = new Option("pf", "polygonfill", true,
            "Polygon Fill Colour (See Footer for more information");
    private static Option vertexColour = new Option("vc", "vertexcolour", true,
            "Vertex Colour (See Footer for more information)");
    private static Option segmentColour = new Option("sc", "segmentcolour", true,
            "Segment Colour (See Footer for more information)");
    private static Option dimensionw = new Option("dw", "dimensionw", true, "Dimension for the width :: MIN = 300");
    private static Option meshType = new Option("mt", "meshtype", true,
            "Mesh type (Grid or Vornoi) ::This is a mandatory option");
    private static Option help = new Option("h", "help", false, "Show usage help");
    private static Option polygonBorderColour = new Option("pb", "polygonborder", true,
            "Polygon Border Colour (See Footer for more information)");
    private static Option polygonBorderThickness = new Option("bt", "borderthickness", true,
            "Polygon Border Thickness :: MIN =0, MAX = 30");
    private static Option fileName = new Option("f", "filename", true, "Sets File Name");
    private static Options options = new Options();
    private static HelpFormatter formatter = new HelpFormatter();

    // Adds the different options to the type Options
    public void addOptions() {
        options.addOption(dimensionw);
        options.addOption(dimensionh);
        options.addOption(segmentThickness);
        options.addOption(segmentColour);
        options.addOption(vertexColour);
        options.addOption(vertexThickness);
        options.addOption(meshType);
        options.addOption(startpoints);
        options.addOption(relaxationLevel);
        options.addOption(sideLength);
        options.addOption(polygonBorderColour);
        options.addOption(polygonFillColour);
        options.addOption(help);
        options.addOption(fileName);
        options.addOption(polygonBorderThickness);
    }

    /**
     * Prints the Help information
     */
    public void getHelp() {
        formatter.printHelp("java -jar generator.jar -mt arg0 [option 1] arg1 [option 2] arg2 ...", "Options", options,
                "Colour must be in RGBA hex format. E.g.: Fully opaque orange would be entered as \"FFA500FF\", and semi-transparent orange as \"FFA50080\"; see https://rgbacolorpicker.com/rgba-to-hex for more examples and for hex conversions");

    }

    /**
     * Determines if the user wants the Help command
     *
     * @param parser Parser that scans the command line
     * @param args   The input arguments that the parser will scan
     * @return Descision as a boolean
     */
    public boolean hasHelpOption(CommandLineParser parser, String[] args) {
        boolean hasHelp = false;
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption(help)) {
                hasHelp = true;
            }
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            getHelp();
        }
        return hasHelp;
    }

    /**
     * Gets the Mesh Type from user and is associated to the enum value
     *
     * @param parser Parser that scans the command line
     * @param args   The input arguments that the parser will scan
     * @return Mesh Type as a GeneratorTypes type
     */
    public GeneratorTypes getMeshType(CommandLineParser parser, String[] args) {
        GeneratorTypes type = GeneratorTypes.NONE;
        try {
            CommandLine cmd = parser.parse(options, args);
            String meshtype = cmd.getOptionValue(meshType).toUpperCase();
            type = GeneratorTypes.valueOf(meshtype);
        } catch (ParseException | IllegalArgumentException | NullPointerException e) {
            System.out.println("WARNING: A valid mesh type was not provided.");
            type = GeneratorTypes.NONE;
        }
        return type;
    }

    /**
     * Gets the Vertex Thickness from user
     *
     * @param parser Parser that scans the command line
     * @param args   The input arguments that the parser will scan
     * @return Vertex Thickness as a String
     */
    public String getVertThickness(CommandLineParser parser, String[] args) {
        String vertexThicknessValue = new String();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption(vertexThickness)) {
                vertexThicknessValue = cmd.getOptionValue(vertexThickness);
            }
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            getHelp();
        }
        return vertexThicknessValue;
    }

    /**
     * Gets the Segment Thickness from user
     *
     * @param parser Parser that scans the command line
     * @param args   The input arguments that the parser will scan
     * @return Segment Thickness as a String
     */
    public String getSegThickness(CommandLineParser parser, String[] args) {
        String segmentthicknessvalue = new String();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption(segmentThickness)) {
                segmentthicknessvalue = cmd.getOptionValue(segmentThickness);
            }
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            getHelp();
        }
        return segmentthicknessvalue;
    }

    /**
     * Gets the Height dimension from the user
     *
     * @param parser Parser that scans the command line
     * @param args   The input arguments that the parser will scan
     * @return Height as an integer
     */
    public int getDimeH(CommandLineParser parser, String[] args) {
        int dimh = 0;
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption(dimensionh)) {
                dimh = Integer.parseInt(cmd.getOptionValue(dimensionh));

            }
        } catch (ParseException | NumberFormatException e) {
            System.err.println(e.getMessage());
            getHelp();
        }
        return dimh;
    }

    /**
     * Gets the Width dimension from the user
     *
     * @param parser Parser that scans the command line
     * @param args   The input arguments that the parser will scan
     * @return Width as an integer
     */
    public int getDimeW(CommandLineParser parser, String[] args) {
        int dimw = 0;
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption(dimensionw)) {
                dimw = Integer.parseInt(cmd.getOptionValue(dimensionw));
            }
        } catch (ParseException | NumberFormatException e) {
            System.err.println(e.getMessage());
            getHelp();
        }
        return dimw;
    }

    /**
     * Gets the Side Length of the Grid from the user
     *
     * @param parser Parser that scans the command line
     * @param args   The input arguments that the parser will scan
     * @return Side Length as an integer
     */
    public int getSideLength(CommandLineParser parser, String[] args) {
        int sidelength = 0;
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption(sideLength)) {
                sidelength = Integer.parseInt(cmd.getOptionValue(sideLength));
            }
        } catch (ParseException | NumberFormatException e) {
            System.err.println(e.getMessage());
            getHelp();
        }
        return sidelength;
    }

    /**
     * Gets the Voronoi Relaxation Level from the user
     *
     * @param parser Parser that scans the command line
     * @param args   The input arguments that the parser will scan
     * @return Relaxation Level as an integer
     */
    public int getRelaxationLevel(CommandLineParser parser, String[] args) {
        int relaxationlevel = 0;
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption(relaxationLevel)) {
                relaxationlevel = Integer.parseInt(cmd.getOptionValue(relaxationLevel));
            }
        } catch (ParseException | NumberFormatException e) {
            System.err.println(e.getMessage());
            getHelp();
        }
        return relaxationlevel;
    }

    /**
     * Gets the Voronoi number of Start Points from the user
     *
     * @param parser Parser that scans the command line
     * @param args   The input arguments that the parser will scan
     * @return Number of Start Points as an integer
     */
    public int getNumOfPoints(CommandLineParser parser, String[] args) {
        int numOfPoints = 0;
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption(startpoints)) {
                numOfPoints = Integer.parseInt(cmd.getOptionValue(startpoints));
            }
        } catch (ParseException | NumberFormatException e) {
            System.err.println(e.getMessage());
            getHelp();
        }
        return numOfPoints;
    }

    /**
     * Gets the Segment Colour from the user
     *
     * @param parser Parser that scans the command line
     * @param args   The input arguments that the parser will scan
     * @return Segment Colour as a String
     */
    public String getSegColour(CommandLineParser parser, String[] args) {
        String segColour = new String();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption(segmentColour)) {
                segColour = cmd.getOptionValue(segmentColour);
            }
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            getHelp();
        }
        return segColour;
    }

    /**
     * Gets the Vertex Colour from the user
     *
     * @param parser Parser that scans the command line
     * @param args   The input arguments that the parser will scan
     * @return Vertex Colour as a String
     */
    public String getVertColour(CommandLineParser parser, String[] args) {
        String vertColour = new String();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption(vertexColour)) {
                vertColour = cmd.getOptionValue(vertexColour);
            }
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            getHelp();
        }
        return vertColour;
    }

    /**
     * Gets the Polygon Fill Colour from the user
     *
     * @param parser Parser that scans the command line
     * @param args   The input arguments that the parser will scan
     * @return Polygon Fill Colour as a String
     */
    public String getPolyFillColour(CommandLineParser parser, String[] args) {
        String pfc = new String();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption(polygonFillColour)) {
                pfc = cmd.getOptionValue(polygonFillColour);
            }
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            getHelp();
        }
        return pfc;
    }

    /**
     * Gets the Polygon Border Colour from the user
     *
     * @param parser Parser that scans the command line
     * @param args   The input arguments that the parser will scan
     * @return Polygon Border Colour as a String
     */
    public String getPolyBorderColour(CommandLineParser parser, String[] args) {
        String pbc = new String();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption(polygonBorderColour)) {
                pbc = cmd.getOptionValue(polygonBorderColour);
            }
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            getHelp();
        }
        return pbc;
    }

    /**
     * Gets the Polygon Border Thickness from the user
     *
     * @param parser Parser that scans the command line
     * @param args   The input arguments that the parser will scan
     * @return Polygon Border Thickness as a String
     */
    public String getPolyBorderThickness(CommandLineParser parser, String[] args) {
        String pbt = new String();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption(polygonBorderThickness)) {
                pbt = cmd.getOptionValue(polygonBorderThickness);
            }
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            getHelp();
        }
        return pbt;
    }

    /**
     * Gets the File Name from the user
     *
     * @param parser Parser that scans the command line
     * @param args   The input arguments that the parser will scan
     * @return File Name as a String
     */
    public String setFileName(CommandLineParser parser, String[] args) {
        String filename = "sample.mesh";
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption(fileName)) {
                filename = cmd.getOptionValue(fileName);
            }
        } catch (ParseException e) {
            System.out.println(
                    "WARNING: No File name was given. The default file name 'sample.mesh' will be used and the file will be created in the default directory.");
            filename = "sample.mesh";
        }
        return filename;
    }

}
