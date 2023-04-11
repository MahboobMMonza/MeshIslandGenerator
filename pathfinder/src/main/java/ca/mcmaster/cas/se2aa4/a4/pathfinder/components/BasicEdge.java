package ca.mcmaster.cas.se2aa4.a4.pathfinder.components;

import java.util.Optional;

/**
 * BasicEdge
 */
public class BasicEdge<T extends Number & Comparable<T>> implements VersatileEdge<T> {

    int index, source, dest;
    T cost;

    public BasicEdge() {
        index = source = dest = -1;
    }

    public BasicEdge(int index, int sourceIndex, int destIndex, T cost) {
        this();
        setIndex(index);
        setN1Idx(sourceIndex);
        setN2Idx(destIndex);
        this.cost = cost;
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
        return source;
    }

    @Override
    public int getN2Idx() {
        return dest;
    }

    @Override
    public void setN2Idx(int nodeIdx) {
        if (nodeIdx < 0) {
            throw new IllegalArgumentException("Cannot change index of a node to a negative number.");
        }
        dest = nodeIdx;
    }

    @Override
    public void setN1Idx(int nodeIdx) {
        if (nodeIdx < 0) {
            throw new IllegalArgumentException("Cannot change index of a node to a negative number.");
        }
        source = nodeIdx;
    }

    @Override
    public Optional<T> getCost(int sourceIdx) {
        if (sourceIdx == dest) {
            return Optional.empty();
        }
        if (sourceIdx != source) {
            throw new IllegalArgumentException("The given node does not match the nodes connected by this edge.");
        }
        return Optional.ofNullable(cost);
    }

    @Override
    public void setCost(int sourceIdx, T cost) {
        if (sourceIdx == dest) {
            throw new IllegalArgumentException(
                    "Cannot set a cost for source node at N2Idx since this node is soley interpreted as a destination.");
        } else if (sourceIdx != source) {
            throw new IllegalArgumentException("The given node does not match the nodes connected by this edge.");
        }
        this.cost = cost;
    }

}
