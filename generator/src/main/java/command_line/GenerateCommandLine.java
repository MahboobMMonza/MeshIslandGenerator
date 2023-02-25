package command_line;


import org.apache.commons.cli.*;

public class GenerateCommandLine{

    private static Options options = new Options();
    private static HelpFormatter formatter = new HelpFormatter();
    private static CommandLineParser parser = new DefaultParser();

    private static Option vertexThickness = Option.builder("vt").longOpt("vertexthickness").hasArg().desc("Vertex Thickness").build();
    private static Option segmentThickness = Option.builder("st").longOpt("segmentthickness").hasArg().desc("Segment Thickness").build();
    private static Option dimensionh = Option.builder("dh").longOpt("dimensionh").hasArg().desc("Dimension for the height").build();
    private static Option startpoints = Option.builder("sp").longOpt("startpoints").hasArg().desc("Num of Start Points").build();
    private static Option relaxationlevel = Option.builder("rl").longOpt("relaxationlevel").hasArg().desc("Relaxation Level").build();
    private static Option sideLength = Option.builder("sl").longOpt("sidelength").hasArg().desc("Side Lengths").build();
    private static Option meshType = Option.builder("mt").longOpt("meshtype").hasArg().desc("Mesh Type").build();
    private static Option dimensionw = Option.builder("dw").longOpt("dimensionw").hasArg().desc("Dimension for the width").build();
    private static Option segmentColour = Option.builder("sc").longOpt("segmentcolour").hasArg().desc("Segment Colour").build();
    private static Option vertexColour = Option.builder("vc").longOpt("vertexcolour").hasArg().desc("Vertex Colour").build();
    private static Option polygonFillColour = Option.builder("pf").longOpt("polygonfill").hasArg().desc("Polygon Fill Colour").build();
    private static Option polygonBorderColour = Option.builder("pb").longOpt("polygonborder").hasArg().desc("Polygon Border Colour").build();


    private static void addOptions(){
        options.addOption(dimensionw);
        options.addOption(dimensionh);
        options.addOption(segmentThickness);
        options.addOption(segmentColour);
        options.addOption(vertexColour);
        options.addOption(vertexThickness);
        options.addOption(meshType);
        options.addOption(startpoints);
        options.addOption(relaxationlevel);
        options.addOption(sideLength);
        options.addOption(polygonBorderColour);
        options.addOption(polygonFillColour);
    }
    
    private static String getMeshType(String[] args){
        String type="Grid Mesh";
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("mt")) {
                String meshtype = cmd.getOptionValue("mt");
                if(meshtype == "Vornoi"){
                    type ="Vornoi";
                }
            } 
        } catch (ParseException e) {

        }
        return type;
    }
    
    private static String getVertThickness(String[] args){
        addOptions();
        String vertexThicknessValue = new String();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("vt")) {
                vertexThicknessValue = cmd.getOptionValue("vt");
            }
        } catch (ParseException e) {
            
        }
        return vertexThicknessValue;
    }

    private static String getSegThickness(String[] args){
        addOptions();
        String segmentthicknessvalue = new String();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("st")) {
                segmentthicknessvalue = cmd.getOptionValue("st");
            }
        } catch (ParseException e) {
            
        }
        return segmentthicknessvalue;
    }

    private static int getDimeH(String[] args){
        addOptions();
        int dimh = 500;
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("dh")) {
                dimh = Integer.parseInt(cmd.getOptionValue("dh"));
                
            }
        } catch (ParseException | NumberFormatException e) {
            dimh=500;
        }
        return dimh;
    }

    private static int getDimeW(String[] args){
        addOptions();
        int dimw = 500;
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("dw")) {
                dimw = Integer.parseInt(cmd.getOptionValue("dw"));
            }
        } catch (ParseException | NumberFormatException e) {
            dimw=500;
        }
        return dimw;
    }

    private static int getSideLength(String[] args){
        addOptions();
        int sidelength = 20;
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("sl")) {
                sidelength = Integer.parseInt(cmd.getOptionValue("sl"));
            }
        } catch (ParseException | NumberFormatException e) {
            sidelength=20;
        }
        return sidelength;
    }

    private static int getRelaxationLevel(String[] args){
        addOptions();
        int relaxationlevel = 20;
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("rl")) {
                relaxationlevel = Integer.parseInt(cmd.getOptionValue("rl"));
            }
        } catch (ParseException | NumberFormatException e) {
            relaxationlevel =20;
        }
        return relaxationlevel;
    }

    private static int getNumOfPoints(String[] args){
        addOptions();
        int numOfPoints = 50;
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("sp")) {
                numOfPoints = Integer.parseInt(cmd.getOptionValue("sp"));
            } 
        } catch (ParseException | NumberFormatException e) {
            numOfPoints =50;
        }
        return numOfPoints;
    }

    private static String getSegColour(String[] args){
        addOptions();
        String segmentColour = new String();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("sc")) {
                segmentColour = cmd.getOptionValue("sc");
            }
        } catch (ParseException e) {
            
        }
        return segmentColour;
    }

    private static String getVertColour(String[] args){
        addOptions();
        String vertexColour = new String();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("vc")) {
                vertexColour = cmd.getOptionValue("vc");
            }
        } catch (ParseException e) {
            
        }
        return vertexColour;
    }

    private static String getPolyFillColour(String[] args){
        addOptions();
        String pfc = new String();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("pf")) {
                pfc = cmd.getOptionValue("pf");
            }
        } catch (ParseException e) {
            
        }
        return pfc;
    }

    private static String getPolyBorderColour(String[] args){
        addOptions();
        String pbc = new String();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("pb")) {
                pbc = cmd.getOptionValue("pb");
            }
        } catch (ParseException e) {
            
        }
        return pbc;
    }
    
}


