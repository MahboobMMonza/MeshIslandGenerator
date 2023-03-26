package ca.mcmaster.cas.se2aa4.a3.island.biomes;

public class DesertBiomeFactory extends AbstractBiomeFactory {
    @Override
    public Biome createBiome() {
        return new DesertBiome();
    }

}
