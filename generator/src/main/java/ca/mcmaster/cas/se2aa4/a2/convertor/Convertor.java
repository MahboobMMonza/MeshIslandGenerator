package ca.mcmaster.cas.se2aa4.a2.convertor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ca.mcmaster.cas.se2aa4.a2.components.*;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.mesh.Mesh;

/**
 * Convertor class converts high-level {@link Mesh} into an equivalent
 * {@link Structs.Mesh}.
 */
public class Convertor {

    private static List<Structs.Vertex> createAllVertices(final List<Vert> vertices) {
        final List<Structs.Vertex> vertexList = new ArrayList<>();
        Structs.Property colour, thickness, centroid;
        int colours;
        String rgba, thick;
        for (final Vert vertex : vertices) {
            colours = vertex.getColour();
            rgba = String.format("%08x", colours);
            thick = String.format("%.2f", vertex.getThickness());
            colour = Structs.Property.newBuilder()
                    .setKey("rgba_color")
                    .setValue(rgba)
                    .build();
            thickness = Structs.Property.newBuilder()
                    .setKey("thickness")
                    .setValue(thick)
                    .build();
            centroid = Structs.Property.newBuilder()
                    .setKey("centroid")
                    .setValue(Boolean.toString(vertex.isCentroid()))
                    .build();
            vertexList.add(Structs.Vertex.newBuilder()
                    .setX(vertex.getX())
                    .setY(vertex.getY())
                    .addProperties(colour)
                    .addProperties(thickness)
                    .addProperties(centroid)
                    .build());
        }
        return vertexList;
    }

    private static List<Structs.Segment> createAllSegments(final List<Seg> segments, final List<Vert> vertices) {
        final List<Structs.Segment> segmentList = new ArrayList<>();
        Vertex idxVert;
        Structs.Property colour, thickness;
        int colours;
        int v1Idx, v2Idx;
        String rgba, thick;
        for (Seg segment : segments) {
            colours = segment.getColour();
            rgba = String.format("%08x", colours);
            thick = String.format("%.2f", segment.getThickness());
            colour = Structs.Property.newBuilder()
                    .setKey("rgba_color")
                    .setValue(rgba)
                    .build();
            thickness = Structs.Property.newBuilder()
                    .setKey("thickness")
                    .setValue(thick)
                    .build();
            idxVert = new Vertex(segment.getX1(), segment.getY1());
            v1Idx = Collections.binarySearch(vertices, idxVert);
            idxVert = new Vertex(segment.getX2(), segment.getY2());
            v2Idx = Collections.binarySearch(vertices, idxVert);
            segmentList.add(Structs.Segment.newBuilder()
                    .setV1Idx(v1Idx)
                    .setV2Idx(v2Idx)
                    .addProperties(colour)
                    .addProperties(thickness)
                    .build());
        }
        return segmentList;
    }

    private static List<Structs.Polygon> createAllPolygons(final List<Poly> polygons, final List<Seg> segments,
            final List<Vert> vertices) {
        final List<Structs.Polygon> polygonList = new ArrayList<>();
        Vertex cent;
        Segment seg;
        List<double[]> polyVerts;
        Structs.Property fillColour, borderColour, thickness;
        int colours;
        String rgba, thick;
        int centIdx, segIdx;
        for (Poly polygon : polygons) {
            colours = polygon.getFillColour();
            rgba = String.format("%08x", colours);
            fillColour = Structs.Property.newBuilder()
                    .setKey("rgba_fill_color")
                    .setValue(rgba)
                    .build();
            colours = polygon.getBorderColour();
            rgba = String.format("%08x", colours);
            borderColour = Structs.Property.newBuilder()
                    .setKey("rgba_border_color")
                    .setValue(rgba)
                    .build();
            thick = String.format("%.2f", polygon.getBorderThickness());
            thickness = Structs.Property.newBuilder()
                    .setKey("thickness")
                    .setValue(thick)
                    .build();
            cent = new Vertex(polygon.getCentroidX(), polygon.getCentroidY());
            centIdx = Collections.binarySearch(vertices, cent);
            Structs.Polygon.Builder builder = Structs.Polygon.newBuilder();
            builder = builder.setCentroidIdx(centIdx);
            polyVerts = polygon.getVertexList();
            for (int i = 1; i < polyVerts.size(); i++) {
                seg = new Segment(polyVerts.get(i - 1)[0], polyVerts.get(i - 1)[1],
                        polyVerts.get(i)[0], polyVerts.get(i)[1]);
                segIdx = Collections.binarySearch(segments, seg);
                builder = builder.addSegmentIdxs(segIdx);
            }
            // seg = new Segment(polyVerts.get(0)[0], polyVerts.get(0)[1],
            // polyVerts.get(polyVerts.size() - 1)[0], polyVerts.get(polyVerts.size() -
            // 1)[1]);
            // segIdx = Collections.binarySearch(segments, seg);
            // builder = builder.addSegmentIdxs(segIdx);
            for (double[] neighbourCentroid : polygon.getNeigbourList()) {
                cent = new Vertex(neighbourCentroid[0], neighbourCentroid[1]);
                centIdx = Collections.binarySearch(vertices, cent);
                builder = builder.addNeighborIdxs(centIdx);
            }
            builder = builder
                    .addProperties(fillColour)
                    .addProperties(borderColour)
                    .addProperties(thickness);
            polygonList.add(builder.build());
        }
        return polygonList;
    }

    /**
     * Creates the low-level io.structs.Mesh structure for the given high-level Mesh
     * structure.
     *
     * @param mesh the high-level Mesh to convert.
     * @return the converted io.Structs.Mesh.
     */
    public static Structs.Mesh convert(final Mesh mesh) {
        final List<Vert> vertices = mesh.getVerts();
        final List<Seg> segments = mesh.getSegs();
        final List<Poly> polygons = mesh.getPolys();
        List<Structs.Vertex> vertexList = createAllVertices(vertices);
        List<Structs.Segment> segmentList = createAllSegments(segments, vertices);
        List<Structs.Polygon> polygonList = createAllPolygons(polygons, segments, vertices);
        Structs.Property height, width;
        height = Structs.Property.newBuilder()
                .setKey("height")
                .setValue(Integer.toString(mesh.getHeight()))
                .build();
        width = Structs.Property.newBuilder()
                .setKey("width")
                .setValue(Integer.toString(mesh.getWidth()))
                .build();
        return Structs.Mesh.newBuilder()
                .addAllPolygons(polygonList)
                .addAllSegments(segmentList)
                .addAllVertices(vertexList)
                .addProperties(height)
                .addProperties(width)
                .build();
    }

}