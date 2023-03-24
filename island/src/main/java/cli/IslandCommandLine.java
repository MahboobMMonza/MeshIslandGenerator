package cli;

import java.util.Random;

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
    private static Options options = new Options();
    private static HelpFormatter formatter = new HelpFormatter();

    // Adds the different options to the type Options
    public void addOptions() {
        options.addOption(mode);
        options.addOption(help);
        options.addOption(inputOption);
        options.addOption(outputOption);
        options.addOption(seed);
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
        long seeds = new Random().nextLong(0, Long.MAX_VALUE);
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption(seed)) {
                seeds = Long.parseLong(cmd.getOptionValue(seed));
            }
        } catch (ParseException | NumberFormatException e) {
        }
        return seeds;
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
            System.out.println("WARNING: A valid mode type was not provided.");
            type = ModeTypes.NONE;
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
