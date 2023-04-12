package ca.mcmaster.cas.se2aa4.a4.pathfinder.pathfinders;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.components.*;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.utility.*;

import java.math.*;
import java.util.*;

/**
 * PosWeightedShortPathTest
 */
public class PosWeightedShortPathTest {

    @Test
    public void unsupportedClass_UnsupportedOperationExceptionSucceeds() {
        Exception exc = assertThrows(UnsupportedOperationException.class,
                () -> new PosWeightedShortPath<BigInteger>(BigInteger.class));
        String message = "Cannot create a PosWeightedGraph for versatile edges with cost type '"
                + BigInteger.class.getName()
                + "'.";
        assertEquals(message, exc.getMessage());
    }

    @Test
    public void nullClass_UnsupportedOperationExceptionSucceeds() {
        Exception exc = assertThrows(IllegalArgumentException.class, () -> new PosWeightedShortPath<BigInteger>(null));
        String message = "Cannot create a PosWeightedGraph with a given null data class.";
        assertEquals(message, exc.getMessage());
    }

    @Test
    public void floatingPointGraph_AllMatchSucceeds() {
        BasicGraph<Double> graph = new BasicGraph<>();
        List<VersatileEdge<Double>> edges = new ArrayList<>();
        List<Node> nodes = new ArrayList<>();
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            nodes.add(new BasicNode(i));
        }
        edges.add(new BasicEdge<Double>(0, 0, 4, 100.54));
        edges.add(new BasicEdge<Double>(1, 0, 3, 30.79));
        edges.add(new BasicEdge<Double>(2, 3, 4, 60.25));
        edges.add(new BasicEdge<Double>(3, 3, 2, 20.11));
        edges.add(new BasicEdge<Double>(4, 0, 1, 10.34));
        edges.add(new BasicEdge<Double>(5, 1, 2, 50d));
        edges.add(new BasicEdge<Double>(6, 2, 4, 10d));
        adj.add(List.of(0, 1, 4));
        adj.add(List.of(5));
        adj.add(List.of(6));
        adj.add(List.of(2, 3));
        adj.add(List.of());
        graph.setAllNodes(nodes);
        graph.setAllEdges(edges);
        for (int i = 0; i < 5; i++) {
            graph.setAssociatedEdgeIdxs(i, adj.get(i));
        }
        Pathfinder<Double, Double> pathfinder = new PosWeightedShortPath<Double>(Double.class);
        PathInfoTriple<Double> info = pathfinder.findAllSourcePaths(graph, 0);
        assertEquals(0, info.getPathCosts().get(0));
        assertEquals(10.34, info.getPathCosts().get(1), 1e-6);
        assertEquals(30.79 + 20.11, info.getPathCosts().get(2), 1e-6);
        assertEquals(30.79, info.getPathCosts().get(3), 1e-6);
        assertEquals(30.79 + 20.11 + 10, info.getPathCosts().get(4), 1e-6);
        assertEquals(List.of(), info.getPathEdgeIdxs().get(0));
        assertEquals(List.of(4), info.getPathEdgeIdxs().get(1));
        assertEquals(List.of(1, 3), info.getPathEdgeIdxs().get(2));
        assertEquals(List.of(1), info.getPathEdgeIdxs().get(3));
        assertEquals(List.of(1, 3, 6), info.getPathEdgeIdxs().get(4));
        assertEquals(Double.POSITIVE_INFINITY, info.getInfinityValue());
    }

    @Test
    public void unreachableNode_InfiniteCostAllMatchSucceeds() {
        Graph<Long> graph = new BasicGraph<>();
        List<Node> nodes = new ArrayList<>();
        List<VersatileEdge<Long>> edges = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            nodes.add(new BasicNode(i));
        }
        graph.setAllNodes(nodes);
        graph.setAllEdges(edges);
        Pathfinder<Long, Long> pathfinder = new PosWeightedShortPath<Long>(Long.class);
        PathInfoTriple<Long> info = pathfinder.findAllSourcePaths(graph, 1);
        assertEquals(List.of(Long.MAX_VALUE, 0L), info.getPathCosts());
        assertEquals(List.of(-1), info.getPathEdgeIdxs().get(0));
        assertEquals(List.of(), info.getPathEdgeIdxs().get(1));
        assertEquals(Long.MAX_VALUE, info.getInfinityValue());
    }

    @Test
    public void bidirectionalEdgeGraph_AllMatchSucceeds() {
        BasicGraph<Integer> graph = new BasicGraph<>();
        List<VersatileEdge<Integer>> edges = new ArrayList<>();
        List<Node> nodes = new ArrayList<>();
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            nodes.add(new BasicNode(i));
        }
        edges.add(new BasicBiEdge<>(0, 0, 1, 4));
        edges.add(new BasicBiEdge<>(1, 0, 7, 8));
        edges.add(new BasicBiEdge<>(2, 1, 7, 11));
        edges.add(new BasicBiEdge<>(3, 1, 2, 8));
        edges.add(new BasicBiEdge<>(4, 8, 7, 7));
        edges.add(new BasicBiEdge<>(5, 8, 6, 6));
        edges.add(new BasicBiEdge<>(6, 8, 2, 2));
        edges.add(new BasicBiEdge<>(7, 3, 2, 7));
        edges.add(new BasicBiEdge<>(8, 5, 2, 4));
        edges.add(new BasicBiEdge<>(9, 5, 6, 2));
        edges.add(new BasicBiEdge<>(10, 5, 3, 14));
        edges.add(new BasicBiEdge<>(11, 5, 4, 10));
        edges.add(new BasicBiEdge<>(12, 3, 4, 9));
        edges.add(new BasicBiEdge<>(13, 7, 6, 1));
        adj.add(List.of(0, 1));
        adj.add(List.of(0, 2, 3));
        adj.add(List.of(3, 6, 7, 8));
        adj.add(List.of(7, 10));
        adj.add(List.of(11, 12));
        adj.add(List.of(8, 9, 10, 11));
        adj.add(List.of(5, 9, 13));
        adj.add(List.of(1, 2, 4, 13));
        adj.add(List.of(4, 5, 6));
        graph.setAllNodes(nodes);
        graph.setAllEdges(edges);
        for (int i = 0; i < 9; i++) {
            graph.setAssociatedEdgeIdxs(i, adj.get(i));
        }
        Pathfinder<Integer, Integer> pathfinder = new PosWeightedShortPath<Integer>(Integer.class);
        PathInfoTriple<Integer> info = pathfinder.findAllSourcePaths(graph, 0);
        assertEquals(List.of(0, 4, 12, 19, 21, 11, 9, 8, 14), info.getPathCosts());
        assertEquals(List.of(), info.getPathEdgeIdxs().get(0));
        assertEquals(List.of(0), info.getPathEdgeIdxs().get(1));
        assertEquals(List.of(0, 3), info.getPathEdgeIdxs().get(2));
        assertEquals(List.of(0, 3, 7), info.getPathEdgeIdxs().get(3));
        assertEquals(List.of(1, 13, 9, 11), info.getPathEdgeIdxs().get(4));
        assertEquals(List.of(1, 13, 9), info.getPathEdgeIdxs().get(5));
        assertEquals(List.of(1, 13), info.getPathEdgeIdxs().get(6));
        assertEquals(List.of(1), info.getPathEdgeIdxs().get(7));
        assertEquals(List.of(0, 3, 6), info.getPathEdgeIdxs().get(8));
    }

    @Test
    public void emptyGraph_AllEmptySucceeds() {
        Graph<Integer> graph = new BasicGraph<>();
        Pathfinder<Integer, Integer> pathfinder = new PosWeightedShortPath<>(Integer.class);
        PathInfoTriple<Integer> info = pathfinder.findAllSourcePaths(graph, 0);
        assertTrue(info.getPathEdgeIdxs().isEmpty());
        assertTrue(info.getPathCosts().isEmpty());
    }

    @Test
    public void negativeEdgeWeight_IllegalArgumentExceptionSucceeds() {
        BasicGraph<Integer> graph = new BasicGraph<>();
        List<VersatileEdge<Integer>> edges = new ArrayList<>();
        List<Node> nodes = new ArrayList<>();
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            nodes.add(new BasicNode(i));
        }
        edges.add(new BasicEdge<Integer>(0, 0, 4, 100));
        edges.add(new BasicEdge<Integer>(1, 0, 3, 30));
        edges.add(new BasicEdge<Integer>(2, 3, 4, 60));
        edges.add(new BasicEdge<Integer>(3, 3, 2, 20));
        edges.add(new BasicEdge<Integer>(4, 0, 1, -10));
        edges.add(new BasicEdge<Integer>(5, 1, 2, 50));
        edges.add(new BasicEdge<Integer>(6, 2, 4, 10));
        adj.add(List.of(0, 1, 4));
        adj.add(List.of(5));
        adj.add(List.of(6));
        adj.add(List.of(2, 3));
        adj.add(List.of());
        graph.setAllNodes(nodes);
        graph.setAllEdges(edges);
        for (int i = 0; i < 5; i++) {
            graph.setAssociatedEdgeIdxs(i, adj.get(i));
        }
        Exception exc = assertThrows(IllegalArgumentException.class,
                () -> new PosWeightedShortPath<Integer>(Integer.class).findAllSourcePaths(graph, 0));
        String message = "The given edge list contains negatively weighted edges.";
        assertEquals(message, exc.getMessage());
    }

    @Test
    public void directedNullCostAdj_AllMatchSucceeds() {
        BasicGraph<Integer> graph = new BasicGraph<>();
        List<VersatileEdge<Integer>> edges = new ArrayList<>();
        List<Node> nodes = new ArrayList<>();
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            nodes.add(new BasicNode(i));
        }
        edges.add(new BasicEdge<Integer>(0, 0, 4, 100));
        edges.add(new BasicEdge<Integer>(1, 0, 3, 30));
        edges.add(new BasicEdge<Integer>(2, 3, 4, 60));
        edges.add(new BasicEdge<Integer>(3, 3, 2, 20));
        edges.add(new BasicEdge<Integer>(4, 0, 1, 10));
        edges.add(new BasicEdge<Integer>(5, 1, 2, 50));
        edges.add(new BasicEdge<Integer>(6, 2, 4, 10));
        adj.add(List.of(0, 1, 4));
        adj.add(List.of(5, 4));
        adj.add(List.of(5, 6, 3));
        adj.add(List.of(1, 2, 3));
        adj.add(List.of(0, 2, 6));
        graph.setAllNodes(nodes);
        graph.setAllEdges(edges);
        for (int i = 0; i < 5; i++) {
            graph.setAssociatedEdgeIdxs(i, adj.get(i));
        }
        Pathfinder<Integer, Integer> pathfinder = new PosWeightedShortPath<Integer>(Integer.class);
        PathInfoTriple<Integer> info = pathfinder.findAllSourcePaths(graph, 0);
        assertEquals(0, info.getPathCosts().get(0));
        assertEquals(10, info.getPathCosts().get(1));
        assertEquals(50, info.getPathCosts().get(2));
        assertEquals(30, info.getPathCosts().get(3));
        assertEquals(60, info.getPathCosts().get(4));
        assertEquals(List.of(), info.getPathEdgeIdxs().get(0));
        assertEquals(List.of(4), info.getPathEdgeIdxs().get(1));
        assertEquals(List.of(1, 3), info.getPathEdgeIdxs().get(2));
        assertEquals(List.of(1), info.getPathEdgeIdxs().get(3));
        assertEquals(List.of(1, 3, 6), info.getPathEdgeIdxs().get(4));
        assertEquals(Integer.MAX_VALUE, info.getInfinityValue());
    }

    @Test
    public void directedGraph_AllMatchSucceeds() {
        BasicGraph<Integer> graph = new BasicGraph<>();
        List<VersatileEdge<Integer>> edges = new ArrayList<>();
        List<Node> nodes = new ArrayList<>();
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            nodes.add(new BasicNode(i));
        }
        edges.add(new BasicEdge<Integer>(0, 0, 4, 100));
        edges.add(new BasicEdge<Integer>(1, 0, 3, 30));
        edges.add(new BasicEdge<Integer>(2, 3, 4, 60));
        edges.add(new BasicEdge<Integer>(3, 3, 2, 20));
        edges.add(new BasicEdge<Integer>(4, 0, 1, 10));
        edges.add(new BasicEdge<Integer>(5, 1, 2, 50));
        edges.add(new BasicEdge<Integer>(6, 2, 4, 10));
        adj.add(List.of(0, 1, 4));
        adj.add(List.of(5));
        adj.add(List.of(6));
        adj.add(List.of(2, 3));
        adj.add(List.of());
        graph.setAllNodes(nodes);
        graph.setAllEdges(edges);
        for (int i = 0; i < 5; i++) {
            graph.setAssociatedEdgeIdxs(i, adj.get(i));
        }
        Pathfinder<Integer, Integer> pathfinder = new PosWeightedShortPath<Integer>(Integer.class);
        PathInfoTriple<Integer> info = pathfinder.findAllSourcePaths(graph, 0);
        assertEquals(0, info.getPathCosts().get(0));
        assertEquals(10, info.getPathCosts().get(1));
        assertEquals(50, info.getPathCosts().get(2));
        assertEquals(30, info.getPathCosts().get(3));
        assertEquals(60, info.getPathCosts().get(4));
        assertEquals(List.of(), info.getPathEdgeIdxs().get(0));
        assertEquals(List.of(4), info.getPathEdgeIdxs().get(1));
        assertEquals(List.of(1, 3), info.getPathEdgeIdxs().get(2));
        assertEquals(List.of(1), info.getPathEdgeIdxs().get(3));
        assertEquals(List.of(1, 3, 6), info.getPathEdgeIdxs().get(4));
        assertEquals(Integer.MAX_VALUE, info.getInfinityValue());
    }

}
