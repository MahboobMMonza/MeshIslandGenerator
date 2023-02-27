package cli;

import org.apache.commons.cli.*;

import ca.mcmaster.cas.se2aa4.a2.generator.GeneratorTypes;

public class GeneratorCommandLine {

    //Options that is used for the command line functionality
    private static Option sideLength = new Option("sl", "sidelength", true,
            "Side Lengths (default: 20)");
    private static Option relaxationLevel = new Option("rl", "relaxationlevel", true,
            "Relaxation Level (default: 20)");
    private static Option startpoints = new Option("sp", "startpoints", true,
            "Number of Start Points (default: 50)");
    private static Option dimensionh = new Option("dh", "dimensionh", true,
            "Dimension for the height (default: 300)");
    private static Option segmentThickness = new Option("st", "segmentthickness", true,
            "Segment Thickness");
    private static Option vertexThickness = new Option("vt", "vertexthickness", true,
            "Vertex Thickness");
    private static Option polygonFillColour = new Option("pf", "polygonfill", true,
            "Polygon Fill Colour (See Footer for more information");
    private static Option vertexColour = new Option("vc", "vertexcolour", true,
            "Vertex Colour (See Footer for more information)");
    private static Option segmentColour = new Option("sc", "segmentcolour", true,
            "Segment Colour (See Footer for more information)");
    private static Option dimensionw = new Option("dw", "dimensionw", true, "Dimension for the width (default: 300)");
    private static Option meshType = new Option("mt", "meshtype", true, "Mesh type (Grid or Vornoi)");
    private static Option help = new Option("h", "help", false, "Show usage help");
    private static Option polygonBorderColour = new Option("pb", "polygonborder", true,
            "Polygon Border Colour (See Footer for more information)");
    private static Option polygonBorderThickness = new Option("bt", "borderthickness", true,
            "Polygon Border Thickness");
    private static Option fileName = new Option("f", "filename", true, "Sets File Name");
    private static Options options = new Options();
    private static HelpFormatter formatter = new HelpFormatter();

    //Adds the different options to the type Options
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

    //A method that provides the user the help instructions regarding the command line options
    public void getHelp(CommandLineParser parser, String[] args) {
        formatter.printHelp("java -jar generator.jar [option 1] arg1 [option 2] arg2 ...", "Help", options,
        "Colour must be in RGBA hex format. E.g.: Fully opaque orange would be entered as \"FFA500FF\", and semi-transparent orange as \"FFA50080\"; see https://rgbacolorpicker.com/rgba-to-hex for more examples and for hex conversions");

    }

