package cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class VisualizerCommandLine {
    //Options that is used for the command line functionality
    private static Option debugOption = new Option("X", "debug", false,
            "Debug Option");
    private static Option inputOption = new Option("i", "input", true,
            "Input Option");
    private static Option outputOption = new Option("o", "output", true,
            "Output Option");
    private static Option help = new Option("h", "help", false, "Show usage help");
    private static Options options = new Options();
    private static HelpFormatter formatter = new HelpFormatter();

    //Adds the different options to the type Options
    public void addOptions() {
        options.addOption(debugOption);
        options.addOption(inputOption);
        options.addOption(outputOption);
        options.addOption(help);
    }

    /**
     * Prints the Help information
     */
    public void getHelp() {
        formatter.printHelp("java -jar visualizer/visualizer.jar [-i] inputfile [-o] outputfile", "Help", options, "For debugging, Add option [-X]");
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
     * Determines if the user wants the Debug command
     *
     * @param parser Parser that scans the command line
     * @param args The input arguments that the parser will scan
     * @return Decision as a boolean
     */
    public boolean hasDebugOption(CommandLineParser parser, String[] args) {
        boolean result = false;
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption(debugOption)) {
                result = true;
            }
        } catch (ParseException e) {
            System.err.println(e.getMessage());
        }
        return result;

    }

    /**
     * Gets the Input File from the user
     *
     * @param parser Parser that scans the command line
     * @param args The input arguments that the parser will scan
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
     * @param args The input arguments that the parser will scan
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
