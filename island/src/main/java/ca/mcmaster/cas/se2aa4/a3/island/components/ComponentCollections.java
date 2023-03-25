package ca.mcmaster.cas.se2aa4.a3.island.components;

import java.util.*;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.*;
import ca.mcmaster.cas.se2aa4.a3.island.elevation.ElevationLevels;

/**
 * TileSets
 */
public enum ComponentCollections {

    COLLECTION;

    private Map<Integer, Tile> allTiles = new HashMap<>();

    private Map<Integer, Edge> allEdges = new HashMap<>();

    private Map<Integer, Point> allPoints = new HashMap<>();

    private boolean setup = false, ready = false;

    /* tiles that are going to be tracked */
    private Set<Integer> oceans = new HashSet<>(), lagoons = new HashSet<>(), shores = new HashSet<>(),
            innerLand = new HashSet<>(), aquifers = new HashSet<>(), lakes = new HashSet<>(),
            freshWaterPoints = new HashSet<>();

    public Iterable<Integer> getAllTileIdxs() {
        return allTiles.keySet();
    }

    public Iterable<Integer> getAllEdgeIdxs() {
        return allEdges.keySet();
    }

    public Iterable<Integer> getAllPointIdxs() {
        return allPoints.keySet();
    }

    public boolean isSetup() {
        return setup;
    }

    public boolean isOceanTile(int index) {
        return oceans.contains(index);
    }

    public boolean isLagoonTile(int index) {
        return lagoons.contains(index);
    }

    public boolean isShoreTile(int index) {
        return shores.contains(index);
    }

    public boolean isInnerLandTile(int index) {
        return innerLand.contains(index);
    }

    public boolean isLakeTile(int index) {
        return lakes.contains(index);
    }

    public boolean isAquiferTile(int index) {
        return aquifers.contains(index);
    }

    public Iterable<Integer> getOceans() {
        return oceans;
    }

    public Iterable<Integer> getLagoons() {
        return lagoons;
    }

    public Iterable<Integer> getShores() {
        return shores;
    }

    public Iterable<Integer> getLakes() {
        return lakes;
    }

    public Iterable<Integer> getAquifers() {
        return aquifers;
    }

    public Iterable<Integer> getInnerLand() {
        return innerLand;
    }

    public Iterable<Integer> getFreshWaterPoints() {
        return freshWaterPoints;
    }

    public ElevationLevels getTileElevationLevel(int index) {
        return allTiles.get(index).getElevation();
    }

    public double getCentreX(int index) {
        return allTiles.get(index).getCentreX();
    }

    public double getCentreY(int index) {
        return allTiles.get(index).getCentreY();
    }

    public double getTileCentreY(int index) {
        return allTiles.get(index).getCentreY();
    }

    public double getPointX(int index) {
        return allPoints.get(index).getX();
    }

    public double getPointY(int index) {
        return allPoints.get(index).getY();
    }

    public List<Integer> getTileNeighbourIdxs(int index) {
        return allTiles.get(index).getNeighbourIdxs();
    }

    public List<Integer> getTileEdgeIdxs(int index) {
        return allTiles.get(index).getEdgeIdxs();
    }

    public List<Integer> getTilePointIdxs(int index) {
        return allTiles.get(index).getEdgeIdxs();
    }

    public TileTypes getTileType(int index) {
        return allTiles.get(index).getTileType();
    }

    public int getTileColour(int index) {
        return allTiles.get(index).getColour();
    }

    public int getEdgeColour(int index) {
        return allEdges.get(index).getColour();
    }

    public int getEdgeIndex(int index) {
        return allEdges.get(index).getIndex();
    }

    public int getEdgeV1Index(int index) {
        return allEdges.get(index).getV1Index();
    }

    public int getEdgeV2Index(int index) {
        return allEdges.get(index).getV1Index();
    }

    public float getEdgeThickness(int index) {
        return allEdges.get(index).getThickness();
    }

    public Map<Integer, Tile> getAllTiles() {
        if (!ready) {
            throw new UnsupportedOperationException("Cannot request the Tiles when the island is not ready.");
        }
        return allTiles;
    }

    public Map<Integer, Edge> getAllEdges() {
        if (!ready) {
            throw new UnsupportedOperationException("Cannot request the Edges when the island is not ready.");
        }
        return allEdges;
    }

    public Map<Integer, Point> getAllPoints() {
        if (!ready) {
            throw new UnsupportedOperationException("Cannot request the Points when the island is not ready.");
        }
        return allPoints;
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

    public void ready() {
        this.ready = true;
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

    public void updateElevationLevels(Map<Integer, ElevationLevels> elevations) {
        allTiles.forEach((index, tile) -> tile.setElevation(elevations.get(index)));
    }

    public void updateTileTypes(Map<Integer, TileTypes> tileTypes) {
        allTiles.forEach((index, tile) -> tile.setTileType(tileTypes.get(index)));
        updateLagoons();
        updateOceans();
        updateShores();
        updateInnerLand();
    }

    public void updateTileColours(Map<Integer, Integer> colours) {
        allTiles.forEach((index, tile) -> tile.setColour(colours.get(index)));
    }

    public void updateEdgeColours(Map<Integer, Integer> colours) {
        allEdges.forEach((index, edge) -> edge.setColour(colours.get(index)));
    }

    private void updateFreshWaterPoints() {
        freshWaterPoints = new HashSet<>();
        for (Tile tile : allTiles.values()) {
            if (lakes.contains(tile.getIndex()) || aquifers.contains(tile.getIndex())) {
                freshWaterPoints.addAll(tile.getPointIdxs());
            }
        }
    }

    private void updateLagoons() {
        lagoons = new HashSet<>();
        for (Tile tile : allTiles.values()) {
            if (tile.getTileType().equals(TileTypes.LAGOON)) {
                lagoons.add(tile.getIndex());
            }
        }
    }

    private void updateOceans() {
        oceans = new HashSet<>();
        for (Tile tile : allTiles.values()) {
            if (tile.getTileType().equals(TileTypes.OCEAN)) {
                oceans.add(tile.getIndex());
            }
        }
    }

    private void updateShores() {
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

    private void updateInnerLand() {
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

}
