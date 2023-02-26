package cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Debug {
    private static Option debugOption = new Option("X", "debug", true,
            "Debug Option");
    private static Options options = new Options();

    public boolean hasDebugOption(CommandLineParser parser, String[] args){
        options.addOption(debugOption);
        boolean result = false;
        try{
            CommandLine cmd = parser.parse(options, args);
            if(cmd.hasOption(debugOption)){
                result = true;
            }
        } catch (ParseException e) {
            System.err.println(e.getMessage());
        }
        return result;

    }

}
