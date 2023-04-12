package ca.mcmaster.cas.se2aa4.a4.pathfinder.utility;

import java.util.*;

import ca.mcmaster.cas.se2aa4.a4.pathfinder.components.Graph;

/**
 * PathReconstructor
 */
public class PathReconstructor {

    static final int NO_EDGES_FOUND = -1;

    public static <T extends Number & Comparable<T>> List<List<Integer>> reconstructAllPaths(Graph<T> graph,
            List<Integer> incomingEdgeIdxs, int sourceNodeIdx) {
        List<List<Integer>> paths = new ArrayList<>(graph.getNumNodes());
        int targetNodeIdx, targetEdgeIdx;
        for (int i = 0; i < graph.getNumNodes(); i++) {
            paths.add(new ArrayList<>());
            targetEdgeIdx = incomingEdgeIdxs.get(i);
            targetNodeIdx = i;
            while (targetEdgeIdx != NO_EDGES_FOUND) {
                paths.get(i).add(targetEdgeIdx);
                targetNodeIdx = graph.getAllEdges().get(targetEdgeIdx).getOtherIdx(targetNodeIdx);
                targetEdgeIdx = incomingEdgeIdxs.get(targetNodeIdx);
            }
            if (paths.get(i).isEmpty() && i != sourceNodeIdx) {
                paths.get(i).add(NO_EDGES_FOUND);
            }
            Collections.reverse(paths.get(i));
        }
        return paths;
    }

}
