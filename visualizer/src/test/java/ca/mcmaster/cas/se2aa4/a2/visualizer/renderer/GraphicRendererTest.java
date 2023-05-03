package ca.mcmaster.cas.se2aa4.a2.visualizer.renderer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import java.util.*;
import java.awt.Color;
import java.awt.Stroke;
import java.awt.BasicStroke;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.*;

/**
 * GraphicRendererTest
 */
public class GraphicRendererTest {

    static List<Vertex> vertices;
    static List<List<Vertex>> pathVerts;
    static List<Segment> segments;
    static List<Polygon> polygons;
    static List<Color> vertColors, segColors, polyColors;
    static List<Stroke> segStrokes, polyStrokes;
    static List<Float> vertStrokes;
    static List<Boolean> vertCentroids;
    static Mesh aMesh;
    static GraphicRenderer rend;

    private static void addAllVertices() {
        vertices = new ArrayList<>();
        vertColors = new ArrayList<>();
        vertStrokes = new ArrayList<>();
        vertCentroids = new ArrayList<>();
        Property colour, thickness, centroid;
        centroid = Property.newBuilder().setKey("centroid").setValue("false").build();
        colour = Property.newBuilder().setKey("rgba_color").setValue("FFAABB11").build();
        thickness = Property.newBuilder().setKey("thickness").setValue("1.25").build();
        vertCentroids.add(false);
        vertColors.add(new Color(0xFFAABB11, true));
        vertStrokes.add(1.25f);
        vertices.add(Vertex.newBuilder()
                .setX(1.0)
                .setY(1.0)
                .addProperties(centroid)
                .addProperties(colour)
                .addProperties(thickness)
                .build());
        colour = colour.toBuilder().setValue("FFAABBCC").build();
        thickness = thickness.toBuilder().setValue("1.50").build();
        vertStrokes.add(1.50f);
        vertColors.add(new Color(0xFFAABBCC, true));
        vertCentroids.add(false);
        vertices.add(Vertex.newBuilder()
                .setX(3.0)
                .setY(4.0)
                .addProperties(centroid)
                .addProperties(colour)
                .addProperties(thickness)
                .build());
        colour = colour.toBuilder().setValue("FF00BBCC").build();
        thickness = thickness.toBuilder().setValue("2.0").build();
        vertColors.add(new Color(0xFF00BBCC, true));
        vertStrokes.add(2.0f);
        vertCentroids.add(false);
        vertices.add(Vertex.newBuilder()
                .setX(6.0)
                .setY(2.0)
                .addProperties(centroid)
                .addProperties(colour)
                .addProperties(thickness)
                .build());
        colour = colour.toBuilder().setValue("80808080").build();
        thickness = thickness.toBuilder().setValue("1.0").build();
        vertColors.add(new Color(0x80808080, true));
        vertStrokes.add(1.0f);
        vertCentroids.add(false);
        vertices.add(Vertex.newBuilder()
                .setX(5.0)
                .setY(-1.0)
                .addProperties(centroid)
                .addProperties(colour)
                .addProperties(thickness)
                .build());
        colour = colour.toBuilder().setValue("400000FF").build();
        thickness = thickness.toBuilder().setValue("3.0").build();
        vertStrokes.add(3.0f);
        vertColors.add(new Color(0x400000FF, true));
        vertCentroids.add(false);
        vertices.add(Vertex.newBuilder()
                .setX(3.0)
                .setY(-3.0)
                .addProperties(centroid)
                .addProperties(colour)
                .addProperties(thickness)
                .build());
        colour = colour.toBuilder().setValue("00000000").build();
        thickness = thickness.toBuilder().setValue("3.0").build();
        centroid = centroid.toBuilder().setValue("true").build();
        vertColors.add(new Color(0x00000000, true));
        vertStrokes.add(3.0f);
        vertCentroids.add(true);
        vertices.add(Vertex.newBuilder()
                .setX(3.6)
                .setY(0.6)
                .addProperties(centroid)
                .addProperties(colour)
                .addProperties(thickness)
                .build());
        colour = colour.toBuilder().setValue("400000FF").build();
        thickness = thickness.toBuilder().setValue("3.0").build();
        vertStrokes.add(3.0f);
        vertColors.add(new Color(0x400000FF, true));
        vertCentroids.add(false);
        vertices.add(Vertex.newBuilder()
                .setX(6.0)
                .setY(6.0)
                .addProperties(centroid)
                .addProperties(colour)
                .addProperties(thickness)
                .build());
        colour = colour.toBuilder().setValue("400000FF").build();
        thickness = thickness.toBuilder().setValue("3.0").build();
        vertStrokes.add(3.0f);
        vertColors.add(new Color(0x400000FF, true));
        vertCentroids.add(false);
        vertices.add(Vertex.newBuilder()
                .setX(9.0)
                .setY(4.0)
                .addProperties(centroid)
                .addProperties(colour)
                .addProperties(thickness)
                .build());
        colour = colour.toBuilder().setValue("00000000").build();
        thickness = thickness.toBuilder().setValue("3.0").build();
        centroid = centroid.toBuilder().setValue("true").build();
        vertColors.add(new Color(0x00000000, true));
        vertStrokes.add(3.0f);
        vertCentroids.add(true);
        vertices.add(Vertex.newBuilder()
                .setX(6)
                .setY(4)
                .addProperties(centroid)
                .addProperties(colour)
                .addProperties(thickness)
                .build());
    }

