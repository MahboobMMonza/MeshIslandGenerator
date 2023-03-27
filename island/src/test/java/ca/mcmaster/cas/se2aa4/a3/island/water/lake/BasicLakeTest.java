package ca.mcmaster.cas.se2aa4.a3.island.water.lake;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BasicLakeTest {
    @Test
    public void testConstructor() {
        BasicLake shaper = new BasicLake(4352353253253225L, 12);

        assertTrue(12 >= shaper.getNumLakes());
    }
}