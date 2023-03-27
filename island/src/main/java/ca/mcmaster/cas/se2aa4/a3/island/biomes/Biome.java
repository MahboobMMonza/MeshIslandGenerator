package ca.mcmaster.cas.se2aa4.a3.island.biomes;

import java.util.*;
import ca.mcmaster.cas.se2aa4.a3.island.components.ComponentCollections;

/**
 * Biome
 */
public interface Biome {

    public Map<Integer, Integer> assignTileBiomeColours(final ComponentCollections collection);

    public Map<Integer, Integer> assignEdgeBiomeColours(final ComponentCollections collection);
}
