package ca.mcmaster.cas.se2aa4.a2.visualizer;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;

import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class GraphicRenderer {

    private static final float DEFAULT_THICKNESS = 1;
    private static final Stroke DEFAULT_STROKE = new BasicStroke(1f);
    private static final Color DEFAULT_COLOR = Color.BLACK;

    public void render(Mesh aMesh, Graphics2D canvas) {
        renderPolygons(aMesh, canvas);
        renderSegments(aMesh, canvas);
        renderVertices(aMesh, canvas);
    }

    private class RadialVertexComparator implements Comparator<Vertex> {

        private Vertex anchor;

        RadialVertexComparator(Vertex anchor) {
            setAnchor(anchor);
        }

        void setAnchor(Vertex anchor) {
            this.anchor = anchor;
        }

        @Override
        public int compare(Vertex v1, Vertex v2) {
            // Determine if the orientation of these 2 points with the anchor is
            // counterclockwise
            // if it is, then v1 comes before v2
            double angle1 = Math.atan2(v1.getY() - anchor.getY(), v1.getX() - anchor.getX()) + 0.0;
            double angle2 = Math.atan2(v2.getY() - anchor.getY(), v2.getX() - anchor.getX()) + 0.0;
            if (angle1 < 0) {
                angle1 = Math.PI * 2 + angle1;
            }
            if (angle2 < 0) {
                angle2 = Math.PI * 2 + angle2;
            }
            return Double.compare(angle1, angle2);
        }
    }

    private void renderSegments(Mesh aMesh, Graphics2D canvas) {
        canvas.setColor(DEFAULT_COLOR);
        canvas.setStroke(DEFAULT_STROKE);
        for (Segment s : aMesh.getSegmentsList()) {
            Color oldColor = canvas.getColor();
            Color strokeColor = extractColor(s.getPropertiesList());
            Stroke oldStroke = canvas.getStroke();
            Stroke stroke = extractThickness(s.getPropertiesList());
            canvas.setColor(strokeColor);
            canvas.setStroke(stroke);
            Point2D p1 = new Point2D.Double(aMesh.getVertices(s.getV1Idx()).getX(),
                    aMesh.getVertices(s.getV1Idx()).getY());
            Point2D p2 = new Point2D.Double(aMesh.getVertices(s.getV2Idx()).getX(),
                    aMesh.getVertices(s.getV2Idx()).getY());
            Line2D line = new Line2D.Double(p1, p2);
            canvas.draw(line);
            canvas.setColor(oldColor);
            canvas.setStroke(oldStroke);
        }
    }

    private void renderVertices(Mesh aMesh, Graphics2D canvas) {
        canvas.setStroke(DEFAULT_STROKE);
        for (Vertex v : aMesh.getVerticesList()) {
            double thickness = (double) extractVertexThickness(v.getPropertiesList());
            double centre_x = v.getX() - (thickness / 2.0d);
            double centre_y = v.getY() - (thickness / 2.0d);
            Color oldColor = canvas.getColor();
            canvas.setColor(extractColor(v.getPropertiesList()));
            if (thickness == 0.0 || canvas.getColor().getAlpha() == 0)
                continue;
            Ellipse2D point = new Ellipse2D.Double(centre_x, centre_y, thickness, thickness);
            canvas.fill(point);
            canvas.setColor(oldColor);
        }
    }

    private List<Vertex> calculatePolyPath(final Mesh aMesh, final Polygon p) {
        Set<Vertex> pathVtx = new TreeSet<>(
                Comparator.comparingDouble(Vertex::getX).thenComparing(Vertex::getY));
        for (int segIdx : p.getSegmentIdxsList()) {
            pathVtx.add(aMesh.getVertices(aMesh.getSegments(segIdx).getV1Idx()));
            pathVtx.add(aMesh.getVertices(aMesh.getSegments(segIdx).getV2Idx()));
        }
        List<Vertex> path = new ArrayList<>(pathVtx);
        Collections.sort(path, new RadialVertexComparator(aMesh.getVertices(p.getCentroidIdx())));
        return path;
    }

    private void renderPolygons(Mesh aMesh, Graphics2D canvas) {
        canvas.setStroke(DEFAULT_STROKE);
        Path2D poly;
        boolean firstPoint;
        for (Polygon p : aMesh.getPolygonsList()) {
            poly = new Path2D.Double();
            firstPoint = false;
            List<Vertex> path = calculatePolyPath(aMesh, p);
            for (Vertex v : path) {
                if (!firstPoint) {
                    poly.moveTo(v.getX(), v.getY());
                    firstPoint = true;
                } else {
                    poly.lineTo(v.getX(), v.getY());
                }
            }
            poly.closePath();
            Color oldColor = canvas.getColor();
            canvas.setColor(extractColor(p.getPropertiesList()));
            canvas.fill(poly);
            canvas.setColor(oldColor);
        }
    }

    private float extractVertexThickness(List<Property> properties) {
        String val = null;
        for (Property p : properties) {
            if (p.getKey().equals("thickness")) {
                System.out.println(p.getValue());
                val = p.getValue();
            }
        }
        if (val != null) {
            return Float.parseFloat(val);
        }
        return DEFAULT_THICKNESS;
    }

    private Stroke extractThickness(List<Property> properties) {
        String val = null;
        for (Property p : properties) {
            if (p.getKey().equals("thickness")) {
                System.out.println(p.getValue());
                val = p.getValue();
            }
        }
        if (val == null) {
            return DEFAULT_STROKE;
        }
        float f = Float.parseFloat(val);
        return new BasicStroke(f);
    }

    private Color extractColor(List<Property> properties) {
        String val = null;
        for (Property p : properties) {
            if (p.getKey().equals("rgba_color")) {
                System.out.println(p.getValue());
                val = p.getValue();
            }
        }
        if (val == null) {
            return DEFAULT_COLOR;
        }
        String[] raw = val.split(",");
        int red = Integer.parseInt(raw[0]);
        int green = Integer.parseInt(raw[1]);
        int blue = Integer.parseInt(raw[2]);
        int alpha = Integer.parseInt(raw[3]);
        return new Color(red, green, blue, alpha);
    }

}
