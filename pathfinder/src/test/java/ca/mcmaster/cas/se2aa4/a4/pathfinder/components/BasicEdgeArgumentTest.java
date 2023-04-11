package ca.mcmaster.cas.se2aa4.a4.pathfinder.components;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import java.util.*;

/**
 * BasicEdgeArgumentTest
 */
public class BasicEdgeArgumentTest {

    private BasicEdge<Integer> basic;

    @BeforeEach
    public void setUp() {
        basic = new BasicEdge<>(3, 1, 2, 5);
    }

    @Test
    public void getEdgeIndex_EdgeIndexMatchSucceeds() {
        assertEquals(basic.getIndex(), 3);
    }

    @Test
    public void getN1Idx_SourceMatchSucceeds() {
        assertEquals(basic.getN1Idx(), 1);
    }

    @Test
    public void getN2Idx_DestMatchSucceeds() {
        assertEquals(basic.getN2Idx(), 2);
    }

    @Test
    public void getSourceCost_CostMatchSucceeds() {
        Optional<Integer> cost = basic.getCost(basic.getN1Idx());
        assertEquals(cost.get(), 5);
    }

    @Test
    public void getDestCost_OptionalEmptySucceeds() {
        Optional<Integer> cost = basic.getCost(basic.getN2Idx());
        assertTrue(cost.isEmpty());
    }

    @Test
    public void setEdgeIndex_EdgeIndexMatchSucceeds() {
        basic.setIndex(4);
        assertEquals(basic.getIndex(), 4);
    }

    @Test
    public void setN1Idx_SourceMatchSucceeds() {
        basic.setN1Idx(4);
        assertEquals(basic.getN1Idx(), 4);
    }

    @Test
    public void setN2Idx_DestMatchSucceeds() {
        basic.setN2Idx(4);
        assertEquals(basic.getN2Idx(), 4);
    }

    @Test
    public void updateCost_SourceCostMatchSucceeds() {
        basic.setCost(1, 4);
        Optional<Integer> cost = basic.getCost(basic.getN1Idx());
        assertEquals(cost.get(), 4);
    }

    @Test
    public void updateCost_DestCostOptionalEmptyMatchSucceeds() {
        basic.setCost(1, 4);
        Optional<Integer> cost = basic.getCost(basic.getN2Idx());
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
    public void invalidGetCostSourceIndex_IllegalArgumentExceptionSucceeds() {
        Exception exc = assertThrows(IllegalArgumentException.class, () -> basic.getCost(7));
        String expectedMessage = "The given node does not match the nodes connected by this edge.";
        assertTrue(expectedMessage.equals(exc.getMessage()));
    }

    @Test
    public void invalidSetCostSourceAsDestIndex_IllegalArgumentExceptionMessageSucceeds() {
        Exception exc = assertThrows(IllegalArgumentException.class, () -> basic.setCost(2, 5));
        String expectedMessage = "Cannot set a cost for source node at N2Idx since this node is soley interpreted as a destination.";
        assertTrue(expectedMessage.equals(exc.getMessage()));
    }

    @Test
    public void invalidSetCostSourceNotMatchingIndex_IllegalArgumentExceptionMessageSucceeds() {
        Exception exc = assertThrows(IllegalArgumentException.class, () -> basic.setCost(3, 5));
        String expectedMessage = "The given node does not match the nodes connected by this edge.";
        assertTrue(expectedMessage.equals(exc.getMessage()));
    }

}
