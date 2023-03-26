package ca.mcmaster.cas.se2aa4.a3.island.water;

import java.util.*;

/**
 * WaterUtil
 */
public class WaterUtil {

    public static double getRangeValue(int innerLandSize, TreeMap<Integer, Double> ranges) {
        double rangeValue = ranges.get(1);
        for (Map.Entry<Integer, Double> probRange : ranges.entrySet()) {
            if (innerLandSize <= probRange.getKey()) {
                rangeValue = probRange.getValue();
                break;
            }
        }
        return rangeValue;
    }
}
