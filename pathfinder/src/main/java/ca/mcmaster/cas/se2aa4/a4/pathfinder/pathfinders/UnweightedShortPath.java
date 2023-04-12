package ca.mcmaster.cas.se2aa4.a4.pathfinder.pathfinders;

import static ca.mcmaster.cas.se2aa4.a4.pathfinder.utility.PathReconstructor.*;

import java.util.*;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.components.Graph;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.utility.PathInfoTriple;

/**
 * UnweightedShortPathfinder treats all edges as unweighted and finds the shortest path using Breadth-First Search.
 */
public class UnweightedShortPath<T extends Number & Comparable<T>> implements Pathfinder<T, Integer> {

    static final int NO_INCOMING_EDGE = -1, INFINITY = Integer.MAX_VALUE;

    ArrayList<Integer> costs;
    ArrayList<Integer> incomingEdgeIdxs;
    ArrayList<Boolean> visited;
    int numNodes;

    public UnweightedShortPath() {
        costs = new ArrayList<>();
        incomingEdgeIdxs = new ArrayList<>();
        visited = new ArrayList<>();
    }

    void resetAllLists(int numNodes) {
        this.numNodes = numNodes;
        costs = new ArrayList<>(numNodes);
        incomingEdgeIdxs = new ArrayList<>(numNodes);
        visited = new ArrayList<>(numNodes);
        for (int i = 0; i < numNodes; i++) {
            costs.add(INFINITY);
            incomingEdgeIdxs.add(NO_INCOMING_EDGE);
            visited.add(false);
        }
        // setAllListDefaults(incomingEdgeIdxs, NO_INCOMING_EDGE, numNodes);
        // setAllListDefaults(costs, INFINITY, numNodes);
        // setAllListDefaults(visited, false, numNodes);
    }

    void bfs(int sourceNodeIdx, Graph<T> graph) {
        int curNodeIdx, neighbourNodeIdx;
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(sourceNodeIdx);
        visited.set(sourceNodeIdx, true);
        costs.set(sourceNodeIdx, 0);
        while (!queue.isEmpty()) {
            curNodeIdx = queue.remove();
            for (int edgeIdx : graph.getAssociatedEdgeIdxs(curNodeIdx)) {
                // treat null cost as unreachable destination
                if (graph.getAllEdges().get(edgeIdx).getCost(curNodeIdx).isEmpty()) {
                    continue;
                }
                neighbourNodeIdx = graph.getAllEdges().get(edgeIdx).getOtherIdx(curNodeIdx);
                if (visited.get(neighbourNodeIdx)) {
                    continue;
                }
                visited.set(neighbourNodeIdx, true);
                queue.add(neighbourNodeIdx);
                costs.set(neighbourNodeIdx, costs.get(curNodeIdx) + 1);
                incomingEdgeIdxs.set(neighbourNodeIdx, edgeIdx);
            }
        }
    }

    @Override
    public PathInfoTriple<Integer> findAllSourcePaths(Graph<T> graph, int sourceNodeIdx) {
        PathInfoTriple<Integer> values = new PathInfoTriple<>();
        resetAllLists(graph.getNumNodes());
        bfs(sourceNodeIdx, graph);
        values.setPathEdgeIdxs(reconstructAllPaths(graph, incomingEdgeIdxs));
        values.setPathCosts(costs);
        values.setInfinityValue(INFINITY);
        return values;
    }
}
