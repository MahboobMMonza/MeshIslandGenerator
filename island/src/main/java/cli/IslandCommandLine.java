package cli;

import ca.mcmaster.cas.se2aa4.a3.island.biomes.*;
import ca.mcmaster.cas.se2aa4.a3.island.elevation.*;
import ca.mcmaster.cas.se2aa4.a3.island.moisture.*;
import ca.mcmaster.cas.se2aa4.a3.island.shaper.*;
import ca.mcmaster.cas.se2aa4.a3.island.water.aquifer.*;
import ca.mcmaster.cas.se2aa4.a3.island.water.lake.*;

import org.apache.commons.cli.*;

public class IslandCommandLine {

    // Options that is used for the command line functionality
    private static Option help = new Option("h", "help", false, "Show usage help");
    private static Option inputOption = new Option("i", "input", true,
            "Input Option :: This is a required option");
    private static Option outputOption = new Option("o", "output", true,
            "Output Option :: This is a required option");
    private static Option seed = new Option("s", "seed", true,
            "Seed :: Provides a random seed if not provided");
    private static Option numOfLakes = new Option("l", "lakes", true,
            "The number of lakes :: MIN = 0");
    private static Option numOfAquifers = new Option("a", "aquifers", true,
            "The number of aquifers :: MIN = 0");
    private static Option numOfRivers = new Option("r", "rivers", true,
            "The number of rivers :: MIN = 0");
    private static Option lakeTypes = new Option("lt", "laketype", true,
            "Lake type :: Default is Basic");
    private static Option aquiferTypes = new Option("at", "aquifertype", true,
            "Aquifer type :: Default is Basic");
    private static Option biome = new Option("b", "biomes", true,
            "Biome type :: Default is Desert");
    private static Option elevation = new Option("e", "altitude", true,
            "Elevation type :: Default is Normal");
    private static Option moistureType = new Option("mt", "moisture", true,
            "Moisture type :: Default is Normal");
    private static Option shape = new Option("sh", "shape", true,
            "Shape type :: Default is Round");
    private static Option soil = new Option("so", "soil", true,
            "Soil Absorption Type :: Default is Normal");
    private static Option mode = new Option("m", "mode", true,
            "Mode type :: This is a required option");
    private static Options options = new Options();
    private static HelpFormatter formatter = new HelpFormatter();

    // Adds the different options to the type Options
    public void addOptions() {
        options.addOption(aquiferTypes);
        options.addOption(lakeTypes);
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
        options.addOption(moistureType);
        options.addOption(mode);
    }

    /**
     * Prints the Help information
     */
    public void getHelp() {
        formatter.printHelp("java -jar island.jar -m arg0 -i arg1 -o arg2", "Help", options,
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
        long seeds = 0;
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption(seed)) {
                seeds = Long.parseLong(cmd.getOptionValue(seed));
                return seeds;
            }
        } catch (ParseException | NumberFormatException e) {
        }
        return seeds;

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
     * Gets the Mode Type from the user
     *
     * @param parser Parser that scans the command line
     * @param args   The input arguments that the parser will scan
     * @return Mode Type as an enum
     */

