package ca.mcmaster.cas.se2aa4.a3.island.shaper;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class RectangleShaperTest {
    @Test
    public void testGetIslandHeight() {
        RectangleShaper shaper = new RectangleShaper(10, 10, 123);
        double islandHeight = shaper.getIslandHeight();
        assertTrue(islandHeight >= 0.65 * 5 && islandHeight <= 0.8 * 5);
    }

    @Test
    public void testGetIslandWidth() {
        RectangleShaper shaper = new RectangleShaper(10, 10, 123);
        double islandWidth = shaper.getIslandWidth();
        assertTrue(islandWidth >= 0.65 * 5 && islandWidth <= 0.8 * 5);
    }
}
