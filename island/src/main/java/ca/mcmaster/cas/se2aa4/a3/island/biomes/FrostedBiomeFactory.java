package ca.mcmaster.cas.se2aa4.a3.island.biomes;

/**
 * FrostedBiomeFactory
 */
public class FrostedBiomeFactory extends AbstractBiomeFactory {

    @Override
    public Biome createBiome() {
        return new FrostedBiome();
    }


}