    public ModeTypes getModeType(CommandLineParser parser, String[] args) {
        ModeTypes type = ModeTypes.NONE;
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption(mode)) {
                String modes = cmd.getOptionValue(mode).toUpperCase();
                type = ModeTypes.valueOf(modes);
            }
        } catch (ParseException e) {
        }
        return type;
    }

    /**
     * Gets the Biome Type from user
     *
     * @param parser Parser that scans the command line
     * @param args   The input arguments that the parser will scan
     * @return Biome type as a factory
     */
    public AbstractBiomeFactory getBiomeType(CommandLineParser parser, String[] args) {
        BiomeTypes type;
        AbstractBiomeFactory biomeFactory = new RainforestBiomeFactory();
        try {
            CommandLine cmd = parser.parse(options, args);
            String modes = cmd.getOptionValue(biome).toUpperCase();
            type = BiomeTypes.valueOf(modes);
            switch (type) {
                case DESERT:
                    biomeFactory = new DesertBiomeFactory();
                    return biomeFactory;
                case FROSTED:
                    biomeFactory = new FrostedBiomeFactory();
                    return biomeFactory;
                case RAINFOREST:
                    biomeFactory = new RainforestBiomeFactory();
                    return biomeFactory;
                default:
                    return biomeFactory;
            }
        } catch (ParseException | IllegalArgumentException | NullPointerException e) {
            return biomeFactory;
        }
    }

    /**
     * Gets the Elevation Type from user
     *
     * @param parser Parser that scans the command line
     * @param args   The input arguments that the parser will scan
     * @return Elevation type as a factory
     */

    public AbstractElevationFactory getElevationType(CommandLineParser parser, String[] args) {
        ElevationTypes type = ElevationTypes.NORMAL;
        try {
            CommandLine cmd = parser.parse(options, args);
            String elevationType = cmd.getOptionValue(elevation).toUpperCase();
            type = ElevationTypes.valueOf(elevationType);
            AbstractElevationFactory elevationFactory;
            switch (type) {
                case STEEP:
                    elevationFactory = new SteepElevationFactory();
                    return elevationFactory;
                case BASIC:
                    elevationFactory = new BasicElevationFactory();
                    return elevationFactory;
                case NORMAL:
                    elevationFactory = new NormalElevationFactory();
                    return elevationFactory;
                case FLAT:
                    elevationFactory = new FlatElevationFactory();
                    return elevationFactory;
                case LOFTY:
                    elevationFactory = new LoftyElevationFactory();
                    return elevationFactory;
                default:
                    elevationFactory = new NormalElevationFactory();
                    return elevationFactory;
            }
        } catch (ParseException | IllegalArgumentException | NullPointerException e) {
            System.out.println("WARNING: Invalid Elevation Type. Defaulting to Normal");
            AbstractElevationFactory elevationFactory = new NormalElevationFactory();
            return elevationFactory;
        }
    }

    /**
     * Gets the Lake Type from user
     *
     * @param parser Parser that scans the command line
     * @param args   The input arguments that the parser will scan
     * @return Lake type as a factory
     */

    public AbstractLakeFactory getLakeType(CommandLineParser parser, String[] args) {
        LakeTypes type;
        AbstractLakeFactory lakeFactory = new BasicLakeFactory();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption(lakeTypes)) {
                String lakeType = cmd.getOptionValue(lakeTypes).toUpperCase();
                type = LakeTypes.valueOf(lakeType);
                lakeFactory = new BasicLakeFactory();
                return lakeFactory;
            }
        } catch (ParseException | IllegalArgumentException | NullPointerException e) {
            lakeFactory = new BasicLakeFactory();
            return lakeFactory;
        }
        return lakeFactory;
    }

    /**
     * Gets the Aquifer Type from user
     *
     * @param parser Parser that scans the command line
     * @param args   The input arguments that the parser will scan
     * @return Aquifer type as a factory
     */

    public AbstractAquiferFactory getAquiferType(CommandLineParser parser, String[] args) {
        AquiferTypes type;
        AbstractAquiferFactory aquiferFactory = new BasicAquiferFactory();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption(aquiferTypes)) {
                String aquiferType = cmd.getOptionValue(aquiferTypes).toUpperCase();
                type = AquiferTypes.valueOf(aquiferType);
                aquiferFactory = new BasicAquiferFactory();
                return aquiferFactory;
            }
        } catch (ParseException | IllegalArgumentException | NullPointerException e) {
            aquiferFactory = new BasicAquiferFactory();
            return aquiferFactory;
        }
        return aquiferFactory;
    }

    /**
     * Gets the Moisture Type from user
     *
     * @param parser Parser that scans the command line
     * @param args   The input arguments that the parser will scan
     * @return Moisture type as a factory
     */

    public AbstractMoistureFactory getMoistureType(CommandLineParser parser, String[] args) {
        MoistureTypes type;
        AbstractMoistureFactory moistureFactory;
        try {
            CommandLine cmd = parser.parse(options, args);
            String MoistureType = cmd.getOptionValue(moistureType).toUpperCase();
            type = MoistureTypes.valueOf(MoistureType);
            switch (type) {
                case NORMAL:
                    moistureFactory = new NormalMoistureFactory();
                    return moistureFactory;
                case BASIC:
                    moistureFactory = new BasicMoistureFactory();
                    return moistureFactory;
                default:
                    moistureFactory = new NormalMoistureFactory();
                    return moistureFactory;
            }
        } catch (ParseException | IllegalArgumentException | NullPointerException e) {
            moistureFactory = new NormalMoistureFactory();
            return moistureFactory;
        }
    }

    /**
     * Gets the Shape Type from user
     *
     * @param parser Parser that scans the command line
     * @param args   The input arguments that the parser will scan
     * @return Shape Type as a factory
     */
    public AbstractShaperFactory getShapeType(CommandLineParser parser, String[] args) {
        ShapeTypes type = ShapeTypes.ROUND;
        AbstractShaperFactory shapeFilterFactory;
        try {
            CommandLine cmd = parser.parse(options, args);
            String shapeType = cmd.getOptionValue(shape).toUpperCase();
            type = ShapeTypes.valueOf(shapeType);
            switch (type) {
                case RECTANGLE:
                    shapeFilterFactory = new RectangleShaperFactory();
                    return shapeFilterFactory;
                case ROUND:
                    shapeFilterFactory = new RoundShaperFactory();
                    return shapeFilterFactory;
                case OVAL:
                    shapeFilterFactory = new OvalShaperFactory();
                    return shapeFilterFactory;
                case LAGOON:
                    shapeFilterFactory = new LagoonShaperFactory();
                default:
                    shapeFilterFactory = new RoundShaperFactory();
                    return shapeFilterFactory;
            }
        } catch (ParseException | IllegalArgumentException | NullPointerException e) {
            System.out.println("WARNING: Invalid Shape Type, Defaulting to Round");
            shapeFilterFactory = new RoundShaperFactory();
            return shapeFilterFactory;
        }
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
