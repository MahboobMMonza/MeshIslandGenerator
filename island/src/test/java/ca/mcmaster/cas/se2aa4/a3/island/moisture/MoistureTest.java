package ca.mcmaster.cas.se2aa4.a3.island.moisture;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.Test;

public class MoistureTest {

    @Test
    public void testNormalGetMoistureRanges() {
        Map<Double, MoistureLevels> ranges = NormalMoisture.getMoistureRanges();
        assertEquals(MoistureLevels.OCEAN_MOISTURE, ranges.get(-3.0));
        assertEquals(MoistureLevels.LAGOON_MOISTURE, ranges.get(-2.0));
        assertEquals(MoistureLevels.WATER_MOISTURE, ranges.get(-1.0));
        assertEquals(MoistureLevels.LOW_MOISTURE, ranges.get(0.4));
        assertEquals(MoistureLevels.WET_MOISTURE, ranges.get(0.1));
        assertEquals(MoistureLevels.HUMID_MOISTURE, ranges.get(0.2));
        assertEquals(MoistureLevels.DRY_MOISTURE, ranges.get(Double.MAX_VALUE));

    }
}