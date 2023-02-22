package ca.mcmaster.cas.se2aa4.a2.mesh;

import java.util.List;

import ca.mcmaster.cas.se2aa4.a2.components.*;

public interface Mesh {

    boolean addPolygon(Polygon p);

    boolean addSegment(Segment s);

    boolean addVertex(Vertex v);

    void lock();

    List<Polygon> getPolygons();

    List<Segment> getSegments();

    List<Vertex> getVertex();

}
