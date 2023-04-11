package ca.mcmaster.cas.se2aa4.a4.pathfinder.pathfinders;

import ca.mcmaster.cas.se2aa4.a4.pathfinder.components.Graph;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.utility.*;

/**
 * Pathfinder finds a path between a source node to all other nodes in the
 * graph. T and U must be the same type as used for the edges of a given graph
 * if weights are counted, or for unweighted pathfinders, U must be Integer.
 */
public interface Pathfinder<T extends Number & Comparable<T>, U extends Number & Comparable<U>> {

    /**
     * Finds a path between a source node to all reachable nodes, and returns an
     * Object array of 3 elements: A List< List< Integer>> with the edges to take
     * from the source node to the target node, a List< U> which contains the
     * distances of the nodes from the source, and a U with the value used to
     * represent the unreachable distance.
     *
     * <p>
     * If the first list is empty and the node index is not the source node index,
     * then
     * that node is not reachable from the source node.
     * </p>
     *
     * @param graph         the graph to search in.
     * @param sourceNodeIdx the index of the source node.
     * @return an Object array as described in the summary.
     */
     PathInfoTriple<U> findAllSourcePaths(Graph<T> graph, int sourceNodeIdx);
}
