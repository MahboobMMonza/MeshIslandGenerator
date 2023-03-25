package ca.mcmaster.cas.se2aa4.a3.island.water.aquifer;

public class BasicAquiferFactory extends AbstractAquiferFactory {

    @Override
    public Aquifer createAquifer(long seed, int maxAquifer) {
        return new BasicAquifer(seed, maxAquifer);
    }
}