    private static void addAllSegments() {
        segments = new ArrayList<>();
        segColors = new ArrayList<>();
        segStrokes = new ArrayList<>();
        Property colour, thickness;
        colour = Property.newBuilder().setKey("rgba_color").setValue("FFFFFFFF").build();
        thickness = Property.newBuilder().setKey("thickness").setValue("3.0").build();
        segColors.add(new Color(0xFFFFFFFF, true));
        segStrokes.add(new BasicStroke(3f));
        segments.add(Segment.newBuilder()
                .setV1Idx(0)
                .setV2Idx(1)
                .addProperties(colour)
                .addProperties(thickness)
                .build());
        colour = colour.toBuilder().setValue("AAAAAAAA").build();
        thickness = thickness.toBuilder().setValue("2.0").build();
        segColors.add(new Color(0xAAAAAAAA, true));
        segStrokes.add(new BasicStroke(2f));
        segments.add(Segment.newBuilder()
                .setV1Idx(1)
                .setV2Idx(2)
                .addProperties(colour)
                .addProperties(thickness)
                .build());
        colour = colour.toBuilder().setValue("BBBBBBBB").build();
        thickness = thickness.toBuilder().setValue("1.0").build();
        segColors.add(new Color(0xBBBBBBBB, true));
        segStrokes.add(new BasicStroke(1f));
        segments.add(Segment.newBuilder()
                .setV1Idx(2)
                .setV2Idx(3)
                .addProperties(colour)
                .addProperties(thickness)
                .build());
        colour = colour.toBuilder().setValue("EEEEEEEE").build();
        thickness = thickness.toBuilder().setValue("4.0").build();
        segColors.add(new Color(0xEEEEEEEE, true));
        segStrokes.add(new BasicStroke(4f));
        segments.add(Segment.newBuilder()
                .setV1Idx(3)
                .setV2Idx(4)
                .addProperties(colour)
                .addProperties(thickness)
                .build());
        colour = colour.toBuilder().setValue("CCCCCCCC").build();
        thickness = thickness.toBuilder().setValue("1.50").build();
        segColors.add(new Color(0xCCCCCCCC, true));
        segStrokes.add(new BasicStroke(1.50f));
        segments.add(Segment.newBuilder()
                .setV1Idx(4)
                .setV2Idx(0)
                .addProperties(colour)
                .addProperties(thickness)
                .build());
        segColors.add(new Color(0xCCCCCCCC, true));
        segStrokes.add(new BasicStroke(1.50f));
        segments.add(Segment.newBuilder()
                .setV1Idx(1)
                .setV2Idx(6)
                .addProperties(colour)
                .addProperties(thickness)
                .build());
        segColors.add(new Color(0xCCCCCCCC, true));
        segStrokes.add(new BasicStroke(1.50f));
        segments.add(Segment.newBuilder()
                .setV1Idx(7)
                .setV2Idx(6)
                .addProperties(colour)
                .addProperties(thickness)
                .build());
        segColors.add(new Color(0xCCCCCCCC, true));
        segStrokes.add(new BasicStroke(1.50f));
        segments.add(Segment.newBuilder()
                .setV1Idx(7)
                .setV2Idx(2)
                .addProperties(colour)
                .addProperties(thickness)
                .build());
    }

