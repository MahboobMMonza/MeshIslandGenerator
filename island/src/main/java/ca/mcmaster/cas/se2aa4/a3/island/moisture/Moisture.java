package ca.mcmaster.cas.se2aa4.a3.island.moisture;

import java.util.Map;

import ca.mcmaster.cas.se2aa4.a3.island.components.ComponentCollections;

/**
 * Moisture
 */
public interface Moisture {

    public Map<Integer, MoistureLevels> moisturizeAllTiles(final ComponentCollections collection);
}
