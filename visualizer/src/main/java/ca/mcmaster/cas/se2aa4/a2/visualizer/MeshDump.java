package ca.mcmaster.cas.se2aa4.a2.visualizer;

import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;

import java.io.IOException;
import java.util.List;

public class MeshDump {

    public void dump(String fileName) throws IOException {
        MeshFactory factory = new MeshFactory();
        Mesh aMesh = factory.read(fileName);
        dump(aMesh);
    }

    public void dump(Mesh aMesh) {
        this.dumpVertices(aMesh);
        this.dumpSegments(aMesh);
        this.dumpPolygons(aMesh);
        this.dumpMeshProperties(aMesh);
    }

    private void dumpMeshProperties(Mesh aMesh) {
        System.out.println(String.format("|MeshProperties| = %d", aMesh.getPropertiesCount()));
        StringBuffer line = new StringBuffer();
        line.append(" [");
        for (Property p : aMesh.getPropertiesList()) {
            line.append(String.format("%s : %s, ", p.getKey(), p.getValue()));
        }
        line.append("]");
    }

    public void dumpPolygons(Mesh aMesh) {
        List<Polygon> polygons = aMesh.getPolygonsList();
        System.out.println("|Polygons| = " + polygons.size());
        int i = 0;
        for (Polygon p : polygons) {
            StringBuffer line = new StringBuffer();
            line.append(i).append(": ");
            i++;
            line.append(String.format("{%d: (%.2f, %.2f)}", p.getCentroidIdx(),
                    aMesh.getVertices(p.getCentroidIdx()).getX(),
                    aMesh.getVertices(p.getCentroidIdx()).getY()));
            line.append(" < ");
            for (int idx : p.getSegmentIdxsList()) {
                line.append(String.format("%d ", idx));
            }
            line.append(">");
            line.append(" [");
            for (Property prop : p.getPropertiesList()) {
                line.append(String.format("%s -> %s, ", prop.getKey(), prop.getValue()));
            }
            line.append("]");
            System.out.println(line);
        }
    }

    public void dumpSegments(Mesh aMesh) {
        List<Segment> segments = aMesh.getSegmentsList();
        System.out.println("|Segments| = " + segments.size());
        int i = 0;
        for (Segment s : segments) {
            StringBuffer line = new StringBuffer();
            line.append(i).append(": ");
            i++;
            line.append(String.format("{%d <-> %d} ", s.getV1Idx(), s.getV2Idx()));
            line.append(String.format("<(%.2f, %.2f), (%.2f, %.2f)> ", aMesh.getVertices(s.getV1Idx()).getX(),
                    aMesh.getVertices(s.getV1Idx()).getY(),
                    aMesh.getVertices(s.getV2Idx()).getX(), aMesh.getVertices(s.getV2Idx()).getY()));
            line.append(" [");
            for (Property p : s.getPropertiesList()) {
                line.append(String.format("%s -> %s, ", p.getKey(), p.getValue()));
            }
            line.append("]");
            System.out.println(line);
        }
    }

    public void dumpVertices(Mesh aMesh) {
        List<Vertex> vertices = aMesh.getVerticesList();
        System.out.println("|Vertices| = " + vertices.size());
        int i = 0;
        for (Vertex v : vertices) {
            StringBuffer line = new StringBuffer();
            line.append(i).append(": ");
            i++;
            line.append(String.format("(%.2f,%.2f)", v.getX(), v.getY()));
            line.append(" [");
            for (Property p : v.getPropertiesList()) {
                line.append(String.format("%s -> %s, ", p.getKey(), p.getValue()));
            }
            line.append("]");
            System.out.println(line);
        }
    }
}
