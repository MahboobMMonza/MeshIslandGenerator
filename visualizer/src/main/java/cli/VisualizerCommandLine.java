package cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class VisualizerCommandLine {
    private static Option debugOption = new Option("X", "debug", false,
            "Debug Option");
    private static Option inputOption = new Option("i", "input", true,
            "Input Option");
    private static Option outputOption = new Option("o", "output", true,
            "Output Option");
    private static Option help = new Option("h", "help", false, "Show usage help");
    private static Options options = new Options();
    private static HelpFormatter formatter = new HelpFormatter();

    public void addOptions() {
        options.addOption(debugOption);
        options.addOption(inputOption);
        options.addOption(outputOption);
        options.addOption(help);
    }

    public void getHelp(CommandLineParser parser, String[] args) {
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption(help)) {
                formatter.printHelp("java -jar visualizer/visualizer.jar [-i] inputfile [-o] outputfile", "Help", options, "For debugging, Add option [-X]");
            }
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            getHelp(parser, args);
        }
    }

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
