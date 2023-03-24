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
    private Set<Integer> oceans = new HashSet<>(), lagoons = new HashSet<>(), shores = new HashSet<>(),
            innerLand = new HashSet<>(), aquifers = new HashSet<>(), lakes = new HashSet<>(),
            freshWaterPoints = new HashSet<>();

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

    public Set<Integer> getLakes() {
        return lakes;
    }

    public Set<Integer> getAquifers() {
        return aquifers;
    }

    public Set<Integer> getInnerLand() {
        return innerLand;
    }

    public Set<Integer> getFreshWaterPoints() {
        return freshWaterPoints;
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
        TileTypes type;
        shores = new HashSet<>();
        for (Tile tile : allTiles.values()) {
            if (!tile.getTileType().equals(TileTypes.LAND)) {
                continue;
            }
            for (Integer neighbour : tile.getNeighbourIdxs()) {
                type = allTiles.get(neighbour).getTileType();
                if (type.equals(TileTypes.LAGOON) || type.equals(TileTypes.OCEAN)) {
                    shores.add(tile.getIndex());
                    continue;
                }
            }
        }
    }

    public void updateLakes(Set<Integer> lakes) {
        this.lakes = lakes;
        innerLand.removeAll(lakes);
        updateFreshWaterPoints();
    }

    public void updateAquifers(Set<Integer> aquifers) {
        this.aquifers = aquifers;
        updateFreshWaterPoints();
    }

    public void updateInnerLand() {
        innerLand = new HashSet<>();
        TileTypes type;
        for (Tile tile : allTiles.values()) {
            type = tile.getTileType();
            if ((type.equals(TileTypes.LAND))
                    && !shores.contains(tile.getIndex())) {

                innerLand.add(tile.getIndex());
            }
        }
    }

    private void updateFreshWaterPoints() {
        freshWaterPoints = new HashSet<>();
        for (Tile tile : allTiles.values()) {
            if (lakes.contains(tile.getIndex()) || aquifers.contains(tile.getIndex())) {
                freshWaterPoints.addAll(tile.getPointIdxs());
            }
        }
    }

}
