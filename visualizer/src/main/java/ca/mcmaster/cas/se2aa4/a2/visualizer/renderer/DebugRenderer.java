package ca.mcmaster.cas.se2aa4.a2.visualizer.renderer;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;

import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.*;

/**
 * DebugRenderer is a GraphicRenderer that renders components in debug mode.
 */
public class DebugRenderer extends GraphicRenderer {

    public static final Stroke DEFAULT_STROKE = new BasicStroke(1f);
    public static final Color DEFAULT_NEIGHBOR_COLOR = Color.GRAY;
    public static final Color DEFAULT_CENTROID_COLOR = Color.BLUE;

    @Override
    public void render(Mesh aMesh, Graphics2D canvas) {
        renderPolygons(aMesh, canvas);
        renderSegments(aMesh, canvas);
        renderNeighbours(aMesh, canvas);
        renderVertices(aMesh, canvas);
    }

    protected List<Line2D> getNeighbours(Mesh aMesh) {
        List<Line2D> neighbourSegments = new ArrayList<>();
        Set<Integer> visited = new TreeSet<>();
        for (Polygon p : aMesh.getPolygonsList()) {
            for (int idx : p.getNeighborIdxsList()) {
                if (visited.contains(idx)) {
                    continue;
                }
                Point2D p1 = new Point2D.Double(aMesh.getVertices(p.getCentroidIdx()).getX(),
                        aMesh.getVertices(p.getCentroidIdx()).getY());
                Point2D p2 = new Point2D.Double(aMesh.getVertices(idx).getX(),
                        aMesh.getVertices(idx).getY());
                Line2D line = new Line2D.Double(p1, p2);
                neighbourSegments.add(line);
            }
            visited.add(p.getCentroidIdx());
        }
        return neighbourSegments;
    }

    protected void renderNeighbours(Mesh aMesh, Graphics2D canvas) {
        canvas.setStroke(DEFAULT_STROKE);
        canvas.setColor(DEFAULT_NEIGHBOR_COLOR);
        List<Line2D> neighbours = getNeighbours(aMesh);
        for (Line2D neighbour : neighbours) {
            canvas.draw(neighbour);
        }
    }

    @Override
    protected float extractThickness(final Vertex vert) {
        return DEFAULT_VERTEX_THICKNESS;
    }

    @Override
    protected Stroke extractThickness(final Polygon poly) {
        return DEFAULT_POLYGON_BORDER_STROKE;
    }

    @Override
    protected Stroke extractThickness(final Segment seg) {
        return DEFAULT_SEGMENT_STROKE;
    }

    @Override
    protected Color extractColor(Polygon poly, String key) {
        if (key.equals("rgba_border_color")) {
            return DEFAULT_POLYGON_BORDER_COLOR;
        } else {
            return DEFAULT_POLYGON_FILL_COLOR;
        }
    }

    @Override
    protected Color extractColor(Segment seg) {
        return DEFAULT_SEGMENT_COLOR;
    }

    @Override
    protected Color extractColor(Vertex vert) {
        String centroidVal = new String();
        boolean centroid = false;
        for (Property p : vert.getPropertiesList()) {
            if (p.getKey().equals("centroid")) {
                centroidVal = p.getValue();
                break;
            }
        }
        centroid = Boolean.parseBoolean(centroidVal);
        return (centroid) ? DEFAULT_CENTROID_COLOR : DEFAULT_VERTEX_COLOR;
    }
}
