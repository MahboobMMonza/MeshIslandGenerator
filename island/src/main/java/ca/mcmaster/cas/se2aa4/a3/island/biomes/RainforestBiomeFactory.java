package ca.mcmaster.cas.se2aa4.a3.island.biomes;

/**
 * RainforestBiomeFactory
 */
public class RainforestBiomeFactory extends AbstractBiomeFactory {

    @Override
    public Biome createBiome() {
        return new RainforestBiome();
    }


}
