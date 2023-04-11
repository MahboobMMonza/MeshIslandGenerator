package ca.mcmaster.cas.se2aa4.a4.pathfinder.components;

import static ca.mcmaster.cas.se2aa4.a4.pathfinder.utility.Utility.*;

import java.util.*;

/**
 * BasicGraph. T must be the same as the cost type for the list of VersatileEdge edges.
 */
public class BasicGraph<T extends Number & Comparable<T>> implements Graph<T> {

    List<Node> allNodes;
    List<VersatileEdge<T>> allEdges;
    List<List<Integer>> allNeighbouringEdges;
    int numNodes;


    public BasicGraph() {
        allNodes = new ArrayList<>();
        allEdges = new ArrayList<>();
        allNeighbouringEdges = new ArrayList<>();
        numNodes = 0;
    }

    @Override
    public List<Node> getAllNodes() {
        return allNodes;
    }

    @Override
    public List<VersatileEdge<T>> getAllEdges() {
        return allEdges;
    }

    @Override
    public void setAssociatedEdgeIdxs(int nodeIdx, List<Integer> associatedEdgeIdxs) {
        allNeighbouringEdges.set(nodeIdx, associatedEdgeIdxs);
    }

    @Override
    public List<Integer> getAssociatedEdgeIdxs(int NodeIdx) {
        return allNeighbouringEdges.get(NodeIdx);
    }

    @Override
    public void setAllNodes(List<Node> nodes) {
        allNodes = nodes;
        numNodes = allNodes.size();
        allNeighbouringEdges = new ArrayList<>(numNodes);
        // Reset the adjacencies whenever nodes are set
        for (int i = 0; i < numNodes; i++) {
            allNeighbouringEdges.add(new ArrayList<>());
        }
    }

    @Override
    public void setAllEdges(List<VersatileEdge<T>> edges) {
        allEdges = edges;
    }

    @Override
    public int getNumNodes() {
        return numNodes;
    }
}
