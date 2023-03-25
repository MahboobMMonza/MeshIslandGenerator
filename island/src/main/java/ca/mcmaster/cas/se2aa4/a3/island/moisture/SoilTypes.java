package ca.mcmaster.cas.se2aa4.a3.island.moisture;

public enum SoilTypes {
    FERTILE(1.75), NORMAL(1.0), POOR(0.4);

    public double getMultiplier() {
        return multiplier;
    }

    private final double multiplier;

    SoilTypes(double multiplier) {
        this.multiplier = multiplier;
    }
}
