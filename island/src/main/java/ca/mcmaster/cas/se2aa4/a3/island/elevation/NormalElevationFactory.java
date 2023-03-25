package ca.mcmaster.cas.se2aa4.a3.island.elevation;

/**
 * NormalElevationFactory
 */
public class NormalElevationFactory extends AbstractElevationFactory {

    @Override
    public Elevation createElevationProfile() {
        return new NormalElevation();
    }

}
