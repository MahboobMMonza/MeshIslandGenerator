package ca.mcmaster.cas.se2aa4.a4.pathfinder.pathfinders;

import static ca.mcmaster.cas.se2aa4.a4.pathfinder.utility.PathReconstructor.*;

import java.util.*;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.utility.PathInfoTriple;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.components.Graph;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.components.VersatileEdge;

/**
 * PosWeightedShortPath finds the shortest path from a given source node to
 * all other reachable nodes in a weighted graph. Additionally an option to find
 * the path using reverse ordering of edge weights can also be configured by
 * setting the <code>reverseOrder</code> property to true in the constructor.
 */
public class PosWeightedShortPath<T extends Number & Comparable<T>> implements Pathfinder<T, T> {

    static final int NO_INCOMING_EDGE = -1;

    final T infinity, zero;

    ArrayList<T> costs;
    ArrayList<Integer> incomingEdgeIdxs;
    ArrayList<Boolean> visited;
    int numNodes;

    public PosWeightedShortPath(Class<T> nonNullCostClass) {
        if (nonNullCostClass == null) {
            throw new IllegalArgumentException("Cannot create a PosWeightedGraph with a given null data class.");
        }
        if (!(nonNullCostClass.equals(Integer.class)) && !(nonNullCostClass.equals(Long.class))
                && !(nonNullCostClass.equals(Double.class))
                && !(nonNullCostClass.equals(Float.class))) {
            throw new UnsupportedOperationException(
                    "Cannot create a PosWeightedGraph for versatile edges with cost type '"
                            + nonNullCostClass.getName()
                            + "'.");
        }
        // Should always work
        if (nonNullCostClass.equals(Integer.class)) {
            zero = (T) Integer.valueOf(0);
            infinity = (T) Integer.valueOf(Integer.MAX_VALUE);
        } else if (nonNullCostClass.equals(Long.class)) {
            zero = (T) Long.valueOf(0L);
            infinity = (T) Long.valueOf(Long.MAX_VALUE);
        } else if (nonNullCostClass.equals(Double.class)) {
            zero = (T) Double.valueOf(0d);
            infinity = (T) Double.valueOf(Double.POSITIVE_INFINITY);
        } else {
            zero = (T) Float.valueOf(0f);
            infinity = (T) Float.valueOf(Float.POSITIVE_INFINITY);
        }
    }

    boolean checkValidGraph(Graph<T> graph) {
        T edgeCost = null;
        boolean checker = true;
        for (VersatileEdge<T> edge : graph.getAllEdges()) {
            if (!edge.getCost(edge.getN1Idx()).isEmpty()) {
                edgeCost = edge.getCost(edge.getN1Idx()).get();
                checker &= edgeCost.compareTo(zero) > 0;
            }
            if (!edge.getCost(edge.getN2Idx()).isEmpty()) {
                edgeCost = edge.getCost(edge.getN2Idx()).get();
                checker &= edgeCost.compareTo(zero) > 0;
            }
        }
        return checker;
    }

    @Override
    public PathInfoTriple<T> findAllSourcePaths(Graph<T> graph, int sourceNodeIdx) {
        PathInfoTriple<T> allInfo = new PathInfoTriple<>();
        boolean valid = checkValidGraph(graph);
        allInfo.setInfinityValue(infinity);
        if (!valid) {
            throw new IllegalArgumentException("The given edge list contains negatively weighted edges.");
        }
        resetAllLists(graph.getNumNodes());
        if (numNodes > 0) {
            dijkstra(graph, sourceNodeIdx);
        }
        allInfo.setPathEdgeIdxs(reconstructAllPaths(graph, incomingEdgeIdxs, sourceNodeIdx));
        allInfo.setPathCosts(costs);
        return allInfo;
    }

    /**
     * Sums the cost of travelling along an edge with the cost of the source node.
     *
     * Note: Object-orientation is broken by checking explicit class for operations.
     *
     * @param curNodeCost The cost of the current node.
     * @param edgeCost    The cost of the edge that is being traveresed.
     * @return The sum of the cost of the two arguments.
     */
    T sumCostEdge(T curNodeCost, T edgeCost) {
        if (curNodeCost.compareTo(infinity) == 0 || edgeCost.compareTo(infinity) == 0) {
            return infinity;
        }
        if (curNodeCost.compareTo(zero) < 0 || edgeCost.compareTo(zero) < 0) {
            throw new IllegalArgumentException("Edge costs cannot be negative for this pathfinder.");
        }
        // Necessary to break object orientation with explicit casts and instance checks
        if (curNodeCost instanceof Integer) {
            return (T) (Integer.valueOf(curNodeCost.intValue() + edgeCost.intValue()));
        }
        if (curNodeCost instanceof Long) {
            return (T) (Long.valueOf(curNodeCost.longValue() + edgeCost.longValue()));
        }
        if (curNodeCost instanceof Double) {
            return (T) (Double.valueOf(curNodeCost.doubleValue() + edgeCost.doubleValue()));
        }
        // By default based on constructor only option is float
        return (T) (Float.valueOf(curNodeCost.floatValue() + edgeCost.floatValue()));
    }

    void resetAllLists(int numNodes) {
        this.numNodes = Math.max(numNodes, 0);
        costs = new ArrayList<>(numNodes);
        visited = new ArrayList<>(numNodes);
        incomingEdgeIdxs = new ArrayList<>(numNodes);
        for (int i = 0; i < numNodes; i++) {
            costs.add(infinity);
            visited.add(false);
            incomingEdgeIdxs.add(NO_INCOMING_EDGE);
        }
    }

    void dijkstra(Graph<T> graph, int sourceNodeIdx) {
        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.comparing(i -> costs.get(i)));
        int curIdx;
        VersatileEdge<T> edge;
        T newCost;
        costs.set(sourceNodeIdx, zero);
        curIdx = sourceNodeIdx;
        pq.add(curIdx);
        while (!pq.isEmpty()) {
            curIdx = pq.remove();
            if (visited.get(curIdx)) {
                continue;
            }
            visited.set(curIdx, true);
            for (Integer edgeIdx : graph.getAssociatedEdgeIdxs(curIdx)) {
                edge = graph.getAllEdges().get(edgeIdx);
                // Treat null cost as unreachable destination in given direction
                if (edge.getCost(curIdx).isEmpty()) {
                    continue;
                }
                newCost = sumCostEdge(costs.get(curIdx), edge.getCost(curIdx).get());
                // If this route does not optimize then continue
                if (newCost.compareTo(costs.get(graph.getAllEdges().get(edgeIdx).getOtherIdx(curIdx))) >= 0) {
                    continue;
                }
                costs.set(graph.getAllEdges().get(edgeIdx).getOtherIdx(curIdx), newCost);
                incomingEdgeIdxs.set(graph.getAllEdges().get(edgeIdx).getOtherIdx(curIdx), edgeIdx);
                pq.add(edge.getOtherIdx(curIdx));
            }
        }
    }

}
