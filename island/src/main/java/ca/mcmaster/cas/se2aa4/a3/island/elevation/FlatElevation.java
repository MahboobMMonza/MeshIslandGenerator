package ca.mcmaster.cas.se2aa4.a3.island.elevation;

public class FlatElevation extends NormalElevation {

    public static final double ELEVATION_MODIFIER = 3;

    @Override
    double calculateDistanceDistribution(double x1, double y1, double x2, double y2) {
        return Math.pow((Math.pow(x2 - x1, 2.0) + Math.pow(y2 - y1, 2.0)), ELEVATION_MODIFIER);
    }
}
