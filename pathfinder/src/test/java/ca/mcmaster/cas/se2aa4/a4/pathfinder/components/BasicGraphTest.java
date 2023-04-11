package ca.mcmaster.cas.se2aa4.a4.pathfinder.components;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import java.util.*;

/**
 * BasicGraphTest
 */
public class BasicGraphTest {

    final static String KEY = "up";

    BasicGraph<Integer> basic;
    List<VersatileEdge<Integer>> simpleBiEdges;
    List<Node> allNodes;
    List<List<Integer>> assocEdges;

    @BeforeEach
    void setUp() {
        basic = new BasicGraph<>();
        simpleBiEdges = new ArrayList<>();
        allNodes = new ArrayList<>();
        assocEdges = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            allNodes.add(new BasicNode(i));
            allNodes.get(i).setProperty(KEY, i + 1);
        }
        simpleBiEdges.add(new BasicBiEdge<Integer>(0, 0, 1, 2));
        simpleBiEdges.add(new BasicBiEdge<Integer>(1, 0, 2, 2));
        simpleBiEdges.add(new BasicBiEdge<Integer>(2, 0, 3, 2));
        simpleBiEdges.add(new BasicBiEdge<Integer>(3, 1, 2, 2));
        simpleBiEdges.add(new BasicBiEdge<Integer>(4, 2, 3, 2));
        basic.setAllNodes(allNodes);
        basic.setAllEdges(simpleBiEdges);
        assocEdges.add(Arrays.asList(1, 2, 3));
        assocEdges.add(Arrays.asList(0, 2));
        assocEdges.add(Arrays.asList(0, 1, 3));
        assocEdges.add(Arrays.asList(0, 2));
        basic.setAssociatedEdgeIdxs(0, Arrays.asList(1, 2, 3));
        basic.setAssociatedEdgeIdxs(1, Arrays.asList(0, 2));
        basic.setAssociatedEdgeIdxs(2, Arrays.asList(0, 1, 3));
        basic.setAssociatedEdgeIdxs(3, Arrays.asList(0, 2));
    }

    @Test
    public void getAllNodes_AllMatchSucceeds() {
        // based on setup orientation, this also tests setAllNodes()
        List<Node> gNodes = basic.getAllNodes();
        assertEquals(4, basic.getNumNodes());
        for (int i = 0; i < allNodes.size(); i++) {
            assertEquals(allNodes.get(i).getIndex(), gNodes.get(i).getIndex());
            assertEquals(allNodes.get(i).getProperty(KEY), gNodes.get(i).getProperty(KEY));
        }
    }

    @Test
    public void getAllEdges_AllMatchSucceeds() {
        // based on setup orientation, this also tests setAllEdges()
        List<VersatileEdge<Integer>> gEdges = basic.getAllEdges();
        assertEquals(5, gEdges.size());
        for (int i = 0; i < simpleBiEdges.size(); i++) {
            assertEquals(simpleBiEdges.get(i).getIndex(), gEdges.get(i).getIndex());
            assertEquals(simpleBiEdges.get(i).getN1Idx(), gEdges.get(i).getN1Idx());
            assertEquals(simpleBiEdges.get(i).getN2Idx(), gEdges.get(i).getN2Idx());
            assertEquals(simpleBiEdges.get(i).getCost(simpleBiEdges.get(i).getN1Idx()).get(),
                    gEdges.get(i).getCost(gEdges.get(i).getN1Idx()).get());
            assertEquals(simpleBiEdges.get(i).getCost(simpleBiEdges.get(i).getN2Idx()).get(),
                    gEdges.get(i).getCost(gEdges.get(i).getN2Idx()).get());
        }
    }

    @Test
    public void newNodesClearNeighbours_AllEmptyListSucceeds() {
        allNodes.add(new BasicNode(5));
        basic.setAllNodes(allNodes);
        for (int i = 0; i < basic.numNodes; i++) {
            assertTrue(basic.getAssociatedEdgeIdxs(i).isEmpty());
        }
    }

    @Test
    public void getAssociatedEdgeIdxs_AllMatchSucceeds() {
        // Test to ensure that neighbourhood is set up correctly
        for (int i = 0; i < basic.numNodes; i++) {
            assertEquals(assocEdges.get(i), basic.getAssociatedEdgeIdxs(i));
        }
    }

}
