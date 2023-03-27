package ca.mcmaster.cas.se2aa4.a3.island.water.aquifer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BasicAquiferTest {
    @Test
    public void testConstructor() {
        BasicAquifer shaper = new BasicAquifer(4352353253253225L, 5);

        assertTrue(5 >= shaper.getNumAquifers());
    }
}