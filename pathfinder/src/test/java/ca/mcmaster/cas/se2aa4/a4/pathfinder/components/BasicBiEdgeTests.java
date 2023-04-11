package ca.mcmaster.cas.se2aa4.a4.pathfinder.components;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import java.util.*;

/**
 * BasicBiEdgeTests
 */
public class BasicBiEdgeTests {

    private BasicBiEdge<Integer> basic;

    @BeforeEach
    public void setUp() {
        basic = new BasicBiEdge<>(3, 1, 2, 5);
    }

    @Test
    public void getEdgeIndex_EdgeIndexMatchSucceeds() {
        assertEquals(3, basic.getIndex());
    }

    @Test
    public void getN1Idx_N1MatchSucceeds() {
        assertEquals(1, basic.getN1Idx());
    }

    @Test
    public void getN2Idx_N2MatchSucceeds() {
        assertEquals(2, basic.getN2Idx());
    }

    @Test
    public void getN1Cost_CostMatchSucceeds() {
        Optional<Integer> cost = basic.getCost(basic.getN1Idx());
        assertEquals(5, cost.get());
    }

    @Test
    public void getN2Cost_CostMatchSucceeds() {
        Optional<Integer> cost = basic.getCost(basic.getN2Idx());
        assertEquals(5, cost.get());
    }

    @Test
    public void getSameBidirectionalCost_CostMatchSucceeds() {
        Optional<Integer> cost1 = basic.getCost(basic.getN1Idx()), cost2 = basic.getCost(basic.getN2Idx());
        assertTrue(cost1.get().equals(5) && cost1.get().equals(cost2.get()));
    }

    @Test
    public void setEdgeIndex_EdgeIndexMatchSucceeds() {
        basic.setIndex(4);
        assertEquals(4, basic.getIndex());
    }

    @Test
    public void setN1Idx_N1MatchSucceeds() {
        basic.setN1Idx(4);
        assertEquals(4, basic.getN1Idx());
    }

    @Test
    public void setN2Idx_N2MatchSucceeds() {
        basic.setN2Idx(4);
        assertEquals(4, basic.getN2Idx());
    }

    @Test
    public void updateCostN1_N1CostMatchSucceeds() {
        basic.setCost(basic.getN1Idx(), 4);
        Optional<Integer> cost = basic.getCost(basic.getN1Idx());
        assertEquals(4, cost.get());
    }

    @Test
    public void updateCostN1_N2CostMatchSucceeds() {
        basic.setCost(basic.getN1Idx(), 4);
        Optional<Integer> cost = basic.getCost(basic.getN2Idx());
        assertEquals(4, cost.get());
    }

    @Test
    public void updateCostN2_N1CostMatchSucceeds() {
        basic.setCost(basic.getN2Idx(), 4);
        Optional<Integer> cost = basic.getCost(basic.getN1Idx());
        assertEquals(4, cost.get());
    }

    @Test
    public void updateCostN2_N2CostMatchSucceeds() {
        basic.setCost(basic.getN2Idx(), 4);
        Optional<Integer> cost = basic.getCost(basic.getN2Idx());
        assertEquals(4, cost.get());
    }

    @Test
    public void updateNullCost_OptionalEmptySucceeds() {
        // Since bidirectional cost matching works, this one test will confirm all
        // necessary information about null costs
        basic.setCost(basic.getN2Idx(), null);
        Optional<Integer> cost = basic.getCost(basic.getN1Idx());
        assertTrue(cost.isEmpty());
    }

    @Test
    public void invalidIndex_IllegalArgumentExceptionSucceeds() {
        Exception exc = assertThrows(IllegalArgumentException.class, () -> basic.setIndex(-1));
        String expectedMessage = "Cannot change index of an edge to a negative number.";
        assertTrue(expectedMessage.equals(exc.getMessage()));
    }

    @Test
    public void invalidN1Index_IllegalArgumentExceptionSucceeds() {
        Exception exc = assertThrows(IllegalArgumentException.class, () -> basic.setN1Idx(-1));
        String expectedMessage = "Cannot change index of a node to a negative number.";
        assertTrue(expectedMessage.equals(exc.getMessage()));
    }

    @Test
    public void invalidN2Index_IllegalArgumentExceptionSucceeds() {
        Exception exc = assertThrows(IllegalArgumentException.class, () -> basic.setN2Idx(-1));
        String expectedMessage = "Cannot change index of a node to a negative number.";
        assertTrue(expectedMessage.equals(exc.getMessage()));
    }

    @Test
    public void invalidGetCostIndex_IllegalArgumentExceptionSucceeds() {
        Exception exc = assertThrows(IllegalArgumentException.class, () -> basic.getCost(7));
        String expectedMessage = "The given node does not match the nodes connected by this edge.";
        assertTrue(expectedMessage.equals(exc.getMessage()));
    }

}
