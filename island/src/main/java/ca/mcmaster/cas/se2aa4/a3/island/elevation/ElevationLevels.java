package ca.mcmaster.cas.se2aa4.a3.island.elevation;

/**
 * ElevationLevels
 */
public enum ElevationLevels {
    OCEAN_ELEVATION(-3), LAGOON_ELEVATION(-2), WATER_ELEVATION(-1), LOW_ELEVATION(0), MEDIUM_ELEVATION(1),
    HIGH_ELEVATION(2);

    public int getElevationID() {
        return elevationID;
    }

    private final int elevationID;

    ElevationLevels(int elevationID) {
        this.elevationID = elevationID;
    }
}
