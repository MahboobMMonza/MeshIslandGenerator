package ca.mcmaster.cas.se2aa4.a3.island.components;

import java.util.*;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.*;

/**
 * TileSets
 */
public enum ComponentCollections {

    COLLECTION;

    private Map<Integer, Tile> allTiles = new HashMap<>();

    private Map<Integer, Edge> allEdges = new HashMap<>();

    private Map<Integer, Point> allPoints = new HashMap<>();

    private boolean setup = false;

    /* tiles that are going to be tracked */
    private Set<Integer> oceans = new HashSet<>(), lagoons = new HashSet<>(), shores = new HashSet<>();

    public Map<Integer, Tile> getAllTiles() {
        return allTiles;
    }

    public Map<Integer, Edge> getAllEdges() {
        return allEdges;
    }

    public Map<Integer, Point> getAllPoints() {
        return allPoints;
    }

    public boolean isSetup() {
        return setup;
    }

    public Set<Integer> getOceans() {
        return oceans;
    }

    public Set<Integer> getLagoons() {
        return lagoons;
    }

    public Set<Integer> getShores() {
        return shores;
    }

    public void setup(Mesh mesh) {
        if (setup) {
            return;
        }
        for (int i = 0; i < mesh.getVerticesCount(); i++) {
            allPoints.put(i, new Point(i, mesh.getVertices(i)));
        }
        for (int i = 0; i < mesh.getSegmentsCount(); i++) {
            allEdges.put(i, new Edge(i, mesh.getSegments(i)));
        }
        for (Polygon poly : mesh.getPolygonsList()) {
            Tile tile = new Tile(poly, mesh.getSegmentsList(), mesh.getVerticesList());
            allTiles.put(tile.getIndex(), tile);
        }
    }

    public void updateLagoons() {
        lagoons = new HashSet<>();
        for (Tile tile : allTiles.values()) {
            if (tile.getTileType().equals(TileTypes.LAGOON)) {
                lagoons.add(tile.getIndex());
            }
        }
    }

    public void updateOceans() {
        oceans = new HashSet<>();
        for (Tile tile : allTiles.values()) {
            if (tile.getTileType().equals(TileTypes.OCEAN)) {
                oceans.add(tile.getIndex());
            }
        }
    }

    public void updateShores() {
        shores = new HashSet<>();
        for (Tile tile : allTiles.values()) {
            if (!tile.getTileType().equals(TileTypes.LAND)) {
                continue;
            }
            for (Integer neighbour : tile.getNeighbourIdxs()) {
                if (lagoons.contains(neighbour) || oceans.contains(neighbour)) {
                    shores.add(tile.getIndex());
                }
            }
        }
    }
}
