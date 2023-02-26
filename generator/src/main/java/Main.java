import static ca.mcmaster.cas.se2aa4.a2.creator.MeshCreator.createMesh;

import java.io.IOException;

import ca.mcmaster.cas.se2aa4.a2.decorator.Decorator;
import ca.mcmaster.cas.se2aa4.a2.decorator.FixedDecorator;
import ca.mcmaster.cas.se2aa4.a2.generator.Generator;
import ca.mcmaster.cas.se2aa4.a2.generator.GridGenerator;
import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.mesh.FixedMesh;
import ca.mcmaster.cas.se2aa4.a2.mesh.Mesh;
import cli.GeneratorCommandLine;

import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;

public class Main {
    public static void main(String[] args) throws IOException {
        CommandLineParser parser = new DefaultParser();
        GeneratorCommandLine cmd = new GeneratorCommandLine();
        cmd.addOptions();
        if (cmd.hasHelpOption(parser, args)) {
            cmd.getHelp(parser, args);
        } else {
            int height = cmd.getDimeH(parser, args), width = cmd.getDimeW(parser, args);
            Mesh fMesh = new FixedMesh(height, width);
            Decorator decorator = new FixedDecorator();
            Generator gen = new GridGenerator(cmd.getSideLength(parser, args));
            MeshFactory factory = new MeshFactory();
            decorator.setVertColour(cmd.getVertColour(parser, args));
            decorator.setSegColour(cmd.getSegColour(parser, args));
            decorator.setPolyFillColour(cmd.getPolyFillColour(parser, args));
            decorator.setPolyBorderColour(cmd.getPolyBorderColour(parser, args));
            decorator.setVertThickness(cmd.getVertThickness(parser, args));
            decorator.setSegThickness(cmd.getSegThickness(parser, args));
            decorator.setPolyBorderThickness(cmd.getPolyBorderThickness(parser, args));
            factory.write(createMesh(fMesh, gen, decorator), cmd.setFileName(parser, args));
        }
    }

}
