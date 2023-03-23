package ca.mcmaster.cas.se2aa4.a3.island.components;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.*;

/**
 * Edge
 */
public class Edge {

    public static final float DEFAULT_THICKNESS = 0f;
    private float thickness;
    private int index, v1Index, v2Index, colour;

    public Edge(int index, Segment seg) {
        this.index = index;
        thickness = DEFAULT_THICKNESS;
        v1Index = seg.getV1Idx();
        v2Index = seg.getV2Idx();
    }

    public int getColour() {
        return colour;
    }

    public void setColour(int colour) {
        this.colour = colour;
    }

    public float getThickness() {
        return thickness;
    }

    public int getIndex() {
        return index;
    }

    public int getV1Index() {
        return v1Index;
    }

    public int getV2Index() {
        return v2Index;
    }

    public void addThickness(float thickness) {
        this.thickness += thickness;
    }

    // public int getOther(int vertIdx) {
    //     return (vertIdx == v1Index) ? v2Index : v1Index;
    // }
}
