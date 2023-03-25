package ca.mcmaster.cas.se2aa4.a3.island.shaper;

public class RoundShaperFactory extends AbstractShaperFactory {

    @Override
    public ShapeFilter createShaper(int height, int width, long seed) {
        return new RoundShaper(height, width, seed);
    }

}
