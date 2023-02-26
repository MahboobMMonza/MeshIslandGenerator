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
    public void addPolyTest() {
        Mesh mesh = new FixedMesh();
        Polygon poly = new Polygon();
        poly.setCentroid(2, 2);
        assertTrue(mesh.addPoly(poly));
        assertFalse(mesh.addPoly(poly));
    }

    @Test
    public void addNewPolySameCentroidTest() {
        Mesh mesh = new FixedMesh();
        Polygon poly = new Polygon(3, 5);
        Polygon otherPoly = new Polygon(3, 5);
        assertTrue(mesh.addPoly(poly));
        assertFalse(mesh.addPoly(otherPoly));
    }

    @Test
    public void addSegTest() {
        Mesh mesh = new FixedMesh();
        Segment seg = new Segment(1, 1, 2, 2);
        assertTrue(mesh.addSeg(seg));
        assertFalse(mesh.addSeg(seg));
    }

    @Test
    public void addVertTest() {
        Mesh mesh = new FixedMesh();
        Vertex v = new Vertex();
        assertTrue(mesh.addVert(v));
        assertFalse(mesh.addVert(v));
    }

    @Test
    public void lockTest() {
        Mesh mesh = new FixedMesh();
        Polygon poly = new Polygon();
        assertTrue(mesh.addPoly(poly));
        mesh.lock();
        Polygon poly2 = new Polygon();
        assertFalse(mesh.addPoly(poly2));
    }

    @Test
    public void getPolyTest() {
        Mesh mesh = new FixedMesh();
        Polygon poly = new Polygon();
        poly.setCentroid(2, 2);
        mesh.addPoly(poly);
        mesh.addPoly(poly);
        mesh.lock();
        List<Poly> polygons = mesh.getPolys();
        assertEquals(1, polygons.size());
    }

    @Test
    public void getSegTest() {
        Mesh mesh = new FixedMesh();
        Segment seg = new Segment(1, 1, 2, 2);
        Segment seg2 = new Segment(3, 3, 4, 4);
        mesh.addSeg(seg);
        mesh.addSeg(seg);
        mesh.addSeg(seg2);
        mesh.lock();
        List<Seg> segments = mesh.getSegs();
        assertEquals(2, segments.size());
    }

    @Test
    public void getVertTest() {
        Mesh mesh = new FixedMesh();
        Vertex v = new Vertex(2, 2);
        mesh.addVert(v);
        mesh.addVert(v);
        mesh.lock();
        List<Vert> vertices = mesh.getVerts();
        assertEquals(1, vertices.size());
    }

}
