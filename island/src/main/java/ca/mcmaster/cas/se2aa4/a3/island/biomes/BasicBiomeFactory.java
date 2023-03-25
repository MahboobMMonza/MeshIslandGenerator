package ca.mcmaster.cas.se2aa4.a3.island.biomes;

/**
 * BasicBiomeFactory
 */
public class BasicBiomeFactory extends AbstractBiomeFactory {

    @Override
    public Biome createBiome() {
        return new BasicBiome();
    }


}
