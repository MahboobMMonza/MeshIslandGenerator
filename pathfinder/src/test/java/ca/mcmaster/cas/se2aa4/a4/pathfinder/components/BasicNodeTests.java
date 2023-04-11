package ca.mcmaster.cas.se2aa4.a4.pathfinder.components;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

/**
 * BasicNodeTests
 */
public class BasicNodeTests {

    private BasicNode node;

    @BeforeEach
    public void setUp() {
        node = new BasicNode(2);
    }

    @Test
    public void getIndex_IndexMatchSucceeds() {
        assertEquals(2, node.getIndex());
    }

    @Test
    public void setIndex_IndexMatchSucceeds() {
        node.setIndex(5);
        assertEquals(5, node.getIndex());
    }

    @Test
    public void invalidIndex_IllegalArgumentExceptionSucceeds() {
        Exception exc = assertThrows(IllegalArgumentException.class, () -> node.setIndex(-1));
        String expectedMessage = "Index of a node cannot be set to a negative number.";
        assertEquals(expectedMessage, exc.getMessage());
    }

    @Test
    public void nodeProperties_PropertyValueMatchSucceeds() {
        String key = "name", value = "Bob";
        node.setProperty(key, value);
        assertEquals(value, node.getProperty(key));
    }

}
