package ca.mcmaster.cas.se2aa4.a2.mesh;

import ca.mcmaster.cas.se2aa4.a2.components.*;

public interface Mesh {

    void addPolygon(Poly p);

    void addSegment(Seg s);

    void addVertex(Vert v);

    void setSegmentThickness(float t);

    void setVertexThickness(float t);

    void finalize();

    void convert();
    
    List<Poly> getPolygans();

    List<Seg> getSegments();

    List<Vert> getVertex();

}
