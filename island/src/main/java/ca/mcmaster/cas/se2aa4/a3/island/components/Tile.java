package ca.mcmaster.cas.se2aa4.a3.island.components;

import java.util.*;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.*;

/**
 * PolyTile
 */
public class Tile {

    private double centreX, centreY;
    private int index, colour, elevationLevel, moistureLevel;

    private List<Integer> neighbourIdxs, edgeIdxs, pointIdxs;

    private TileTypes tileType;

    public Tile(Polygon poly, List<Segment> allSegments, List<Vertex> allVertices) {
        index = poly.getCentroidIdx();
        centreX = allVertices.get(poly.getCentroidIdx()).getX();
        centreY = allVertices.get(poly.getCentroidIdx()).getY();
        neighbourIdxs = new ArrayList<>();
        for (int centIdx : poly.getNeighborIdxsList()) {
            neighbourIdxs.add(centIdx);
        }
        edgeIdxs = new ArrayList<>();
        Set<Integer> verts = new HashSet<>();
        for (int segIdx : poly.getSegmentIdxsList()) {
            edgeIdxs.add(segIdx);
            verts.add(allSegments.get(segIdx).getV1Idx());
            verts.add(allSegments.get(segIdx).getV2Idx());
        }
        pointIdxs = new ArrayList<>(verts);
    }

    public int getMoistureLevel() {
        return moistureLevel;
    }

    public void setMoistureLevel(int moistureLevel) {
        this.moistureLevel = moistureLevel;
    }

    public int getElevationLevel() {
        return elevationLevel;
    }

    public void setElevationLevel(int elevationLevel) {
        this.elevationLevel = elevationLevel;
    }

    public int getColour() {
        return colour;
    }

    public void setColour(int colour) {
        this.colour = colour;
    }

    public void setTileType(TileTypes type) {
        this.tileType = type;
    }

    public double getCentreX() {
        return centreX;
    }

    public double getCentreY() {
        return centreY;
    }

    public int getIndex() {
        return index;
    }

    public List<Integer> getNeighbourIdxs() {
        return neighbourIdxs;
    }

    public List<Integer> getEdgeIdxs() {
        return edgeIdxs;
    }

    public List<Integer> getPointIdxs() {
        return pointIdxs;
    }

    public TileTypes getTileType() {
        return tileType;
    }
}
