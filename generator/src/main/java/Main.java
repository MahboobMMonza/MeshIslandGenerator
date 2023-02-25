import java.io.IOException;

import ca.mcmaster.cas.se2aa4.a2.generator.Generator;
import ca.mcmaster.cas.se2aa4.a2.generator.GridGenerator;
import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.mesh.FixedMesh;
import static ca.mcmaster.cas.se2aa4.a2.creator.MeshCreator.createMesh;

public class Main {

    public static void main(String[] args) throws IOException {
        FixedMesh fMesh = new FixedMesh();
        final int height = 500, width = 500, side = 20;
        Generator gen = new GridGenerator(side);
        MeshFactory factory = new MeshFactory();
        // Make vertices red
        fMesh.setVertexColour(255, 0, 0, 255);
        // Make segments black
        fMesh.setSegmentColour(0, 0, 0, 255);
        // Make polygons yellow
        fMesh.setPolygonColour(255, 255, 0, 255);
        // Set vertex thickness to 3
        fMesh.setVertexThickness(3.0f);
        fMesh.setSegmentThickness(1.0f);
        factory.write(createMesh(fMesh, gen, height, width), args[0]);
    }

}
