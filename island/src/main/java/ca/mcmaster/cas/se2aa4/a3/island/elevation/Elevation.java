package ca.mcmaster.cas.se2aa4.a3.island.elevation;

import java.util.*;
import ca.mcmaster.cas.se2aa4.a3.island.components.ComponentCollections;

/**
 * Elevator
 */
public interface Elevation {

    public Map<Integer, ElevationLevels> elevateAllTiles(final ComponentCollections collection);
}
