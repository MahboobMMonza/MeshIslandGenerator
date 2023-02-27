package ca.mcmaster.cas.se2aa4.a2.generator;

import org.locationtech.jts.geom.*;
import org.locationtech.jts.triangulate.*;

import ca.mcmaster.cas.se2aa4.a2.components.Vertex;
import ca.mcmaster.cas.se2aa4.a2.components.Vert;
import ca.mcmaster.cas.se2aa4.a2.components.Segment;
import ca.mcmaster.cas.se2aa4.a2.components.Seg;
import ca.mcmaster.cas.se2aa4.a2.components.Poly;
import org.locationtech.jts.algorithm.ConvexHull;

import ca.mcmaster.cas.se2aa4.a2.mesh.Mesh;
import java.util.*;

public class VoronoiGenerator implements Generator {

    private class IntermediateVertexComparator implements Comparator<double[]> {
        @Override
        public int compare(double[] vertex1, double[] vertex2) {
            int comp = Double.compare(vertex1[0], vertex2[0]);
            if (comp != 0) {
                return comp;
            }
            return Double.compare(vertex1[1], vertex2[1]);
        }
    }

    final static PrecisionModel HUNDREDTH_PRECISION_MODEL = new PrecisionModel(100);
    final static double NINE_TENTHS = 0.9;
    public final static int MIN_NUM_POINTS = 20, MIN_RELAXATION_LEVEL = 1;

    static void warnUser(int givenValue, int defaultValue, String subject) {
        if (givenValue < defaultValue) {
            System.out.println(String.format(
                    "WARNING: The given value of %1$s was set below the minimum value of %2$s for argument %3$s. Setting %3$s to default value %2$s",
                    givenValue, defaultValue, subject));
        }

    }

    private final Random randomX, randomY;

    private final int numPoints, relaxationLevel;

    public VoronoiGenerator(int numPoints, int relaxationLevel) {
        this.numPoints = Math.max(numPoints, MIN_NUM_POINTS);
        this.relaxationLevel = Math.max(relaxationLevel, MIN_RELAXATION_LEVEL);
        randomX = new Random();
        randomY = new Random();
        warnUser(numPoints, MIN_NUM_POINTS, "NUMBER POINTS");
        warnUser(relaxationLevel, MIN_RELAXATION_LEVEL, "RELAXATION LEVEL");
    }

    public VoronoiGenerator(int numPoints, int relaxationLevel, long seedX, long seedY) {
        this.numPoints = Math.max(numPoints, MIN_NUM_POINTS);
        this.relaxationLevel = Math.max(relaxationLevel, MIN_RELAXATION_LEVEL);
        randomX = new Random(seedX);
        randomY = new Random(seedY);
        warnUser(numPoints, MIN_NUM_POINTS, "NUMBER POINTS");
        warnUser(relaxationLevel, MIN_RELAXATION_LEVEL, "RELAXATION LEVEL");
    }

    @Override
    public void generate(final Mesh mesh) {
        // Create the initial bounded voronoi diagram with random points and get the
        // polygons
        List<Polygon> voronoiPolygons = createVoronoiPolygons(randomCoords(mesh.getHeight(), mesh.getWidth()),
                mesh.getHeight(), mesh.getWidth());
        // Apply Lloyd Relaxation to the initial diagram as specified by the relaxation
        // level
        voronoiPolygons = lloydRelax(voronoiPolygons, mesh.getHeight(), mesh.getWidth());
        // Compute Delaunay triangulation and get the corresponding polygons, whose
        // edges represent neighbourhood relations between centroids
        List<Polygon> delaunayPoly = delaunayGen(
                getVoronoiCentroids(voronoiPolygons));

        List<Poly> finalPolys = convertPolygons(voronoiPolygons, delaunayPoly);

        addAllComponents(mesh, finalPolys);
    }

    /**
     * Creates the Voronoi Diagram and returns its polygons based on the coordinates
     * fed and crops it based
     * on height and width parameters
     *
     * @param coords the list of coordinates to create the voronoi
     * @param height the height of the mesh size (to restrict size of voronoi
     *               diagram)
     * @param width  the the width of the mesh size (to restrict size of voronoi
     *               diagram)
     * @return the list of polygons in the Voronoi Diagram
     */

