package ca.mcmaster.cas.se2aa4.a4.pathfinder.components;

import java.util.*;

/**
 * VersatileEdge provides an interface for creating weighted edges in a graph
 * that can be undirected or directed.
 * The weight associated is called the Cost, and it can be set to appropriate
 * primitive-wrapped subtypes of the Number class. In the graph,
 * define the type T to match E.
 */
public interface VersatileEdge<E extends Number & Comparable<E>> {

    int getIndex();

    void setIndex(int edgeIdx);

    int getN1Idx();

    int getN2Idx();

    default int getOtherIdx(int sourceIdx) {
        if (sourceIdx == getN2Idx()) {
            return getN1Idx();
        } else if (sourceIdx == getN1Idx()) {
            return getN2Idx();
        } else {
            throw new IllegalArgumentException(
                    "The index given does not match the indices of any known nodes this edge connects.");
        }
    }

    void setN2Idx(int nodeIdx);

    void setN1Idx(int nodeIdx);

    Optional<E> getCost(int sourceIdx);

    void setCost(int sourceIdx, E cost);
}
