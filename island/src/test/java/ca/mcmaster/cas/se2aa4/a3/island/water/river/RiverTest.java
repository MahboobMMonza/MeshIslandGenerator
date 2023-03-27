package ca.mcmaster.cas.se2aa4.a3.island.water.river;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class RiverTest {
    @Test
    public void testConstructor() {
        River shaper = new River(4352353253253225L, 3);

        assertTrue(3 >= shaper.getNumRivers());
    }
}