package ca.mcmaster.cas.se2aa4.a3.island.elevation;

public class LoftyElevationFactory extends AbstractElevationFactory {

    @Override
    public Elevation createElevationProfile() {
        return new LoftyElevation();
    }

}
