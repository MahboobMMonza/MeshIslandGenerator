package ca.mcmaster.cas.se2aa4.a3.island.shaper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class OvalShaperTest {

    @Test
    public void testConstructor() {
        int height = 10;
        int width = 20;
        double delta = 0.01;
        OvalShaper ovalShaper = new OvalShaper(height, width);
        assertEquals(width / 2.0, ovalShaper.getCentreX(), delta);
        assertEquals(height / 2.0, ovalShaper.getCentreY(), delta);
        assertEquals(height * OvalShaper.getGuaranteedModifier() / 2, ovalShaper.getGuaranteedRadiusY(), delta);
    }
}
