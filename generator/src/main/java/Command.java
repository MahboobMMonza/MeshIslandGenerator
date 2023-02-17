import org.apache.commons.cli.*;

import java.io.PrintWriter;

public class CLI {

    // Creates the different options needed in the command line
    private static final Option Color = new Option("c","color",false,"Sets the color");
    private static final Option Thickness = new Option("t","thickness",false,"Sets the thickness");

    // Gives the user a help statement if the command line does not fit requierments
    private static void printHelp(Options options){
        HelpFormatter formatter = new HelpFormatter();
        PrintWriter pw = new PrintWriter(System.out);
        pw.println("Mesh Generation " + Math.class.getPackage().getSpecificationVersion());
        pw.println();
        formatter.printUsage(pw, 100, "java -jar MeshGeneration.jar [options] Color Thickness");
        formatter.printOptions(pw,100,options,2,5);
        pw.close();
    }


    public static void main(String[] args) {
        CommandLineParser clp = new DefaultParser();

        Options options =new Options();
        options.addOption(Color);
        options.addOption(Thickness);



        try{
            CommandLine cl = clp.parse(options, args);
            if(cl.getArgList().size() < 2){
                printHelp(options);
                System.exit(-1);
            }
            try {
                String a = cl.getArgList().get(0);
                int b = Integer.parseInt(cl.getArgList().get(1));
            } catch (Exception e){
                printHelp(options);
                e.printStackTrace();
            }


            if (cl.hasOption(Color)&& cl.hasOption(Thickness)){
                System.out.println(cl.getArgList().get(0));
                System.out.println(cl.getArgList().get(1));
            } else if (cl.hasOption(Thickness)){
                System.out.println(cl.getArgList().get(1));
            } else{
                printHelp(options);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
