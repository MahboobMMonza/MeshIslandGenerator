package ca.mcmaster.cas.se2aa4.a3.island.elevation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.TreeMap;

import org.junit.jupiter.api.Test;

public class ElevationTest {

    @Test
    public void testNormalGetElevationRanges() {
        TreeMap<Double, ElevationLevels> ranges = NormalElevation.ELEVATION_RANGES;
        assertEquals(ElevationLevels.OCEAN_ELEVATION, ranges.get(-3.0));
        assertEquals(ElevationLevels.LAGOON_ELEVATION, ranges.get(-2.0));
        assertEquals(ElevationLevels.WATER_ELEVATION, ranges.get(-1.0));
        assertEquals(ElevationLevels.LOW_ELEVATION, ranges.get(1.0 / 3.0));
        assertEquals(ElevationLevels.MEDIUM_ELEVATION, ranges.get(2.0 / 3.0));
        assertEquals(ElevationLevels.HIGH_ELEVATION, ranges.get(Double.MAX_VALUE));
    }

}
