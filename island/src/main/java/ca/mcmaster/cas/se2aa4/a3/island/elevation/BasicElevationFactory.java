package ca.mcmaster.cas.se2aa4.a3.island.elevation;

/**
 * BasicElevationFactory
 */
public class BasicElevationFactory extends AbstractElevationFactory {

    @Override
    public Elevation createElevationProfile() {
        return new BasicElevation();
    }

}
