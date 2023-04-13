package ca.mcmaster.cas.se2aa4.a3.island.convertor;

import java.util.*;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.*;
import ca.mcmaster.cas.se2aa4.a3.island.components.*;

/**
 * Convertor
 */
public class Convertor {

    public static final float DEFAULT_THICKNESS = 0f, CITY_VERTEX_THICKNESS = 5f, ROAD_THICKNESS = 2f;
    public static final int TRANSPARENT_COLOUR = 0x00FFFFFF, CAPITAL_COLOUR = 0xFFFF0000, CITY_ROAD_COLOUR = 0xFF000000;

    private static List<Vertex> createAllVertices(ComponentCollections collection) {
        List<Vertex> vertices = new ArrayList<>();
        List<Point> points = new ArrayList<>(collection.getAllPoints().values());
        points.sort(Comparator.comparing(Point::getIndex));
        Vertex v;
        Property colour, thickness, centroid, capitalColour, cityColour, cityThickness;
        String rgba, thick, capitalRgba, cityRgba, cityThick;
        rgba = String.format("%08x", TRANSPARENT_COLOUR);
        thick = String.format("%.2f", DEFAULT_THICKNESS);
        capitalRgba = String.format("%08x", CAPITAL_COLOUR);
        cityRgba = String.format("%08x", CITY_ROAD_COLOUR);
        cityThick = String.format("%.2f", CITY_VERTEX_THICKNESS);
        colour = Property.newBuilder()
                .setKey("rgba_color")
                .setValue(rgba)
                .build();
        capitalColour = Property.newBuilder()
                .setKey("rgba_color")
                .setValue(capitalRgba)
                .build();
        cityColour = Property.newBuilder()
                .setKey("rgba_color")
                .setValue(cityRgba)
                .build();
        thickness = Property.newBuilder()
                .setKey("thickness")
                .setValue(thick)
                .build();
        cityThickness = Property.newBuilder()
                .setKey("thickness")
                .setValue(cityThick)
                .build();
        for (Point point : points) {
            centroid = Property.newBuilder()
                    .setKey("centroid")
                    .setValue(Boolean.toString(point.isCentroid()))
                    .build();
            v = Vertex.newBuilder()
                    .setX(point.getX())
                    .setY(point.getY())
                    .addProperties((point.isCapital()) ? capitalColour : (point.isCity()) ? cityColour : colour)
                    .addProperties((point.isCity()) ? cityThickness : thickness)
                    .addProperties(centroid)
                    .build();
            vertices.add(v);
        }
        return vertices;
    }

    private static List<Segment> createAllSegments(ComponentCollections collection) {
        List<Edge> edges = new ArrayList<>(collection.getAllEdges().values());
        List<Segment> segments = new ArrayList<>();
        Segment s;
        edges.sort(Comparator.comparing(Edge::getIndex));
        String rgba, thick;
        Property colour, thickness;
        for (Edge edge : edges) {
            rgba = String.format("%08x", edge.getColour());
            thick = String.format("%.2f", edge.getThickness());
            colour = Property.newBuilder()
                    .setKey("rgba_color")
                    .setValue(rgba)
                    .build();
            thickness = Property.newBuilder()
                    .setKey("thickness")
                    .setValue(thick)
                    .build();
            s = Segment.newBuilder()
                    .setV1Idx(edge.getV1Index())
                    .setV2Idx(edge.getV2Index())
                    .addProperties(colour)
                    .addProperties(thickness)
                    .build();
            segments.add(s);
        }
        rgba = String.format("%08x", CITY_ROAD_COLOUR);
        thick = String.format("%.2f", ROAD_THICKNESS);
        colour = Property.newBuilder()
                .setKey("rgba_color")
                .setValue(rgba)
                .build();
        thickness = Property.newBuilder()
                .setKey("thickness")
                .setValue(thick)
                .build();
        for (int[] centroidPair : collection.getRoads()) {
            s = Segment.newBuilder()
                    .setV1Idx(centroidPair[0])
                    .setV2Idx(centroidPair[1])
                    .addProperties(colour)
                    .addProperties(thickness)
                    .build();
            segments.add(s);
        }
        return segments;
    }

    private static List<Polygon> createAllPolygons(ComponentCollections collection) {
        List<Tile> tiles = new ArrayList<>(collection.getAllTiles().values());
        List<Polygon> polygons = new ArrayList<>();
        Polygon.Builder polyBuilder;
        String fillRGBA, borderRGBA, thick;
        Property fill, border, thickness;
        tiles.sort(Comparator.comparing(Tile::getIndex));
        borderRGBA = String.format("%08x", TRANSPARENT_COLOUR);
        thick = String.format("%.2f", DEFAULT_THICKNESS);
        border = Property.newBuilder()
                .setKey("rgba_border_color")
                .setValue(borderRGBA)
                .build();
        thickness = Property.newBuilder()
                .setKey("thickness")
                .setValue(thick)
                .build();
        for (Tile tile : tiles) {
            polyBuilder = Polygon.newBuilder();
            for (int neighIdx : tile.getNeighbourIdxs()) {
                polyBuilder.addNeighborIdxs(neighIdx);
            }
            for (int segIdx : tile.getEdgeIdxs()) {
                polyBuilder.addSegmentIdxs(segIdx);
            }
            fillRGBA = String.format("%08x", tile.getColour());
            fill = Property.newBuilder()
                    .setKey("rgba_fill_color")
                    .setValue(fillRGBA)
                    .build();
            polyBuilder.addProperties(thickness)
                    .addProperties(border)
                    .addProperties(fill)
                    .setCentroidIdx(tile.getIndex());
            polygons.add(polyBuilder.build());
        }
        return polygons;
    }

    public static Mesh convert(ComponentCollections collection, int height, int width) {
        List<Vertex> vertices = createAllVertices(collection);
        List<Segment> segments = createAllSegments(collection);
        List<Polygon> polygons = createAllPolygons(collection);
        Property meshHeight, meshWidth;
        meshHeight = Property.newBuilder()
                .setKey("height")
                .setValue(Integer.toString(height))
                .build();
        meshWidth = Property.newBuilder()
                .setKey("width")
                .setValue(Integer.toString(width))
                .build();
        return Mesh.newBuilder()
                .addAllPolygons(polygons)
                .addAllVertices(vertices)
                .addAllSegments(segments)
                .addProperties(meshHeight)
                .addProperties(meshWidth)
                .build();
    }
}
