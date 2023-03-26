package ca.mcmaster.cas.se2aa4.a3.island.shaper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class RoundShaperTest {
    @Test
    public void testConstructor() {
        RoundShaper shaper = new RoundShaper(10, 20);
        assertEquals(10.0 / 2.0, shaper.getCentreY());
        assertEquals(20.0 / 2.0, shaper.getCentreX());
        assertEquals(5.5 / 2.0, shaper.getGuaranteedRadius());
    }

    @Test
    public void testConstructorWithSeed() {
        RoundShaper shaper = new RoundShaper(10, 20, 123);
        assertEquals(10.0 / 2.0, shaper.getCentreY());
        assertEquals(20.0 / 2.0, shaper.getCentreX());
        assertFalse(shaper.getGuaranteedRadius() >= 5.5 && shaper.getGuaranteedRadius() <= 8.0);
    }

}
