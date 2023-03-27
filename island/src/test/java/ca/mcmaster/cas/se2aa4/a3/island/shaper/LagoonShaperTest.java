package ca.mcmaster.cas.se2aa4.a3.island.shaper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class LagoonShaperTest {
    @Test
    public void testLagoonShaperConstructor() {
        int height = 10;
        int width = 20;
        LagoonShaper shaper = new LagoonShaper(height, width);
        assertEquals(shaper.getCentreY(), height / 2.0, 0.0);
        assertEquals(shaper.getCentreX(), width / 2.0, 0.0);
        assertEquals(shaper.getOuterRadius(), Math.min(width / 2.0, height / 2.0) * LagoonShaper.OUTER_MODIFIER, 0.0);
        assertEquals(shaper.getInnerRadius(), Math.min(width / 2.0, height / 2.0) * LagoonShaper.INNER_MODIFIER, 0.0);
    }

    @Test
    public void testLagoonShaperConstructorWithSeed() {
        int height = 10;
        int width = 20;
        long seed = 12345L;
        LagoonShaper shaper = new LagoonShaper(height, width, seed);
        assertEquals(shaper.getCentreY(), height / 2.0, 0.0);
        assertEquals(shaper.getCentreX(), width / 2.0, 0.0);
        assertEquals(shaper.getOuterRadius(), Math.min(width / 2.0, height / 2.0) * LagoonShaper.OUTER_MODIFIER, 0.0);
    }
}
