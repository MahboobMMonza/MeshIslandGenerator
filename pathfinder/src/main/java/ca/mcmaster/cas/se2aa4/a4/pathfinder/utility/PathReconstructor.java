package ca.mcmaster.cas.se2aa4.a4.pathfinder.utility;

import java.util.*;

import ca.mcmaster.cas.se2aa4.a4.pathfinder.components.Graph;

/**
 * PathReconstructor
 */
public class PathReconstructor {

    public static <T extends Number & Comparable<T>> List<List<Integer>> reconstructAllPaths(Graph<T> graph,
            List<Integer> incomingEdgeIdxs) {
        List<List<Integer>> paths = new ArrayList<>(graph.getNumNodes());
        int targetNodeIdx, targetEdgeIdx;
        for (int i = 0; i < graph.getNumNodes(); i++) {
            paths.add(new ArrayList<>());
            targetEdgeIdx = incomingEdgeIdxs.get(i);
            targetNodeIdx = i;
            while (targetEdgeIdx != -1) {
                paths.get(i).add(targetEdgeIdx);
                targetNodeIdx = graph.getAllEdges().get(targetEdgeIdx).getOtherIdx(targetNodeIdx);
                targetEdgeIdx = incomingEdgeIdxs.get(targetNodeIdx);
            }
            Collections.reverse(paths.get(i));
        }
        return paths;
    }

}
