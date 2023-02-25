import static ca.mcmaster.cas.se2aa4.a2.creator.MeshCreator.createMesh;

import java.io.IOException;

import ca.mcmaster.cas.se2aa4.a2.decorator.Decorator;
import ca.mcmaster.cas.se2aa4.a2.decorator.FixedDecorator;
import ca.mcmaster.cas.se2aa4.a2.generator.Generator;
import ca.mcmaster.cas.se2aa4.a2.generator.GridGenerator;
import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.mesh.FixedMesh;
import ca.mcmaster.cas.se2aa4.a2.mesh.Mesh;

public class Main {

    public static void main(String[] args) throws IOException {
        final int height = 500, width = 500, side = 20;
        Mesh fMesh = new FixedMesh();
        Decorator decorator = new FixedDecorator();
        Generator gen = new GridGenerator(side);
        MeshFactory factory = new MeshFactory();
        // Make vertices blue
        decorator.setVertColour("0000ffff");
        // Make segments black
        decorator.setSegColour("000000ff");
        // Make polygon fill green
        decorator.setPolyFillColour("00ff00ff");
        // Make polygon border colour orange
        decorator.setPolyBorderColour("ffa500ff");
        // Set vertex thickness to 3
        decorator.setVertThickness("6");
        // Set segment thickness to 1
        decorator.setSegThickness("1");
        decorator.setPolyBorderThickness("3");
        factory.write(createMesh(fMesh, gen, decorator, height, width), args[0]);
    }

}
