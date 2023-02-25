package ca.mcmaster.cas.se2aa4.a2.mesh;

import org.junit.jupiter.api.Test;

import ca.mcmaster.cas.se2aa4.a2.components.Polygon;
import ca.mcmaster.cas.se2aa4.a2.components.Segment;
import ca.mcmaster.cas.se2aa4.a2.components.Vertex;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;


public class FixedMeshTest {

    @Test
    public void addPolyTest(){
        Mesh mesh = new FixedMesh();
        Polygon poly = new Polygon();
        poly.setCentroid(2,2);
        assertTrue(mesh.addPolygon(poly));
        assertFalse(mesh.addPolygon(poly));
    }

    @Test
    public void addSegTest(){
        Mesh mesh = new FixedMesh();
        Segment seg = new Segment(1, 1, 2, 2);
        assertTrue(mesh.addSegment(seg));
        assertFalse(mesh.addSegment(seg));
    }

    @Test
    public void addVertTest(){
        Mesh mesh = new FixedMesh();
        Vertex v = new Vertex();
        assertTrue(mesh.addVertex(v));
        assertFalse(mesh.addVertex(v));
    }

    @Test
    public void lockTest(){
        Mesh mesh = new FixedMesh();
        Polygon poly = new Polygon();
        assertTrue(mesh.addPolygon(poly));
        mesh.lock();
        Polygon poly2 = new Polygon();
        assertFalse(mesh.addPolygon(poly2));
    }

    @Test
    public void getPolyTest(){
        Mesh mesh = new FixedMesh();
        Polygon poly = new Polygon();
        poly.setCentroid(2,2);
        mesh.addPolygon(poly);
        mesh.addPolygon(poly);
        mesh.lock();
        List<Polygon> polygons = mesh.getPolygons();
        assertEquals(1, polygons.size());
    }

    @Test
    public void getSegTest(){
        Mesh mesh = new FixedMesh();
        Segment seg = new Segment(1, 1, 2, 2);
        Segment seg2 = new Segment(3, 3, 4, 4);
        mesh.addSegment(seg);
        mesh.addSegment(seg);
        mesh.addSegment(seg2);
        mesh.lock();
        List<Segment> segments = mesh.getSegments();
        assertEquals(2, segments.size());
    }

    @Test
    public void getVertTest(){
        Mesh mesh = new FixedMesh();
        Vertex v = new Vertex(2,2);
        mesh.addVertex(v);
        mesh.addVertex(v);
        mesh.lock();
        List<Vertex> vertices = mesh.getVertex();
        assertEquals(1, vertices.size());
    }

    @Test
    public void setVertThicknessTest(){
        FixedMesh mesh = new FixedMesh();
        assertTrue(mesh.setVertexThickness(3));
    }

    @Test
    public void setSegThicknessTest(){
        FixedMesh mesh = new FixedMesh();
        assertTrue(mesh.setSegmentThickness(3));
    }

    @Test
    public void setSegColourTest(){
        FixedMesh mesh = new FixedMesh();
        assertTrue(mesh.setSegmentColour(0, 0, 0, 0));
        assertTrue(mesh.setSegmentColour(200, 5, 100, 5));
    }

    @Test
    public void setVertColourTest(){
        FixedMesh mesh = new FixedMesh();
        assertTrue(mesh.setVertexColour(0, 0, 0, 0));
        assertTrue(mesh.setVertexColour(200, 5, 100, 5));
    }

    @Test
    public void setPolyColourTest(){
        FixedMesh mesh = new FixedMesh();
        assertTrue(mesh.setPolygonColour(0, 0, 0, 0));
        assertTrue(mesh.setPolygonColour(200, 5, 100, 5));
    }
}
