package ca.mcmaster.cas.se2aa4.a3.island.elevation;

import java.util.*;

public class SteepElevation extends NormalElevation {

    @Override
    public Map<Double, ElevationLevels> getElevationRanges() {
        // This map is pseudo constant but declared this way for inheritance
        Map<Double, ElevationLevels> ELEVATION_RANGES = new TreeMap<>();
        ELEVATION_RANGES.put(-3.0, ElevationLevels.OCEAN_ELEVATION);
        ELEVATION_RANGES.put(-2.0, ElevationLevels.LAGOON_ELEVATION);
        ELEVATION_RANGES.put(-1.0, ElevationLevels.WATER_ELEVATION);
        ELEVATION_RANGES.put(0.65, ElevationLevels.LOW_ELEVATION);
        ELEVATION_RANGES.put(0.84, ElevationLevels.MEDIUM_ELEVATION);
        ELEVATION_RANGES.put(Double.MAX_VALUE, ElevationLevels.HIGH_ELEVATION);
        return ELEVATION_RANGES;
    }

}