    public List<Polygon> createVoronoiPolygons(List<Coordinate> coords, int height, int width) {
        GeometryFactory geomfactory = new GeometryFactory(HUNDREDTH_PRECISION_MODEL);
        VoronoiDiagramBuilder voronoiBuilder = new VoronoiDiagramBuilder();
        Envelope boundedDiagram = new Envelope(0, width, 0, height);
        voronoiBuilder.setClipEnvelope(boundedDiagram);
        voronoiBuilder.setSites(coords);

        Geometry createdPolys = voronoiBuilder.getDiagram(geomfactory);
        // Crop the Geometry
        createdPolys = createdPolys.intersection(new GeometryFactory().toGeometry(boundedDiagram));
        List<Polygon> polygonList = new ArrayList<>();

        GeometryCollection geomCollection = (GeometryCollection) createdPolys;
        for (int i = 0; i < geomCollection.getNumGeometries(); i++) {
            // Add the ith item in the collection to polygonList if the ith item is a
            // polygon
            if (geomCollection.getGeometryN(i) instanceof Polygon) {
                Polygon polygon = (Polygon) geomCollection.getGeometryN(i);
                polygonList.add(polygon);
            }
        }
        // Collections.sort(polygonList, new PolygonComparator());
        return polygonList;
    }

    /**
     * Computes lloyd relaxtion and returns the list of relaxed polygons
     * 
     *
     * @param initialVoronoiPolygons the unrelaxed list of polygons
     * @param height                 the height of the given mesh
     * @param width                  the width of the given mesh
     * @return the relaxed list of polygons
     */

    List<Polygon> lloydRelax(final List<Polygon> initialVoronoiPolygons, final int height, final int width) {
        List<Coordinate> centroidCoordinates;
        List<Polygon> relaxedPolygons = initialVoronoiPolygons;
        for (int i = 0; i < relaxationLevel; i++) {
            centroidCoordinates = getVoronoiCentroids(relaxedPolygons);
            relaxedPolygons = createVoronoiPolygons(centroidCoordinates, height, width);
        }
        return relaxedPolygons;
    }

    /**
     * Adds all the necessary components generated by the polygon to the given mesh
     * 
     * @param mesh     the Mesh to add all components to.
     * @param polygons the list of Polygons from which all relevant components are
     *                 generated.
     */
    void addAllComponents(final Mesh mesh, final List<Poly> polygons) {
        Vert v;
        Seg s;
        List<double[]> vertices;
        for (Poly p : polygons) {
            vertices = p.getVertexList();
            // Add the first vertex of this polygon
            v = new Vertex(vertices.get(0)[0], vertices.get(0)[1]);
            mesh.addVert(v);
            // Add remaining vertices, and since they are ordered radially, they are
            // adjacent to the vertices before and after them.
            // As a result, a segment can be made with this vertex and the vertex before, so
            // add that as well.
            for (int i = 1; i < vertices.size(); i++) {
                v = new Vertex(vertices.get(i)[0], vertices.get(i)[1]);
                mesh.addVert(v);
                s = new Segment(vertices.get(i - 1)[0], vertices.get(i - 1)[1], vertices.get(i)[0], vertices.get(i)[1]);
                mesh.addSeg(s);
            }
            // Add the segment that connects the first vertex with the last one.
            s = new Segment(vertices.get(0)[0], vertices.get(0)[1], vertices.get(vertices.size() - 1)[0],
                    vertices.get(vertices.size() - 1)[1]);
            mesh.addSeg(s);
            mesh.addPoly(p);
        }
    }

    /**
     * Generates random coordinates given the size of the mesh
     * 
     * 
     * @param height the height of the given mesh
     * @param width  the width of the given mesh
     * @return a list of randomly generated coordinates with a size equal to the
     *         value of this numPoints
     */

    List<Coordinate> randomCoords(final int height, final int width) {
        List<Coordinate> coords = new ArrayList<>();

        for (int i = 1; i < numPoints; i++) {
            int pointX = randomX.nextInt(width);
            int pointY = randomY.nextInt(height);
            coords.add(new Coordinate(pointX, pointY));
        }
        return coords;
    }

    /**
     * Gets the centroids from the given list of polygons
     * 
     * @param polygons the list of polygons
     * @return the list of centroids for the given list of polygons
     */
    List<Coordinate> getVoronoiCentroids(final List<Polygon> polygons) {
        List<Coordinate> centroids = new ArrayList<>();
        for (Polygon p : polygons) {
            centroids.add(new Coordinate(p.getCentroid().getX(), p.getCentroid().getY()));
        }
        return centroids;
    }

    /**
     * Computes the Delaunay Triangulation for the given centroid coodinates from a
     * Voronoi diagram
     * 
     * @param coords the list of Coordinates representing the centroids of the
     *               generated Voronoi diagram
     * @return the list of Polygons that represent neighbourhood relationships for
     *         the given Voronoi
     */
    List<Polygon> delaunayGen(List<Coordinate> coords) {

        GeometryFactory geomfactory = new GeometryFactory(HUNDREDTH_PRECISION_MODEL);
        DelaunayTriangulationBuilder dBuilder = new DelaunayTriangulationBuilder();
        dBuilder.setSites(coords);
        Geometry createdPolys = dBuilder.getTriangles(geomfactory);
        List<Polygon> triangleList = new ArrayList<>();

        GeometryCollection geomCollection = (GeometryCollection) createdPolys;
        for (int i = 0; i < geomCollection.getNumGeometries(); i++) {
            Polygon polygon = (Polygon) geomCollection.getGeometryN(i);
            triangleList.add(polygon);
        }
        return triangleList;
    }

