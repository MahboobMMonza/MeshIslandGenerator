package ca.mcmaster.cas.se2aa4.a3.island.shaper;

public abstract class AbstractShaperFactory {

    public abstract ShapeFilter createShaper(int height, int width, long seed);
}
