import static ca.mcmaster.cas.se2aa4.a2.creator.MeshCreator.createMesh;

import java.io.IOException;

import ca.mcmaster.cas.se2aa4.a2.decorator.Decorator;
import ca.mcmaster.cas.se2aa4.a2.decorator.FixedDecorator;
import ca.mcmaster.cas.se2aa4.a2.generator.Generator;
import ca.mcmaster.cas.se2aa4.a2.generator.GridGenerator;
import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.mesh.FixedMesh;
import ca.mcmaster.cas.se2aa4.a2.mesh.Mesh;
import command_line.GenerateCommandLine;

public class Main {

    public static void main(String[] args) throws IOException {

        GenerateCommandLine cmd = new GenerateCommandLine();
        cmd.getHelp(args);
        Mesh fMesh = new FixedMesh();
        Decorator decorator = new FixedDecorator();
        Generator gen = new GridGenerator(cmd.getSideLength(args));
        MeshFactory factory = new MeshFactory();
        decorator.setVertColour(cmd.getVertColour(args));
        decorator.setSegColour(cmd.getSegColour(args));
        decorator.setPolyFillColour(cmd.getPolyFillColour(args));
        decorator.setPolyBorderColour(cmd.getPolyBorderColour(args));
        decorator.setVertThickness(cmd.getVertThickness(args));
        decorator.setSegThickness(cmd.getSegThickness(args));
        decorator.setPolyBorderThickness(cmd.getPolyBorderThickness(args));
        factory.write(createMesh(fMesh, gen, decorator, cmd.getDimeH(args), cmd.getDimeW(args)), cmd.setFileName(args));
    }

}
