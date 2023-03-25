package ca.mcmaster.cas.se2aa4.a3.island.elevation;

/**
 * SteepElevationFactory
 */
public class SteepElevationFactory extends AbstractElevationFactory {

    @Override
    public Elevation createElevationProfile() {
        return new SteepElevation();
    }

}
