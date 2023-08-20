package ca.mcmaster.cas.se2aa4.a2.mesh;

import org.junit.jupiter.api.Test;

import ca.mcmaster.cas.se2aa4.a2.components.Poly;
import ca.mcmaster.cas.se2aa4.a2.components.Polygon;
import ca.mcmaster.cas.se2aa4.a2.components.Seg;
import ca.mcmaster.cas.se2aa4.a2.components.Segment;
import ca.mcmaster.cas.se2aa4.a2.components.Vert;
import ca.mcmaster.cas.se2aa4.a2.components.Vertex;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class FixedMeshTest {

    @Test
    public void addNewPolySameCentroidTest() {
        // Test to check if the polygon is added and can't be duplicated
        Mesh mesh = new FixedMesh();
        Poly poly = new Polygon(3, 5);
        Poly otherPoly = new Polygon(3, 5);
        assertAll("Assert that only one unique Poly is ever added",
                () -> assertTrue(mesh.addPoly(poly)),
                () -> assertFalse(mesh.addPoly(otherPoly)),
                () -> assertFalse(mesh.addPoly(poly)));
    }

    @Test
    public void addSegTest() {
        // Test to check if the segment is added and can't be duplicated
        Mesh mesh = new FixedMesh();
        Seg seg = new Segment(1, 1, 2, 2);
        Seg otherSeg = new Segment(1, 1, 2, 2);
        assertAll("Assert that only one unique Seg is ever added",
                () -> assertTrue(mesh.addSeg(seg)),
                () -> assertFalse(mesh.addSeg(otherSeg)),
                () -> assertFalse(mesh.addSeg(seg)));
    }

    @Test
    public void addVertTest() {
        // Test to check if the vertex is added and can't be duplicated
        Mesh mesh = new FixedMesh();
        Vert vertex = new Vertex(-4.23, 12.3432);
        Vert otherVertex = new Vertex(-4.23, 12.3432);
        assertAll("Assert that only one unique Vert is ever added",
                () -> assertTrue(mesh.addVert(vertex)),
                () -> assertFalse(mesh.addVert(otherVertex)),
                () -> assertFalse(mesh.addVert(vertex)));
    }

    @Test
    public void lockTest() {
        // Test to check if the any new objects won't be added after mesh.lock()
        Mesh mesh = new FixedMesh();
        Poly poly = new Polygon(54.564, 54632.2454);
        Vert vert = new Vertex(42.587, 42320.2112);
        Seg seg = new Segment(-2288.1541, 46645.112, 23, -28722);
        Poly poly2 = new Polygon(-3, -45);
        assertTrue(mesh.addPoly(poly));
        mesh.lock();
        assertAll("Assert that no other objects can be added to any category after locking",
                () -> assertFalse(mesh.addPoly(poly2)),
                () -> assertFalse(mesh.addVert(vert)),
                () -> assertFalse(mesh.addSeg(seg)),
                () -> assertFalse(mesh.addPoly(null)),
                () -> assertFalse(mesh.addVert(null)),
                () -> assertFalse(mesh.addSeg(null)));
    }

    @Test
    public void getPolyTest() {
        // Test to check if the correct polygon is returned
        Mesh mesh = new FixedMesh();
        Poly poly, poly2;
        poly = new Polygon();
        poly2 = new Polygon();
        poly.setCentroid(2, 2);
        poly2.setCentroid(2, 3);
        mesh.addPoly(poly);
        mesh.addPoly(poly2);
        mesh.addPoly(poly2);
        mesh.addPoly(poly);
        mesh.lock();
        List<Poly> polygons = mesh.getPolys();
        assertAll("Assert that getPolys returns a list with unique polygons",
                () -> assertEquals(2, polygons.size()),
                () -> assertEquals(polygons.get(0), poly),
                () -> assertEquals(polygons.get(1), poly2));
    }

    @Test
    public void getSegTest() {
        // Test to check if the correct segment is added
        Mesh mesh = new FixedMesh();
        Seg seg = new Segment(1, 1, 2, 2);
        Seg seg2 = new Segment(3, 3, 4, 4);
        mesh.addSeg(seg2);
        mesh.addSeg(seg);
        mesh.addSeg(seg);
        mesh.addSeg(seg2);
        mesh.lock();
        List<Seg> segments = mesh.getSegs();
        assertAll("Assert that getSegs returns a list with unique segments",
                () -> assertEquals(2, segments.size()),
                () -> assertEquals(segments.get(0), seg),
                () -> assertEquals(segments.get(1), seg2));
    }

    @Test
    public void getVertTest() {
        // Test to check if the correct vertex is added
        Mesh mesh = new FixedMesh();
        Vert vert, vert2;
        vert = new Vertex(2, 2);
        vert2 = new Vertex(-1, 3);
        mesh.addVert(vert);
        mesh.addVert(vert);
        mesh.addVert(vert2);
        mesh.addVert(vert2);
        mesh.lock();
        List<Vert> vertices = mesh.getVerts();
        assertAll("Assert that getVerts returns a list with unique vertices",
                () -> assertEquals(2, vertices.size()),
                () -> assertEquals(vertices.get(0), vert2),
                () -> assertEquals(vertices.get(1), vert));
    }

}