    /**
     * Gets the vertices represented as a list of arrays of two doubles from a given
     * Polygon
     * 
     * @param polygon the Polygon from which to extract the vertices from
     * @return the list of vertices represented as arrays of two doubles where the
     *         first element is X and second element is Y
     */
    private List<double[]> getVertices(final Polygon polygon) {
        GeometryFactory geomFactory = new GeometryFactory(HUNDREDTH_PRECISION_MODEL);
        Coordinate[] coordinates = new Coordinate[10];
        List<double[]> vertices = new ArrayList<>();
        coordinates = polygon.getCoordinates();
        ConvexHull convexHull = new ConvexHull(coordinates, geomFactory);
        Geometry convexPoly = convexHull.getConvexHull();
        for (Coordinate c : convexPoly.getCoordinates()) {
            vertices.add(new double[] { c.getX(), c.getY() });
        }

        return vertices;
    }

    /**
     * Converts the given {@link org.locationtech.jts.geom.Polygon} into a list of
     * {@link ca.mcmaster.cas.se2aa4.a2.components.Poly}
     * 
     * @param voronoi  the list of Voronoi Polygons to be converted
     * @param delauney the list of Delauney Polygons for making neighbourhood
     *                 relationships
     * @return the converted list of type Poly
     */
    private List<Poly> convertPolygons(final List<Polygon> voronoi, final List<Polygon> delauney) {
        final List<Poly> polys = new ArrayList<>();
        Poly p, p1, p2;
        p1 = new ca.mcmaster.cas.se2aa4.a2.components.Polygon();
        p2 = new ca.mcmaster.cas.se2aa4.a2.components.Polygon();
        Set<Seg> segSet = new TreeSet<>();
        Seg s1, s2, s3;
        IntermediateVertexComparator vertComp = new IntermediateVertexComparator();
        for (Polygon dPoly : delauney) {
            Coordinate[] coordinates = dPoly.getCoordinates();
            s1 = new Segment(coordinates[0].getX(), coordinates[0].getY(),
                    coordinates[1].getX(), coordinates[1].getY());
            s2 = new Segment(coordinates[0].getX(), coordinates[0].getY(),
                    coordinates[2].getX(), coordinates[2].getY());
            s3 = new Segment(coordinates[2].getX(), coordinates[2].getY(),
                    coordinates[1].getX(), coordinates[1].getY());
            segSet.add(s1);
            segSet.add(s2);
            segSet.add(s3);
        }

        for (Polygon polygon : voronoi) {
            Point centroid = polygon.getCentroid();
            p = new ca.mcmaster.cas.se2aa4.a2.components.Polygon(centroid.getX(), centroid.getY());
            p.setVertices(getVertices(polygon));
            polys.add(p);
        }
        // sorting polygons to assign neighbours using binary search
        Collections.sort(polys);
        // Initialize a list of double arrays which will hold the centroid coordinates
        // of the neighbours
        List<List<double[]>> neighboursList = new ArrayList<>();
        for (int i = 0; i < polys.size(); i++) {
            neighboursList.add(new ArrayList<>());
        }
        p = new ca.mcmaster.cas.se2aa4.a2.components.Polygon();
        for (Seg seg : segSet) {
            // Binary search the centroid indices of the two polygons whose centroids are
            // the ends of this segment to add them to each other's neighboursList
            p.setCentroid(seg.getX1(), seg.getY1());
            p1 = polys.get(Collections.binarySearch(polys, p));
            p.setCentroid(seg.getX2(), seg.getY2());
            p2 = polys.get(Collections.binarySearch(polys, p));
            int idx1 = Collections.binarySearch(polys, p1);
            int idx2 = Collections.binarySearch(polys, p2);
            // Along the edges, the neighbourhood relation gets messed up. Fix this by
            // filtering using
            // binary search of one vertex to the other, since each polygon should at least
            // share 1 vertex
            // if they are neighbours.
            for (double[] p1Vertex : p1.getVertexList()) {
                for (double[] p2Vertex : p2.getVertexList()) {
                    if (Double.compare(p1Vertex[0], p2Vertex[0]) == 0
                            && Double.compare(p1Vertex[1], p2Vertex[1]) == 0) {
                        neighboursList.get(idx1).add(new double[] { seg.getX2(), seg.getY2() });
                        neighboursList.get(idx2).add(new double[] { seg.getX1(), seg.getY1() });
                    }
                }
            }
        }
        for (int i = 0; i < polys.size(); i++) {
            polys.get(i).setNeighbours(neighboursList.get(i));
        }

        return polys;
    }

}
