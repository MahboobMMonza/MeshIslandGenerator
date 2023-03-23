package ca.mcmaster.cas.se2aa4.a3.island.moisture;

public enum MoistureLevels {
    OCEAN_MOISTURE(-3), LAGOON_MOISTURE(-2), WATER_MOISTURE(-1), DRY_MOISTURE(0),LOW_MOISTURE(1), 
    HUMID_MOISTURE(2), WET_MOISTURE(3);

    public int getMoistureID() {
        return moistureID;
    }

    private final int moistureID;

    MoistureLevels(int moistureID) {
        this.moistureID = moistureID;
    }
}
