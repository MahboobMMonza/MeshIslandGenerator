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
        // Make vertices red
        decorator.setVertColour("FF0000FF");
        // Make segments black
        decorator.setSegColour("000000FF");
        // Make polygons green
        decorator.setPolyFillColour("00FF00FF");
        // Set vertex thickness to 3
        decorator.setVertThickness("3");
        // Set segment thickness to 1
        decorator.setSegThickness("1");
        factory.write(createMesh(fMesh, gen, decorator, height, width), args[0]);
    }

}
