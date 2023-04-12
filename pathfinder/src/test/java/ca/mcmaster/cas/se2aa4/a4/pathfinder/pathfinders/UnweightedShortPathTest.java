package ca.mcmaster.cas.se2aa4.a4.pathfinder.pathfinders;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.components.*;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.utility.*;
import java.util.*;

/**
 * UnweightedShortPathTest
 */
public class UnweightedShortPathTest {

    @Test
    public void bidirectionalShortestPath_AllMatchSucceeds() {
        BasicGraph<Integer> graph = new BasicGraph<>();
        List<VersatileEdge<Integer>> edges = new ArrayList<>();
        List<Node> nodes = new ArrayList<>();
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            nodes.add(new BasicNode(i));
        }
        edges.add(new BasicBiEdge<Integer>(0, 0, 1, 1));
        edges.add(new BasicBiEdge<Integer>(1, 0, 3, 1));
        edges.add(new BasicBiEdge<Integer>(2, 1, 2, 1));
        edges.add(new BasicBiEdge<Integer>(3, 3, 2, 1));
        edges.add(new BasicBiEdge<Integer>(4, 4, 2, 1));
        edges.add(new BasicBiEdge<Integer>(5, 4, 3, 1));
        edges.add(new BasicBiEdge<Integer>(6, 4, 5, 1));
        adj.add(List.of(0, 1));
        adj.add(List.of(0, 2));
        adj.add(List.of(2, 3, 4));
        adj.add(List.of(1, 3, 5));
        adj.add(List.of(4, 5, 6));
        adj.add(List.of(6));
        graph.setAllNodes(nodes);
        graph.setAllEdges(edges);
        for (int i = 0; i < adj.size(); i++) {
            graph.setAssociatedEdgeIdxs(i, adj.get(i));
        }
        Pathfinder<Integer, Integer> pathfinder = new UnweightedShortPath<Integer>();
        PathInfoTriple<Integer> info = pathfinder.findAllSourcePaths(graph, 0);
        assertEquals(List.of(0, 1, 2, 1, 2, 3), info.getPathCosts());
        assertEquals(List.of(), info.getPathEdgeIdxs().get(0));
        assertEquals(List.of(0), info.getPathEdgeIdxs().get(1));
        assertTrue(info.getPathEdgeIdxs().get(2).equals(List.of(0, 2))
                || info.getPathEdgeIdxs().get(2).equals(List.of(1, 3)));
        assertEquals(List.of(1), info.getPathEdgeIdxs().get(3));
        assertEquals(List.of(1, 5), info.getPathEdgeIdxs().get(4));
    }

    @Test
    public void emptyGraph_AllEmptySucceeds() {
        Graph<Integer> graph = new BasicGraph<>();
        Pathfinder<Integer, Integer> pathfinder = new UnweightedShortPath<>();
        PathInfoTriple<Integer> info = pathfinder.findAllSourcePaths(graph, 0);
        assertTrue(info.getPathEdgeIdxs().isEmpty());
        assertTrue(info.getPathCosts().isEmpty());
    }

    @Test
    public void directedNullEdgeCostShortestPath_AllMatchSucceeds() {
        BasicGraph<Integer> graph = new BasicGraph<>();
        List<VersatileEdge<Integer>> edges = new ArrayList<>();
        List<Node> nodes = new ArrayList<>();
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            nodes.add(new BasicNode(i));
        }
        edges.add(new BasicEdge<Integer>(0, 0, 1, 1));
        edges.add(new BasicEdge<Integer>(1, 0, 3, 1));
        edges.add(new BasicEdge<Integer>(2, 1, 2, 1));
        edges.add(new BasicEdge<Integer>(3, 3, 2, 1));
        edges.add(new BasicEdge<Integer>(4, 2, 4, 1));
        edges.add(new BasicEdge<Integer>(5, 4, 3, 1));
        edges.add(new BasicEdge<Integer>(6, 4, 5, 1));
        edges.add(new BasicEdge<Integer>(7, 2, 0, 1));
        adj.add(List.of(0, 1, 7));
        adj.add(List.of(0, 2));
        adj.add(List.of(2, 3, 4, 7));
        adj.add(List.of(1, 3, 5));
        adj.add(List.of(4, 5, 6));
        adj.add(List.of(6));
        graph.setAllNodes(nodes);
        graph.setAllEdges(edges);
        for (int i = 0; i < adj.size(); i++) {
            graph.setAssociatedEdgeIdxs(i, adj.get(i));
        }
        Pathfinder<Integer, Integer> pathfinder = new UnweightedShortPath<Integer>();
        PathInfoTriple<Integer> info = pathfinder.findAllSourcePaths(graph, 0);
        assertEquals(List.of(0, 1, 2, 1, 3, 4), info.getPathCosts());
        assertEquals(List.of(), info.getPathEdgeIdxs().get(0));
        assertEquals(List.of(0), info.getPathEdgeIdxs().get(1));
        assertTrue(info.getPathEdgeIdxs().get(2).equals(List.of(0, 2))
                || info.getPathEdgeIdxs().get(2).equals(List.of(1, 3)));
        assertEquals(List.of(1), info.getPathEdgeIdxs().get(3));
        assertTrue(info.getPathEdgeIdxs().get(4).equals(List.of(1, 3, 4))
                || info.getPathEdgeIdxs().get(4).equals(List.of(0, 2, 4)));
        assertTrue(info.getPathEdgeIdxs().get(5).equals(List.of(0, 2, 4, 6))
                || info.getPathEdgeIdxs().get(5).equals(List.of(1, 3, 4, 6)));
    }

    @Test
    public void directedEdgeShortestPathNoPathToNode_AllMatchSucceeds() {
        BasicGraph<Integer> graph = new BasicGraph<>();
        List<VersatileEdge<Integer>> edges = new ArrayList<>();
        List<Node> nodes = new ArrayList<>();
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            nodes.add(new BasicNode(i));
        }
        edges.add(new BasicEdge<Integer>(0, 0, 1, 1));
        edges.add(new BasicEdge<Integer>(1, 3, 0, 1));
        edges.add(new BasicEdge<Integer>(2, 1, 2, 1));
        edges.add(new BasicEdge<Integer>(3, 3, 2, 1));
        edges.add(new BasicEdge<Integer>(4, 2, 4, 1));
        edges.add(new BasicEdge<Integer>(5, 3, 4, 1));
        adj.add(List.of(0));
        adj.add(List.of(2));
        adj.add(List.of(4));
        adj.add(List.of(1, 3, 5));
        adj.add(List.of());
        graph.setAllNodes(nodes);
        graph.setAllEdges(edges);
        for (int i = 0; i < adj.size(); i++) {
            graph.setAssociatedEdgeIdxs(i, adj.get(i));
        }
        Pathfinder<Integer, Integer> pathfinder = new UnweightedShortPath<Integer>();
        PathInfoTriple<Integer> info = pathfinder.findAllSourcePaths(graph, 0);
        assertEquals(List.of(0, 1, 2, Integer.MAX_VALUE, 3), info.getPathCosts());
        assertEquals(List.of(), info.getPathEdgeIdxs().get(0));
        assertEquals(List.of(0), info.getPathEdgeIdxs().get(1));
        assertEquals(List.of(0, 2), info.getPathEdgeIdxs().get(2));
        assertEquals(List.of(-1), info.getPathEdgeIdxs().get(3));
        assertEquals(List.of(0, 2, 4), info.getPathEdgeIdxs().get(4));
        assertEquals(Integer.MAX_VALUE, info.getInfinityValue());
    }

}
