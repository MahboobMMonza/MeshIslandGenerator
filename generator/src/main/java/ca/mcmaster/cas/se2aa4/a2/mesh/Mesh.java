package ca.mcmaster.cas.se2aa4.a2.mesh;

import java.util.List;

import ca.mcmaster.cas.se2aa4.a2.components.*;

public interface Mesh {

    void addPolygon(Polygon p);

    void addSegment(Segment s);

    void addVertex(Vertex v);

    void setSegmentThickness(float t);

    void setVertexThickness(float t);

    void finalize();

    void convert();

    List<Polygon> getPolygons();

    List<Segment> getSegments();

    List<Vertex> getVertex();

}