    //A method that checks if the help option is used and returns true
    public boolean hasHelpOption(CommandLineParser parser, String[] args) {
        boolean hasHelp = false;
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption(help)) {
                hasHelp = true;
            }
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            getHelp(parser, args);
        }
        return hasHelp;
    }

    //A method that gets the mesh type from user and is associated to the enum value, returns the type 
    public GeneratorTypes getMeshType(CommandLineParser parser, String[] args) {
        GeneratorTypes type = GeneratorTypes.NONE;
        try {
            CommandLine cmd = parser.parse(options, args);
            String meshtype = cmd.getOptionValue(meshType).toUpperCase();
            type = GeneratorTypes.valueOf(meshtype);
        } catch (ParseException | IllegalArgumentException | NullPointerException e ) {
            System.out.println("WARNING: A valid mesh type was not provided.");
            type = GeneratorTypes.NONE;
        }
        return type;
    }

    //A method that gets vertex thickness from user, returns it as string
    public String getVertThickness(CommandLineParser parser, String[] args) {
        String vertexThicknessValue = new String();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption(vertexThickness)) {
                vertexThicknessValue = cmd.getOptionValue(vertexThickness);
            }
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            getHelp(parser, args);
        }
        return vertexThicknessValue;
    }

    //A method that gets segment thickness from user, returns it as string
    public String getSegThickness(CommandLineParser parser, String[] args) {
        String segmentthicknessvalue = new String();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption(segmentThickness)) {
                segmentthicknessvalue = cmd.getOptionValue(segmentThickness);
            }
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            getHelp(parser, args);
        }
        return segmentthicknessvalue;
    }

    //A method that gets mesh height dimension from user, returns it as int
    public int getDimeH(CommandLineParser parser, String[] args) {
        int dimh = 0;
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption(dimensionh)) {
                dimh = Integer.parseInt(cmd.getOptionValue(dimensionh));

            }
        } catch (ParseException | NumberFormatException e) {
            System.err.println(e.getMessage());
            getHelp(parser, args);
        }
        return dimh;
    }

    //A method that gets mesh width dimension from user, returns it as int
    public int getDimeW(CommandLineParser parser, String[] args) {
        int dimw = 0;
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption(dimensionw)) {
                dimw = Integer.parseInt(cmd.getOptionValue(dimensionw));
            }
        } catch (ParseException | NumberFormatException e) {
            System.err.println(e.getMessage());
            getHelp(parser, args);
        }
        return dimw;
    }

    //A method that gets grid side length from user, returns it as int
    public int getSideLength(CommandLineParser parser, String[] args) {
        int sidelength = 0;
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption(sideLength)) {
                sidelength = Integer.parseInt(cmd.getOptionValue(sideLength));
            }
        } catch (ParseException | NumberFormatException e) {
            System.err.println(e.getMessage());
            getHelp(parser, args);
        }
        return sidelength;
    }

    //A method that gets voronoi relaxation level from user, returns it as int
    public int getRelaxationLevel(CommandLineParser parser, String[] args) {
        int relaxationlevel = 0;
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption(relaxationLevel)) {
                relaxationlevel = Integer.parseInt(cmd.getOptionValue(relaxationLevel));
            }
        } catch (ParseException | NumberFormatException e) {
            System.err.println(e.getMessage());
            getHelp(parser, args);
        }
        return relaxationlevel;
    }

    //A method that gets voronoi numbers of start points from user, returns it as int
    public int getNumOfPoints(CommandLineParser parser, String[] args) {
        int numOfPoints = 0;
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption(startpoints)) {
                numOfPoints = Integer.parseInt(cmd.getOptionValue(startpoints));
            }
        } catch (ParseException | NumberFormatException e) {
            System.err.println(e.getMessage());
            getHelp(parser, args);
        }
        return numOfPoints;
    }

    //A method that gets segment colour from user, returns it as string
    public String getSegColour(CommandLineParser parser, String[] args) {
        String segColour = new String();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption(segmentColour)) {
                segColour = cmd.getOptionValue(segmentColour);
            }
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            getHelp(parser, args);
        }
        return segColour;
    }

    //A method that gets vertex colour from user, returns it as string
    public String getVertColour(CommandLineParser parser, String[] args) {
        String vertColour = new String();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption(vertexColour)) {
                vertColour = cmd.getOptionValue(vertexColour);
            }
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            getHelp(parser, args);
        }
        return vertColour;
    }

    //A method that gets polygon fill colour from user, returns it as string
    public String getPolyFillColour(CommandLineParser parser, String[] args) {
        String pfc = new String();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption(polygonFillColour)) {
                pfc = cmd.getOptionValue(polygonFillColour);
            }
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            getHelp(parser, args);
        }
        return pfc;
    }

    //A method that gets polygon border colour from user, returns it as string
    public String getPolyBorderColour(CommandLineParser parser, String[] args) {
        String pbc = new String();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption(polygonBorderColour)) {
                pbc = cmd.getOptionValue(polygonBorderColour);
            }
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            getHelp(parser, args);
        }
        return pbc;
    }

    //A method that gets polygon border thickness from user, returns it as string
    public String getPolyBorderThickness(CommandLineParser parser, String[] args) {
        String pbt = new String();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption(polygonBorderThickness)) {
                pbt = cmd.getOptionValue(polygonBorderThickness);
            }
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            getHelp(parser, args);
        }
        return pbt;
    }

    //A method that gets the file name from user, returns it as string
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
