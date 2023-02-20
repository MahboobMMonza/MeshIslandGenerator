package ca.mcmaster.cas.se2aa4.a2.visualizer;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;

import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.List;

public class GraphicRenderer {

    private static final int THICKNESS = 3;
    private static final Stroke stroke = new BasicStroke(1f);

    public void render(Mesh aMesh, Graphics2D canvas) {
        Mesh(aMesh, canvas);
        Squarelines(aMesh, canvas);
    }

    private Color getAverageColor(Color c1, Color c2) {
        int redAverage, greenAverage, blueAverage;
        redAverage = (c1.getRed() + c2.getRed()) / 2;
        greenAverage = (c1.getGreen() + c2.getGreen()) / 2;
        blueAverage = (c1.getBlue() + c2.getBlue()) / 2;
        return new Color(redAverage, greenAverage, blueAverage);
    }
    private Color extractColor(List<Property> properties) {
        String val = null;
        for(Property p: properties) {
            if (p.getKey().equals("rgb_color")) {
                System.out.println(p.getValue());
                val = p.getValue();
            }
        }
        if (val == null)
            return Color.BLACK;
        String[] raw = val.split(",");
        int red = Integer.parseInt(raw[0]);
        int green = Integer.parseInt(raw[1]);
        int blue = Integer.parseInt(raw[2]);
        return new Color(red, green, blue);
    }

    private void Mesh(Mesh aMesh, Graphics2D canvas){
        canvas.setColor(Color.BLACK);
        canvas.setStroke(stroke);
        for (Segment s: aMesh.getSegmentsList()) {
            Color old = canvas.getColor();
            Color v1Color = extractColor(aMesh.getVertices(s.getV1Idx()).getPropertiesList());
            Color v2Color = extractColor(aMesh.getVertices(s.getV2Idx()).getPropertiesList());
            Color strokeColor = getAverageColor(v1Color, v2Color);
            canvas.setColor(strokeColor);
            Point2D p1 = new Point2D.Double(aMesh.getVertices(s.getV1Idx()).getX(), aMesh.getVertices(s.getV1Idx()).getY());
            Point2D p2 = new Point2D.Double(aMesh.getVertices(s.getV2Idx()).getX(), aMesh.getVertices(s.getV2Idx()).getY());
            Line2D line = new Line2D.Double(p1, p2);
            System.out.printf("[(%f, %f), (%f, %f)]%n",line.getX1(), line.getY1(), line.getX2(), line.getY2());
            canvas.draw(line);
            canvas.setColor(old);
        }
    }
    private void Squarelines(Mesh aMesh, Graphics2D canvas){
        canvas.setStroke(stroke);
        for (Vertex v: aMesh.getVerticesList()) {
            double centre_x = v.getX() - (THICKNESS/2.0d);
            double centre_y = v.getY() - (THICKNESS/2.0d);
            Color old = canvas.getColor();
            canvas.setColor(extractColor(v.getPropertiesList()));
            Ellipse2D point = new Ellipse2D.Double(centre_x, centre_y, THICKNESS, THICKNESS);
            canvas.fill(point);
            canvas.setColor(old);
        }
    }
}
