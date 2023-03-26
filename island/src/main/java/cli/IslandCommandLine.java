package cli;

import java.util.Random;
import ca.mcmaster.cas.se2aa4.a3.island.moisture.*;

import org.apache.commons.cli.*;

public class IslandCommandLine {

    // Options that is used for the command line functionality
    private static Option mode = new Option("m", "mode", true,
            "Default::Mode");
    private static Option help = new Option("h", "help", false, "Show usage help");
    private static Option inputOption = new Option("i", "input", true,
            "Input Option");
    private static Option outputOption = new Option("o", "output", true,
            "Output Option");
    private static Option seed = new Option("s", "seed", true,
            "Seed");
    private static Option numOfLakes = new Option("l", "lakes", true,
            "The number of lakes");
    private static Option numOfAquifers = new Option("a", "aquifers", true,
            "The number of aquifers");
    private static Option numOfRivers = new Option("r", "rivers", true,
            "The number of rivers");
    private static Option biome = new Option("b", "biomes", true,
            "Biome type");
    private static Option elevation = new Option("e", "altitude", true,
            "Elevation");
    private static Option shape = new Option("sh", "shape", true,
            "Shape type");
    private static Option soil = new Option("so", "soil", true,
            "Soil Absorption Type");
    private static Options options = new Options();
    private static HelpFormatter formatter = new HelpFormatter();

    // Adds the different options to the type Options
    public void addOptions() {
        options.addOption(mode);
        options.addOption(help);
        options.addOption(inputOption);
        options.addOption(outputOption);
        options.addOption(seed);
        options.addOption(shape);
        options.addOption(elevation);
        options.addOption(biome);
        options.addOption(numOfAquifers);
        options.addOption(numOfLakes);
        options.addOption(numOfRivers);
        options.addOption(soil);
    }

    /**
     * Prints the Help information
     */
    public void getHelp() {
        formatter.printHelp("java -jar island.jar -m arg0 -i arg1 -o arg2", "Options", options,
                "Files must contain .mesh at the end");

    }

    /**
     * Determines if the user wants the Help command
     *
     * @param parser Parser that scans the command line
     * @param args   The input arguments that the parser will scan
     * @return Decision as a boolean
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
     * Gets the Seed from user
     *
     * @param parser Parser that scans the command line
     * @param args   The input arguments that the parser will scan
     * @return Seed as a long
     */
    public long getSeed(CommandLineParser parser, String[] args) {
        long seeds = 0, oldSeeds = new Random().nextLong(0, Long.MAX_VALUE);
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption(seed)) {
                seeds = Long.parseLong(cmd.getOptionValue(seed));
                return seeds;
            }
        } catch (ParseException | NumberFormatException e) {
        }
        return oldSeeds;

    }

    /**
     * Gets the Number of Lakes from the user
     *
     * @param parser Parser that scans the command line
     * @param args   The input arguments that the parser will scan
     * @return Number of Lakes as an integer
     */
    public int getNumOfLakes(CommandLineParser parser, String[] args) {
        int lake = 0;
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption(numOfLakes)) {
                lake = Integer.parseInt(cmd.getOptionValue(numOfLakes));
            }
        } catch (ParseException | NumberFormatException e) {
            lake = 0;
        }
        return lake;
    }

    /**
     * Gets the Number of Aquifers from the user
     *
     * @param parser Parser that scans the command line
     * @param args   The input arguments that the parser will scan
     * @return Number of Aquifers as an integer
     */
    public int getNumOfAquifers(CommandLineParser parser, String[] args) {
        int aquifer = 0;
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption(numOfAquifers)) {
                aquifer = Integer.parseInt(cmd.getOptionValue(numOfAquifers));

            }
        } catch (ParseException | NumberFormatException e) {
            aquifer = 0;
        }
        return aquifer;
    }

    /**
     * Gets the Number of Rivers from the user
     *
     * @param parser Parser that scans the command line
     * @param args   The input arguments that the parser will scan
     * @return Number of Rivers as an integer
     */
    public int getNumOfRivers(CommandLineParser parser, String[] args) {
        int rivers = 0;
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption(numOfRivers)) {
                rivers = Integer.parseInt(cmd.getOptionValue(numOfRivers));
            }
        } catch (ParseException | NumberFormatException e) {
            rivers = 0;
        }
        return rivers;
    }

    /**
     * Gets the Mode from user
     *
     * @param parser Parser that scans the command line
     * @param args   The input arguments that the parser will scan
     * @return Mode
     */
    public ModeTypes getModeType(CommandLineParser parser, String[] args) {
        ModeTypes type = ModeTypes.NONE;
        try {
            CommandLine cmd = parser.parse(options, args);
            String modes = cmd.getOptionValue(mode).toUpperCase();
            type = ModeTypes.valueOf(modes);
        } catch (ParseException | IllegalArgumentException | NullPointerException e) {
            type = ModeTypes.NONE;
        }
        return type;
    }

    /**
     * Gets the Soil Type from user
     *
     * @param parser Parser that scans the command line
     * @param args   The input arguments that the parser will scan
     * @return Soil Type as an enum
     */

    public SoilTypes getSoilType(CommandLineParser parser, String[] args) {
        SoilTypes type = SoilTypes.NORMAL;
        try {
            CommandLine cmd = parser.parse(options, args);
            String soilType = cmd.getOptionValue(soil).toUpperCase();
            type = SoilTypes.valueOf(soilType);
        } catch (ParseException | IllegalArgumentException | NullPointerException e) {
            type = SoilTypes.NORMAL;
        }
        return type;
    }

    /**
     * Gets the Input File from the user
     *
     * @param parser Parser that scans the command line
     * @param args   The input arguments that the parser will scan
     * @return Input File as a String
     */
    public String inputCli(CommandLineParser parser, String[] args) {
        String input = "";
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption(inputOption)) {
                input = cmd.getOptionValue(inputOption);
            } else {
                throw new ParseException(input);
            }
        } catch (ParseException e) {
            System.err.println(e.getMessage());
        }
        return input;
    }

    /**
     * Gets the Output File from the user
     *
     * @param parser Parser that scans the command line
     * @param args   The input arguments that the parser will scan
     * @return Output File as a String
     */
    public String outputCli(CommandLineParser parser, String[] args) {
        String output = "";
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption(outputOption)) {
                output = cmd.getOptionValue(outputOption);
            } else {
                throw new ParseException(output);
            }
        } catch (ParseException e) {
            System.err.println(e.getMessage());
        }
        return output;
    }

}
