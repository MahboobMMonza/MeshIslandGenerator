package command_line;


import org.apache.commons.cli.*;

public class GenerateCommandLine{

    private static Options options = new Options();
    private static HelpFormatter formatter = new HelpFormatter();

    private static Option vertexThickness = Option.builder("vt").longOpt("vertexthickness").hasArg().desc("Vertex Thickness").build();
    private static Option segmentThickness = Option.builder("st").longOpt("segmentthickness").hasArg().desc("Segment Thickness").build();
    private static Option dimensionh = Option.builder("dh").longOpt("dimensionh").hasArg().desc("Dimension for the height (default: 500)").build();
    private static Option startpoints = Option.builder("sp").longOpt("startpoints").hasArg().desc("Number of Start Points (default: 50)").build();
    private static Option relaxationLevel = Option.builder("rl").longOpt("relaxationlevel").hasArg().desc("Relaxation Level (default: 20)").build();
    private static Option sideLength = Option.builder("sl").longOpt("sidelength").hasArg().desc("Side Lengths (default: 20)").build();
    private static Option meshType = Option.builder("mt").longOpt("meshtype").hasArg().desc("Mesh type (default: Grid Mesh)").build();
    private static Option dimensionw = Option.builder("dw").longOpt("dimensionw").hasArg().desc("Dimension for the width (default: 500)").build();
    private static Option segmentColour = Option.builder("sc").longOpt("segmentcolour").hasArg().desc("Segment Colour").build();
    private static Option vertexColour = Option.builder("vc").longOpt("vertexcolour").hasArg().desc("Vertex Colour").build();
    private static Option polygonFillColour = Option.builder("pf").longOpt("polygonfill").hasArg().desc("Polygon Fill Colour").build();
    private static Option polygonBorderColour = Option.builder("pb").longOpt("polygonborder").hasArg().desc("Polygon Border Colour").build();
    private static  Option help = Option.builder("h").longOpt("help").desc("Show usage help").build();
    private static Option polygonBorderThickness = Option.builder("bt").longOpt("borderthickness").hasArg().desc("Polygon Border Thickness").build();
    private static  Option fileName = Option.builder("f").longOpt("filename").desc("Sets File Name").build();

    public void addOptions(){
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
    public void getHelp(CommandLineParser parser,String[] args){
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption(help)) {
                formatter.printHelp("Mesh Generation","Help Needed", options,"Colour must be in RGBA hex format. E.g.: Fully opaque orange would be entered as \"FFA500FF\", and semi-transparent orange as \"FFA50080\"; see https://rgbacolorpicker.com/rgba-to-hex for more examples and for hex conversions");
            }
        } catch (ParseException e) {

        }
    }

    public boolean hasHelpOption(CommandLineParser parser,String[] args){
        boolean hasHelp = false;
        try{
            CommandLine cmd = parser.parse(options,args);
            if (cmd.hasOption(help)) {
                hasHelp=true;
            } else {hasHelp = false;}
        } catch(ParseException e){

        }
        return hasHelp;
    }
    public String getMeshType(CommandLineParser parser,String[] args){
        String type="Grid";
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

    public String getVertThickness(CommandLineParser parser,String[] args){
        String vertexThicknessValue = new String();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption(vertexThickness)) {
                vertexThicknessValue = cmd.getOptionValue(vertexThickness);
            }
        } catch (ParseException e) {

        }
        return vertexThicknessValue;
    }

    public String getSegThickness(CommandLineParser parser,String[] args){
        String segmentthicknessvalue = new String();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption(segmentThickness)) {
                segmentthicknessvalue = cmd.getOptionValue(segmentThickness);
            }
        } catch (ParseException e) {

        }
        return segmentthicknessvalue;
    }

    public int getDimeH(CommandLineParser parser,String[] args){
        int dimh = 500;
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption(dimensionh)) {
                dimh = Integer.parseInt(cmd.getOptionValue(dimensionh));

            }
        } catch (ParseException | NumberFormatException e) {
            dimh=500;
        }
        return dimh;
    }

    public int getDimeW(CommandLineParser parser,String[] args){
        int dimw = 500;
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption(dimensionw)) {
                dimw = Integer.parseInt(cmd.getOptionValue(dimensionw));
            }
        } catch (ParseException | NumberFormatException e) {
            dimw=500;
        }
        return dimw;
    }

    public int getSideLength(CommandLineParser parser,String[] args){
        int sidelength = 20;
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption(sideLength)) {
                sidelength = Integer.parseInt(cmd.getOptionValue(sideLength));
            }
        } catch (ParseException | NumberFormatException e) {
            sidelength=20;
        }
        return sidelength;
    }

    public int getRelaxationLevel(CommandLineParser parser,String[] args){
        int relaxationlevel = 20;
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption(relaxationLevel)) {
                relaxationlevel = Integer.parseInt(cmd.getOptionValue(relaxationLevel));
            }
        } catch (ParseException | NumberFormatException e) {
            relaxationlevel =20;
        }
        return relaxationlevel;
    }

    public int getNumOfPoints(CommandLineParser parser,String[] args){
        int numOfPoints = 50;
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption(startpoints)) {
                numOfPoints = Integer.parseInt(cmd.getOptionValue(startpoints));
            }
        } catch (ParseException | NumberFormatException e) {
            numOfPoints =50;
        }
        return numOfPoints;
    }

    public String getSegColour(CommandLineParser parser,String[] args){
        String segColour = new String();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption(segmentColour)) {
                segColour = cmd.getOptionValue(segmentColour);
            }
        } catch (ParseException e) {

        }
        return segColour;
    }

    public String getVertColour(CommandLineParser parser,String[] args){
        String vertColour = new String();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption(vertexColour)) {
                vertColour = cmd.getOptionValue(vertexColour);
            }
        } catch (ParseException e) {

        }
        return vertColour;
    }

    public String getPolyFillColour(CommandLineParser parser,String[] args){
        String pfc = new String();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption(polygonFillColour)) {
                pfc = cmd.getOptionValue(polygonFillColour);
            }
        } catch (ParseException e) {

        }
        return pfc;
    }

    public String getPolyBorderColour(CommandLineParser parser,String[] args){
        String pbc = new String();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption(polygonBorderColour)) {
                pbc = cmd.getOptionValue(polygonBorderColour);
            }
        } catch (ParseException e) {

        }
        return pbc;
    }

    public String getPolyBorderThickness(CommandLineParser parser,String[] args){
        String pbt = new String();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption(polygonBorderThickness)) {
                pbt = cmd.getOptionValue(polygonBorderThickness);
            }
        } catch (ParseException e) {

        }
        return pbt;
    }

    public String setFileName(CommandLineParser parser,String[] args){
        String filename = new String();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption(fileName)) {
                filename = cmd.getOptionValue(fileName);
            }
        } catch (ParseException e) {

        }
        return filename;
    }



}



