package ca.mcmaster.cas.se2aa4.a4.pathfinder.components;

import java.util.Optional;

/**
 * BasicBiEdge
 */
public class BasicBiEdge<T extends Number & Comparable<T>> implements VersatileEdge<T> {

    int index, n1Index, n2Index;
    T cost;

    public BasicBiEdge() {
        index = n1Index = n2Index = -1;
    }

    public BasicBiEdge(int index, int n1Index, int n2Index, T cost) {
        this();
        setIndex(index);
        setN1Idx(n1Index);
        setN2Idx(n2Index);
        updateCost(cost);
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public void setIndex(int edgeIdx) {
        if (edgeIdx < 0) {
            throw new IllegalArgumentException("Cannot change index of an edge to a negative number.");
        }
        index = edgeIdx;
    }

    @Override
    public int getN1Idx() {
        return n1Index;
    }

    @Override
    public int getN2Idx() {
        return n2Index;
    }

    @Override
    public void setN1Idx(int nodeIdx) {
        if (nodeIdx < 0) {
            throw new IllegalArgumentException("Cannot change index of a node to a negative number.");
        }
        n1Index = nodeIdx;
    }

    @Override
    public void setN2Idx(int nodeIdx) {
        if (nodeIdx < 0) {
            throw new IllegalArgumentException("Cannot change index of a node to a negative number.");
        }
        n1Index = nodeIdx;
    }

    @Override
    public Optional<T> getCost(int sourceIdx) {
        if (sourceIdx != n1Index && sourceIdx != n2Index) {
            throw new IllegalArgumentException("The given node does not match the nodes connected by this edge.");
        }
        // Let using object deal with null costs
        return Optional.ofNullable(cost);
    }

    @Override
    public void setCost(int sourceIdx, T cost) {
        if (sourceIdx != n1Index && sourceIdx != n2Index) {
            throw new IllegalArgumentException("The given node does not match the nodes connected by this edge.");
        }
        updateCost(cost);
    }

    void updateCost(T newCost) {
        cost = newCost;
    }

}
