package ca.mcmaster.cas.se2aa4.a2.elevation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.Test;

import ca.mcmaster.cas.se2aa4.a3.island.elevation.*;

public class ElevationTest {

    @Test
    public void testNormalAssignLevel() {
        NormalElevation elev = new NormalElevation();
        Map<Double, ElevationLevels> ranges = elev.getElevationRanges();

        assertEquals(ElevationLevels.OCEAN_ELEVATION, ranges.get(-3.0));
        assertEquals(ElevationLevels.LAGOON_ELEVATION, ranges.get(-2.0));
        assertEquals(ElevationLevels.WATER_ELEVATION, ranges.get(-1.0));
        assertEquals(ElevationLevels.LOW_ELEVATION, ranges.get(1.0 / 3.0));
        assertEquals(ElevationLevels.MEDIUM_ELEVATION, ranges.get(2.0 / 3.0));
        assertEquals(ElevationLevels.HIGH_ELEVATION, ranges.get(Double.MAX_VALUE));
    }

    @Test
    public void testSteepAssignLevel() {
        SteepElevation elevation = new SteepElevation();
        Map<Double, ElevationLevels> ranges = elevation.getElevationRanges();

        assertEquals(ElevationLevels.OCEAN_ELEVATION, ranges.get(-3.0));
        assertEquals(ElevationLevels.LAGOON_ELEVATION, ranges.get(-2.0));
        assertEquals(ElevationLevels.WATER_ELEVATION, ranges.get(-1.0));
        assertEquals(ElevationLevels.LOW_ELEVATION, ranges.get(0.65));
        assertEquals(ElevationLevels.MEDIUM_ELEVATION, ranges.get(0.84));
        assertEquals(ElevationLevels.HIGH_ELEVATION, ranges.get(Double.MAX_VALUE));
    }
}