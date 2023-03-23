package ca.mcmaster.cas.se2aa4.a3.island.noise;

import ca.mcmaster.cas.se2aa4.a3.island.components.ComponentCollections;
import ca.mcmaster.cas.se2aa4.a3.island.components.Tile;
import ca.mcmaster.cas.se2aa4.a3.island.components.TileTypes;

/**
 * NoiseFunction interface guarantees
 */
public interface NoiseTiler {

    public void overwriteTileTypes(final ComponentCollections collection);
}
