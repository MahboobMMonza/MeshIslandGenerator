package ca.mcmaster.cas.se2aa4.a2.generator;

import java.util.ArrayList;
import java.util.List;

import ca.mcmaster.cas.se2aa4.a2.components.Polygon;
import ca.mcmaster.cas.se2aa4.a2.components.Segment;
import ca.mcmaster.cas.se2aa4.a2.components.Vertex;
import ca.mcmaster.cas.se2aa4.a2.mesh.Mesh;

/**
 * GridGenerator
 */
public class GridGenerator implements Generator {

    private static final double TOP_X = 0, TOP_Y = 0;

    @Override
    public void generate(final Mesh mesh, final int height, final int width, final int n) {
        final double increment = Math.round(n / 2.0 * 100) / 100.0;
        // Find the number of squares that at least partially fit inside the canvas
        // area, and calculate the centroid bounds
        final double bottomCentX = (Math.ceil(height / (n + 0.0)) * n) - increment;
        final double bottomCentY = (Math.ceil(width / (n + 0.0)) * n) - increment;
        final List<double[]> allCentroids = generateCentroids(increment, bottomCentX, bottomCentY);
        // Create a list of Polygons using the generateCentroids
        final List<Polygon> allPolys = generatePolygons(allCentroids);
        // Create the vertices for all Polygons in the list in a radial order
        populateVertices(allPolys, increment);
        // Create the neighbours for all Polygons in the list
        populateNeighbours(allPolys, increment, bottomCentX, bottomCentY);
        // Add all components to the mesh from the generated polygons
        populateAllComponents(mesh, allPolys);
    }

    private void populateAllComponents(final Mesh mesh, final List<Polygon> polygons) {
        Vertex v;
        Segment s;
        List<double[]> vertices;
        for (Polygon polygon : polygons) {
            vertices = polygon.getVertexList();
            v = new Vertex();
            v.setPosition(vertices.get(0)[0], vertices.get(0)[1]);
            mesh.addVertex(v);
            for (int i = 1; i < vertices.size(); i++) {
                v = new Vertex();
            }
        }
    }

    private void populateNeighbours(final List<Polygon> polygons, final double increment, final double bottomCentX,
            final double bottomCentY) {
        List<double[]> neighbourCentroids;
        double x, y;
        for (final Polygon poly : polygons) {
            neighbourCentroids = new ArrayList<>();
            // Use math to calculate each neighbouring centroid's index
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    // Calculate the current centroid index
                    x = poly.getCentroidX() + (2 * i * increment);
                    y = poly.getCentroidY() + (2 * j * increment);
                    // Add the calculated centroid position iff it is in the bounds and not the
                    // current centroid
                    if (x >= TOP_X && y >= TOP_Y && x != poly.getCentroidX() && y != poly.getCentroidY()
                            && x <= bottomCentX && y <= bottomCentY) {
                        neighbourCentroids.add(new double[] { x, y });
                    }
                }
            }
        }
    }

    private void populateVertices(final List<Polygon> polygons, final double increment) {
        List<double[]> vertices;
        for (final Polygon polygon : polygons) {
            vertices = new ArrayList<>();
            vertices.add(new double[] { polygon.getCentroidX() + increment, polygon.getCentroidY() - increment });
            vertices.add(new double[] { polygon.getCentroidX() - increment, polygon.getCentroidY() - increment });
            vertices.add(new double[] { polygon.getCentroidX() - increment, polygon.getCentroidY() + increment });
            vertices.add(new double[] { polygon.getCentroidX() + increment, polygon.getCentroidY() + increment });
            polygon.setVertices(vertices);
        }
    }

    private List<Polygon> generatePolygons(final List<double[]> centroids) {
        final List<Polygon> polygons = new ArrayList<>();
        Polygon p;
        for (final double[] centroid : centroids) {
            p = new Polygon();
            p.setCentroid(centroid[0], centroid[1]);
        }
        return polygons;
    }

    /**
     * Generates a list of XY-pairs with all of the centroids in the current mesh.
     *
     * @param increment   the shortest distance between the edge and the centroid
     *                    of the square.
     * @param bottomCentX The last x-value for a valid centroid in case of
     *                    overflow.
     * @param bottomCentY The last y-value for a valid centroid in case of
     *                    overflow.
     * @return a list of XY-pairs stored as double[] representing the coordinates of
     *         each centroid.
     */
    private List<double[]> generateCentroids(final double increment, final double bottomCentX,
            final double bottomCentY) {
        final List<double[]> centroids = new ArrayList<>();
        for (double x = TOP_X + increment; x < bottomCentX; x += 2 * increment) {
            for (double y = TOP_Y + increment; y < bottomCentY; y += 2 * increment) {
                centroids.add(new double[] { x, y });
            }
        }
        return centroids;
    }

}
