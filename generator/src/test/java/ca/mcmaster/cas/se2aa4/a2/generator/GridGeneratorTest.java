package ca.mcmaster.cas.se2aa4.a2.generator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Arrays;
import ca.mcmaster.cas.se2aa4.a2.components.*;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.mesh.FixedMesh;
import ca.mcmaster.cas.se2aa4.a2.mesh.Mesh;

public class GridGeneratorTest {

    private GridGenerator generator = new GridGenerator();
    private int height = 500;
    private int width = 500;
    private int n = 20;
    

    @Test
    public void numOfPoly() {
        Mesh mesh = new FixedMesh();
        double expectedPolygons = (Math.ceil(width / (n + 0.0)) * (height / (n + 0.0)));
        generator.generate(mesh, height, width, n);
        mesh.lock();
        assertEquals(expectedPolygons, mesh.getPolygons().size());
    }

    @Test
    public void meshIsNotNull() {
        DotGen g = new DotGen();
        Structs.Mesh aMesh = g.generate();
        assertNotNull(aMesh);
    }

    @Test
    public void numOfVert(){
        Mesh mesh = new FixedMesh();
        generator.generate(mesh,height,width,n);
        int expectedVertices = 1301;
        mesh.lock();
        List<Vertex> vertices = mesh.getVertex();
        assertEquals(expectedVertices,vertices.size());
    }

    @Test
    public void verticesIsNotDuplicate(){
        Mesh mesh = new FixedMesh();
        generator.generate(mesh,height,width,n);
        mesh.lock();
        List<Vertex> vertices = mesh.getVertex();
        Set<String> vertposition = new HashSet<>();
        for (Vertex v : vertices){
            String position = String.format("(%f, %f)", v.getX(), v.getY());
            assertFalse(vertposition.contains(position));
            vertposition.add(position);
        }
    }

    @Test
    public void numOfCentroids(){
        Mesh mesh = new FixedMesh();
        generator.generate(mesh,height,width,n);
        mesh.lock();
        List<Polygon> centroids = mesh.getPolygons();
        assertEquals(625, centroids.size());
    }

    @Test
    public void correctCentroids(){
        double bottomCentX = 500.0;
        double bottomCentY = 500.0;
        Mesh mesh = new FixedMesh();
        generator.generate(mesh,height,width,n);
        mesh.lock();
        List<Polygon> centroids = mesh.getPolygons();

        Polygon topLeft = centroids.get(0);
        assertEquals(10, topLeft.getCentroidX(), 0.00001);
        assertEquals(10, topLeft.getCentroidY(), 0.00001);
    
        Polygon bottomRight = centroids.get(centroids.size()-1);
        assertEquals(490, bottomRight.getCentroidX(), 0.00001);
        assertEquals(490, bottomRight.getCentroidY(), 0.00001);
       
        Polygon center = centroids.get(centroids.size()/2);
        assertEquals(250, center.getCentroidX(), 0.00001);
        assertEquals(250, center.getCentroidY(), 0.00001);

        for (Polygon centroid : centroids) {
            assertTrue(centroid.getCentroidX() >= 0.5 && centroid.getCentroidX() <= bottomCentX - 0.5);
            assertTrue(centroid.getCentroidY() >= 0.5 && centroid.getCentroidY() <= bottomCentY - 0.5);
        }
  }


    @Test
    public void testCornerPolygonNeighbors() {
    Mesh mesh = new FixedMesh();
    generator.generate(mesh, 30, 30, 3);
    mesh.lock();
    List<Polygon> polys = mesh.getPolygons();
    for(double[] neighbours : polys.get(0).getNeigbourList()){
        System.out.println(String.format("(%.2f, %.2f) :: (%.2f, %.2f)", polys.get(0).getCentroidX(), polys.get(0).getCentroidY(), neighbours[0], neighbours[1]));
    }

    assertEquals(3, polys.get(0).getNeigbourList().size());
}
  
}





    

    



