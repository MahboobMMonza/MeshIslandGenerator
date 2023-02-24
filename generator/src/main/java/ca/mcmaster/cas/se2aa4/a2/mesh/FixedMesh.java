package ca.mcmaster.cas.se2aa4.a2.mesh;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import ca.mcmaster.cas.se2aa4.a2.components.Polygon;
import ca.mcmaster.cas.se2aa4.a2.components.Segment;
import ca.mcmaster.cas.se2aa4.a2.components.Vertex;

/**
 * FixedMesh class, which implements the Mesh interface.
 */
public class FixedMesh implements Mesh {

    private final Set<Polygon> polygonsSet;
    private final Set<Segment> segmentsSet;
    private final Set<Vertex> verticesSet;
    private List<Polygon> polygonsList;
    private List<Segment> segmentsList;
    private List<Vertex> verticesList;
    private float segmentThickness, vertexThickness;
    private final int[] vertexColour, segmentColour, polygonColour;
    private boolean locked;

    /**
     * Creates a new FixedMesh and initializes all necessary fields.
     */
    public FixedMesh() {
        vertexColour = new int[4];
        segmentColour = new int[4];
        polygonColour = new int[4];
        verticesSet = new TreeSet<>();
        polygonsSet = new TreeSet<>();
        segmentsSet = new TreeSet<>();
        segmentThickness = vertexThickness = 1f;
        locked = false;
    }

    @Override
    public boolean addPolygon(Polygon p) {
        return locked ? false : polygonsSet.add(p);
    }

    @Override
    public boolean addSegment(Segment s) {
        return locked ? false : segmentsSet.add(s);
    }

    @Override
    public boolean addVertex(Vertex v) {
        return locked ? false : verticesSet.add(v);
    }

    /**
     * Set the thickness for each Segment in the mesh between 0.25 and 5 if it is
     * unlocked.
     *
     * @param t the desired thickness value as as float.
     * @return true if a thickness value was set, false if the mesh is locked.
     */
    public boolean setSegmentThickness(float t) {
        if (locked)
            return false;
        segmentThickness = Math.min(Math.max(t, 0.25f), 5);
        return true;
    }

    /**
     * Set the thickness for each Vertex in the mesh between 0.25 and 5 if it is
     * unlocked.
     *
     * @param t the desired thickness value as as float.
     * @return true if a thickness value was set, false if the mesh is locked.
     */
    public boolean setVertexThickness(float t) {
        if (locked)
            return false;
        vertexThickness = Math.min(Math.max(t, 0.25f), 5);
        return true;
    }

    /**
     * Set the colour values for each Polygon in the mesh to a colour between 0 and
     * 255 if it is unlocked.
     *
     * @param r the desired red value as an integer.
     * @param g the desired green value as an integer.
     * @param b the desired blue value as an integer.
     * @param a the desired alpha value as an integer.
     * @return true if the colours were set, false if the mesh is locked.
     */
    public boolean setPolygonColour(int r, int g, int b, int a) {
        if (locked)
            return false;
        polygonColour[0] = Math.min(Math.max(r, 0), 255);
        polygonColour[1] = Math.min(Math.max(g, 0), 255);
        polygonColour[2] = Math.min(Math.max(b, 0), 255);
        polygonColour[3] = Math.min(Math.max(a, 0), 255);
        return true;
    }

    /**
     * Set the colour values for each Segment in the mesh to a colour between 0 and
     * 255 if it is unlocked.
     *
     * @param r the desired red value as an integer.
     * @param g the desired green value as an integer.
     * @param b the desired blue value as an integer.
     * @param a the desired alpha value as an integer.
     * @return true if the colours were set, false if the mesh is locked.
     */
    public boolean setSegmentColour(int r, int g, int b, int a) {
        if (locked)
            return false;
        segmentColour[0] = Math.min(Math.max(r, 0), 255);
        segmentColour[1] = Math.min(Math.max(g, 0), 255);
        segmentColour[2] = Math.min(Math.max(b, 0), 255);
        segmentColour[3] = Math.min(Math.max(a, 0), 255);
        return true;
    }

    /**
     * Set the colour values for each non-centroid Vertex in the mesh to a colour
     * between 0 and 255 if it is unlocked.
     *
     * @param r the desired red value as an integer.
     * @param g the desired green value as an integer.
     * @param b the desired blue value as an integer.
     * @param a the desired alpha value as an integer.
     * @return true if the colours were set, false if the mesh is locked.
     */
    public boolean setVertexColour(int r, int g, int b, int a) {
        if (locked)
            return false;
        vertexColour[0] = Math.min(Math.max(r, 0), 255);
        vertexColour[1] = Math.min(Math.max(g, 0), 255);
        vertexColour[2] = Math.min(Math.max(b, 0), 255);
        vertexColour[3] = Math.min(Math.max(a, 0), 255);
        return true;
    }

    @Override
    public List<Polygon> getPolygons() {
        return polygonsList;
    }

    @Override
    public List<Segment> getSegments() {
        return segmentsList;
    }

    @Override
    public List<Vertex> getVertex() {
        return verticesList;
    }

    /**
     * Applies the appropriate properties to each Polygon in the mesh.
     */
    private void decoratePolygons() {
        for (Polygon p : polygonsSet) {
            decoratePolygon(p);
        }
    }

    /**
     * Applies the appropriate properties to each Segment in the mesh.
     */
    private void decorateSegments() {
        for (Segment s : segmentsSet) {
            decorateSegment(s);
        }
    }

    /**
     * Applies the appropriate properties to each Vertex in the mesh.
     */
    private void decorateVertices() {
        for (Vertex v : verticesSet) {
            decorateVertex(v);
        }
    }

    /**
     * Applies the appropriate properties to each centroid in each Polygon in the
     * mesh.
     */
    private void decorateCentroids() {
        for (Polygon p : polygonsSet) {
            Vertex v = new Vertex(p.getCentroidX(), p.getCentroidY());
            decorateCentroid(v);
            verticesSet.add(v);
        }
    }

    /**
     * Apply the segment decorations to the given Segment.
     *
     * @param s the Segment to decorate.
     */
    private void decorateSegment(Segment s) {
        s.setColour(segmentColour[0], segmentColour[1], segmentColour[2], segmentColour[3]);
        s.setThickness(segmentThickness);
    }

    /**
     * Apply the vertex decorations to the given Vertex.
     *
     * @param v the Vertex to decorate.
     */
    private void decorateVertex(Vertex v) {
        v.setColour(vertexColour[0], vertexColour[1], vertexColour[2], vertexColour[3]);
        v.setThickness(vertexThickness);
    }

    /**
     * Apply the centroid vertex decoration to the given centroid Vertex.
     *
     * @param v the centroid to decorate.
     */
    private void decorateCentroid(Vertex v) {
        // Centroid should be completely transparent
        v.setColour(0, 0, 0, 0);
        v.setThickness(0);
    }

    /**
     * Apply the polygon decorations to the given Polygon.
     *
     * @param p the Polygon to decorate.
     */
    private void decoratePolygon(Polygon p) {
        p.setColour(polygonColour[0], polygonColour[1], polygonColour[2], polygonColour[3]);
    }

    @Override
    public void lock() {
        if (locked)
            return;
        decorateVertices();
        decorateSegments();
        decoratePolygons();
        decorateCentroids();
        polygonsList = new ArrayList<>(polygonsSet);
        segmentsList = new ArrayList<>(segmentsSet);
        verticesList = new ArrayList<>(verticesSet);
        locked = true;
    }

}
