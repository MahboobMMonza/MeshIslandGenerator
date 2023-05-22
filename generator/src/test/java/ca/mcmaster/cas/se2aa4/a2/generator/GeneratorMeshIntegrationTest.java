package ca.mcmaster.cas.se2aa4.a2.generator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import ca.mcmaster.cas.se2aa4.a2.components.*;
import ca.mcmaster.cas.se2aa4.a2.mesh.FixedMesh;
import ca.mcmaster.cas.se2aa4.a2.mesh.Mesh;

public class GeneratorMeshIntegrationTest {

    private final int n = 20;
    private final int height = 500;
    private final int width = 500;
    private final GridGenerator generator = new GridGenerator(n);

    // Test to check if the expected amount of polygons is created
    @Test
    public void numOfPoly() {
        Mesh mesh = new FixedMesh(height, width);
        double expectedPolygons = (Math.ceil(width / (n + 0.0)) * (height / (n + 0.0)));
        generator.generate(mesh);
        mesh.lock();
        assertEquals(expectedPolygons, mesh.getPolys().size());
    }

    // Test to check if the correct amount of vertices is created
    @Test
    public void numOfVert() {
        Mesh mesh = new FixedMesh(height, width);
        generator.generate(mesh);
        int expectedVertices = 1301;
        mesh.lock();
        List<Vert> vertices = mesh.getVerts();
        assertEquals(expectedVertices, vertices.size());
    }

    // Test to check if the vertices is not duplicated
    @Test
    public void verticesIsNotDuplicate() {
        Mesh mesh = new FixedMesh(height, width);
        generator.generate(mesh);
        mesh.lock();
        List<Vert> vertices = mesh.getVerts();
        Set<String> vertposition = new HashSet<>();
        for (Vert v : vertices) {
            String position = String.format("(%f, %f)", v.getX(), v.getY());
            assertFalse(vertposition.contains(position));
            vertposition.add(position);
        }
    }

    // Test to check if the correct amount of centroids is created
    @Test
    public void numOfCentroids() {
        Mesh mesh = new FixedMesh(height, width);
        generator.generate(mesh);
        mesh.lock();
        List<Poly> centroids = mesh.getPolys();
        assertEquals(625, centroids.size());
    }

    // Test to check of the right location of the centroid
    @Test
    public void correctCentroids() {
        double bottomCentX = 500.0;
        double bottomCentY = 500.0;
        Mesh mesh = new FixedMesh(height, width);
        generator.generate(mesh);
        mesh.lock();
        List<Poly> centroids = mesh.getPolys();

        Poly topLeft = centroids.get(0);
        assertEquals(10, topLeft.getCentroidX(), 0.00001);
        assertEquals(10, topLeft.getCentroidY(), 0.00001);

        Poly bottomRight = centroids.get(centroids.size() - 1);
        assertEquals(490, bottomRight.getCentroidX(), 0.00001);
        assertEquals(490, bottomRight.getCentroidY(), 0.00001);

        Poly center = centroids.get(centroids.size() / 2);
        assertEquals(250, center.getCentroidX(), 0.00001);
        assertEquals(250, center.getCentroidY(), 0.00001);

        for (Poly centroid : centroids) {
            assertTrue(centroid.getCentroidX() >= 0.5 && centroid.getCentroidX() <= bottomCentX - 0.5);
            assertTrue(centroid.getCentroidY() >= 0.5 && centroid.getCentroidY() <= bottomCentY - 0.5);
        }
    }

    @Test
    public void testCornerPolygonNeighbors() {
        // Creates a new mesh with the minimum width and height
        Mesh mesh = new FixedMesh();
        Generator generator = new GridGenerator(5);
        generator.generate(mesh);
        mesh.lock();
        List<Poly> polys = mesh.getPolys();
        /*
         * for (double[] neighbours : polys.get(0).getNeigbourList()) {
         * System.out.println(String.format("(%.2f, %.2f) :: (%.2f, %.2f)",
         * polys.get(0).getCentroidX(),
         * polys.get(0).getCentroidY(), neighbours[0], neighbours[1]));
         * }
         */
        assertEquals(3, polys.get(0).getNeigbourList().size());
    }

}