import java.io.IOException;

import ca.mcmaster.cas.se2aa4.a2.generator.Generator;
import ca.mcmaster.cas.se2aa4.a2.generator.GridGenerator;
import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.mesh.FixedMesh;
import ca.mcmaster.cas.se2aa4.a2.mesh.Mesh;
import static ca.mcmaster.cas.se2aa4.a2.creator.MeshCreator.createMesh;

public class Main {

    public static void main(String[] args) throws IOException {
        FixedMesh fMesh = new FixedMesh();
        Generator gen = new GridGenerator();
        final int height = 500, width = 500, side = 20;
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
        factory.write(createMesh(fMesh, gen, height, width, side), args[0]);
    }

}