    private static void addAllPolygons() {
        polygons = new ArrayList<>();
        polyColors = new ArrayList<>();
        polyStrokes = new ArrayList<>();
        pathVerts = new ArrayList<>();
        Property fill, border, thickness;
        fill = Property.newBuilder().setKey("rgba_fill_color").setValue("80FF00FF").build();
        border = Property.newBuilder().setKey("rgba_border_color").setValue("FF00FFFF").build();
        thickness = Property.newBuilder().setKey("thickness").setValue("0.75").build();
        polyColors.add(new Color(0x80ff00ff, true));
        polyColors.add(new Color(0xff00ffff, true));
        polyStrokes.add(new BasicStroke(0.75f));
        polygons.add(Polygon.newBuilder()
                .addProperties(fill)
                .addProperties(border)
                .addProperties(thickness)
                .setCentroidIdx(5)
                .addAllSegmentIdxs(List.of(4, 1, 0, 2, 3))
                .build());
        polyColors.add(new Color(0x80ff00ff, true));
        polyColors.add(new Color(0xff00ffff, true));
        polyStrokes.add(new BasicStroke(0.75f));
        polygons.add(Polygon.newBuilder()
                .addProperties(fill)
                .addProperties(border)
                .addProperties(thickness)
                .setCentroidIdx(8)
                .addAllSegmentIdxs(List.of(7, 1, 6, 5))
                .build());
        pathVerts.add(List.of(vertices.get(2), vertices.get(1), vertices.get(0), vertices.get(4), vertices.get(3)));
        pathVerts.add(List.of(vertices.get(7), vertices.get(6), vertices.get(1), vertices.get(2)));
    }

    @BeforeAll
    public static void setup() {
        addAllVertices();
        addAllSegments();
        addAllPolygons();
        aMesh = Mesh.newBuilder()
                .addAllVertices(vertices)
                .addAllPolygons(polygons)
                .addAllSegments(segments)
                .build();
        rend = new GraphicRenderer();
    }

    @Test
    public void extractVertexThickness_AllMatchSucceeds() {
        List<Float> extracted = new ArrayList<>();
        for (Vertex v : vertices) {
            extracted.add(rend.extractThickness(v));
        }
        assertEquals(vertStrokes, extracted);
    }

    @Test
    public void extractVertexColor_AllMatchSucceeds() {
        List<Color> extracted = new ArrayList<>();
        for (Vertex v : vertices) {
            extracted.add(rend.extractColor(v));
        }
        assertEquals(vertColors, extracted);
    }

    @Test
    public void extractSegmentColor_AllMatchSucceeds() {
        List<Color> extracted = new ArrayList<>();
        for (Segment seg : segments) {
            extracted.add(rend.extractColor(seg));
        }
        assertEquals(segColors, extracted);
    }

    @Test
    public void extractSegmentThickness_AllMatchSucceeds() {
        List<Stroke> extracted = new ArrayList<>();
        for (Segment seg : segments) {
            extracted.add(rend.extractThickness(seg));
        }
        assertEquals(segStrokes, extracted);
    }

    @Test
    public void extractPolygonThickness_AllMatchSucceeds() {
        List<Stroke> extracted = new ArrayList<>();
        for (Polygon p : polygons) {
            extracted.add(rend.extractThickness(p));
        }
        assertEquals(polyStrokes, extracted);
    }

    @Test
    public void extractPolygonColour_AllMatchSucceeds() {
        List<Color> extracted = new ArrayList<>();
        for (Polygon p : polygons) {
            extracted.add(rend.extractColor(p, "rgba_fill_color"));
            extracted.add(rend.extractColor(p, "rgba_border_color"));
        }
        assertEquals(polyColors, extracted);
    }

    @Test
    public void calculatePolyPath_CorrectOrderSucceeds() {
        List<List<Vertex>> path = new ArrayList<>();
        for (Polygon p : polygons) {
            path.add(rend.calculatePolyPath(aMesh, p));
        }
        assertAll(
                "Polygon Path Assertions",
                () -> assertEquals(pathVerts.get(0), path.get(0), "First polygon path"),
                () -> assertEquals(pathVerts.get(1), path.get(1), "Right angled from centroid polygon path"));
    }
}
