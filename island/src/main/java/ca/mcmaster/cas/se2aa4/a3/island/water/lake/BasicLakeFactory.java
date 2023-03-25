package ca.mcmaster.cas.se2aa4.a3.island.water.lake;

public class BasicLakeFactory extends AbstractLakeFactory {

    @Override
    public Lake createLake(long seed, int maxLakes) {
        return new BasicLake(seed, maxLakes);
    }

}
