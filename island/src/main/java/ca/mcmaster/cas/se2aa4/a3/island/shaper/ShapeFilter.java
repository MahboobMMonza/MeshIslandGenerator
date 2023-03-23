package ca.mcmaster.cas.se2aa4.a3.island.shaper;

import java.util.Map;

import ca.mcmaster.cas.se2aa4.a3.island.components.*;

/**
 * ShapeFilter gives a value to subtract from some base noise
 */
public interface ShapeFilter {

    public void shapeAllTiles(final ComponentCollections collection);
}
