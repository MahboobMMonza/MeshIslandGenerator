package ca.mcmaster.cas.se2aa4.a4.pathfinder.components;

import java.util.*;

/**
 * Graph provides an interface for representing a graph with nodes and
 * (un)weighted edges, where a CostType must be defined during construction of
 * an implementing class.
 */
public interface Graph<T extends Number & Comparable<T>> {

    List<Node> getAllNodes();

    List<VersatileEdge<T>> getAllEdges();

    void setAssociatedEdgeIdxs(int nodeIdx, List<Integer> associatedEdgeIdxs);

    List<Integer> getAssociatedEdgeIdxs(int NodeIdx);

    void setAllNodes(List<Node> nodes);

    void setAllEdges(List<VersatileEdge<T>> edges);

    int getNumNodes();
}
