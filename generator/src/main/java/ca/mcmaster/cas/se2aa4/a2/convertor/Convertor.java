package ca.mcmaster.cas.se2aa4.a2.convertor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;

/**
 * Convertor class converts high-level
 * {@link ca.mcmaster.cas.se2aa4.a2.mesh.Mesh} into an equivalent
 * {@link ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh}.
 */
public class Convertor {

    private static List<Vertex> createAllVertices(final List<ca.mcmaster.cas.se2aa4.a2.components.Vertex> vertices) {
        final List<Vertex> vertexList = new ArrayList<>();
        Property colour, thickness;
        int[] colours;
        String rgba, thick;
        for (final ca.mcmaster.cas.se2aa4.a2.components.Vertex vertex : vertices) {
            colours = vertex.getColour();
            rgba = String.format("%d,%d,%d,%d", colours[0], colours[1], colours[2], colours[3]);
            thick = String.format("%.2f", vertex.getThickness());
            colour = Property.newBuilder()
                    .setKey("rgba_color")
                    .setValue(rgba)
                    .build();
            thickness = Property.newBuilder()
                    .setKey("thickness")
                    .setValue(thick)
                    .build();
            vertexList.add(Vertex.newBuilder()
                    .setX(vertex.getX())
                    .setY(vertex.getY())
                    .addProperties(colour)
                    .addProperties(thickness)
                    .build());
        }
        return vertexList;
    }

    private static List<Segment> createAllSegments(final List<ca.mcmaster.cas.se2aa4.a2.components.Segment> segments,
            final List<ca.mcmaster.cas.se2aa4.a2.components.Vertex> vertices) {
        final List<Segment> segmentList = new ArrayList<>();
        ca.mcmaster.cas.se2aa4.a2.components.Vertex idxVert;
        Property colour, thickness;
        int[] colours;
        int v1Idx, v2Idx;
        String rgba, thick;
        for (ca.mcmaster.cas.se2aa4.a2.components.Segment segment : segments) {
            colours = segment.getColour();
            rgba = String.format("%d,%d,%d,%d", colours[0], colours[1], colours[2], colours[3]);
            thick = String.format("%.2f", segment.getThickness());
            colour = Property.newBuilder()
                    .setKey("rgba_color")
                    .setValue(rgba)
                    .build();
            thickness = Property.newBuilder()
                    .setKey("thickness")
                    .setValue(thick)
                    .build();
            idxVert = new ca.mcmaster.cas.se2aa4.a2.components.Vertex(segment.getX1(), segment.getY1());
            v1Idx = Collections.binarySearch(vertices, idxVert);
            idxVert = new ca.mcmaster.cas.se2aa4.a2.components.Vertex(segment.getX2(), segment.getY2());
            v2Idx = Collections.binarySearch(vertices, idxVert);
            segmentList.add(Segment.newBuilder()
                    .setV1Idx(v1Idx)
                    .setV2Idx(v2Idx)
                    .addProperties(colour)
                    .addProperties(thickness)
                    .build());
        }
        return segmentList;
    }

    private static List<Polygon> createAllPolygons(final List<ca.mcmaster.cas.se2aa4.a2.components.Polygon> polygons,
            final List<ca.mcmaster.cas.se2aa4.a2.components.Segment> segments,
            final List<ca.mcmaster.cas.se2aa4.a2.components.Vertex> vertices) {
        final List<Polygon> polygonList = new ArrayList<>();
        ca.mcmaster.cas.se2aa4.a2.components.Vertex cent;
        ca.mcmaster.cas.se2aa4.a2.components.Segment seg;
        List<double[]> polyVerts;
        Property colour;
        int[] colours;
        String rgba;
        int centIdx, segIdx;
        for (ca.mcmaster.cas.se2aa4.a2.components.Polygon polygon : polygons) {
            colours = polygon.getColour();
            rgba = String.format("%d,%d,%d,%d", colours[0], colours[1], colours[2], colours[3]);
            colour = Property.newBuilder()
                    .setKey("rgba_color")
                    .setValue(rgba)
                    .build();
            cent = new ca.mcmaster.cas.se2aa4.a2.components.Vertex(polygon.getCentroidX(), polygon.getCentroidY());
            centIdx = Collections.binarySearch(vertices, cent);
            Polygon.Builder builder = Polygon.newBuilder();
            builder = builder.setCentroidIdx(centIdx);
            polyVerts = polygon.getVertexList();
            for (int i = 1; i < polyVerts.size(); i++) {
                seg = new ca.mcmaster.cas.se2aa4.a2.components.Segment(polyVerts.get(i - 1)[0], polyVerts.get(i - 1)[1],
                        polyVerts.get(i)[0], polyVerts.get(i)[1]);
                segIdx = Collections.binarySearch(segments, seg);
                builder = builder.addSegmentIdxs(segIdx);
            }
            seg = new ca.mcmaster.cas.se2aa4.a2.components.Segment(polyVerts.get(0)[0], polyVerts.get(0)[1],
                    polyVerts.get(polyVerts.size() - 1)[0], polyVerts.get(polyVerts.size() - 1)[1]);
            segIdx = Collections.binarySearch(segments, seg);
            builder = builder.addSegmentIdxs(segIdx);
            for (double[] neighbourCentroid : polygon.getNeigbourList()) {
                cent = new ca.mcmaster.cas.se2aa4.a2.components.Vertex(neighbourCentroid[0], neighbourCentroid[1]);
                centIdx = Collections.binarySearch(vertices, cent);
                builder = builder.addNeighborIdxs(centIdx);
            }
            builder = builder.addProperties(colour);
            polygonList.add(builder.build());
        }
        return polygonList;
    }

    /**
     * Creates the low-level io.structs.Mesh structure for the given high-level Mesh structure.
     * @param mesh the high-level Mesh to convert.
     * @return the converted io.Structs.Mesh.
     */
    public static Mesh convert(final ca.mcmaster.cas.se2aa4.a2.mesh.Mesh mesh) {
        final List<ca.mcmaster.cas.se2aa4.a2.components.Vertex> vertices = mesh.getVertex();
        final List<ca.mcmaster.cas.se2aa4.a2.components.Segment> segments = mesh.getSegments();
        final List<ca.mcmaster.cas.se2aa4.a2.components.Polygon> polygons = mesh.getPolygons();
        List<Vertex> vertexList = createAllVertices(vertices);
        List<Segment> segmentList = createAllSegments(segments, vertices);
        List<Polygon> polygonList = createAllPolygons(polygons, segments, vertices);
        return Mesh.newBuilder().addAllPolygons(polygonList).addAllSegments(segmentList).addAllVertices(vertexList).build();
    }

}
