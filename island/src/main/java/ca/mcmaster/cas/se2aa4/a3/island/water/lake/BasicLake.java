package ca.mcmaster.cas.se2aa4.a3.island.water.lake;

import java.util.*;

import ca.mcmaster.cas.se2aa4.a3.island.water.*;
import ca.mcmaster.cas.se2aa4.a3.island.components.*;

public class BasicLake implements Lake {

    private int numLakes;
    private Random rand;
    private double baseProbability, probabilityIncrement;

    public static final Map<Integer, Double> STOPPING_PROBABILTY_RANGES;
    public static final Map<Integer, Double> LAKE_PROBABILTY_INCREMENTS;

    static {
        STOPPING_PROBABILTY_RANGES = new TreeMap<>();
        STOPPING_PROBABILTY_RANGES.put(1, 1.0);
        STOPPING_PROBABILTY_RANGES.put(10, 0.85);
        STOPPING_PROBABILTY_RANGES.put(30, 0.75);
        STOPPING_PROBABILTY_RANGES.put(75, 0.65);
        STOPPING_PROBABILTY_RANGES.put(150, 0.55);
        STOPPING_PROBABILTY_RANGES.put(300, 0.45);
        STOPPING_PROBABILTY_RANGES.put(500, 0.35);
        STOPPING_PROBABILTY_RANGES.put(1000, 0.25);
        STOPPING_PROBABILTY_RANGES.put(2000, 0.20);
        STOPPING_PROBABILTY_RANGES.put(Integer.MAX_VALUE, 0.15);

        LAKE_PROBABILTY_INCREMENTS = new TreeMap<>();
        LAKE_PROBABILTY_INCREMENTS.put(1, 0.0);
        LAKE_PROBABILTY_INCREMENTS.put(10, 0.2);
        LAKE_PROBABILTY_INCREMENTS.put(30, 0.1);
        LAKE_PROBABILTY_INCREMENTS.put(75, 0.1);
        LAKE_PROBABILTY_INCREMENTS.put(150, 0.1);
        LAKE_PROBABILTY_INCREMENTS.put(300, 0.05);
        LAKE_PROBABILTY_INCREMENTS.put(500, 0.05);
        LAKE_PROBABILTY_INCREMENTS.put(1000, 0.05);
        LAKE_PROBABILTY_INCREMENTS.put(2000, 0.05);
        LAKE_PROBABILTY_INCREMENTS.put(Integer.MAX_VALUE, 0.05);
    }

    public BasicLake(long seed, int maxLakes) {
        this.rand = new Random(seed);
        this.numLakes = rand.nextInt(0, maxLakes + 1);

    }

    @Override
    public Set<Integer> assignLakeTiles(ComponentCollections collection) {
        Set<Integer> lakes = new HashSet<>();
        List<Integer> lakeSources;
        List<Integer> landTilesList = new ArrayList<>();
        collection.getInnerLand().forEach((idx) -> landTilesList.add(idx));
        numLakes = Math.min(landTilesList.size(), numLakes);
        while (lakes.size() < numLakes) {
            lakes.add(landTilesList.get(rand.nextInt(0, landTilesList.size())));
        }

        lakeSources = new ArrayList<>(lakes);
        baseProbability = STOPPING_PROBABILTY_RANGES.get(1);
        for (Map.Entry<Integer, Double> probRange : STOPPING_PROBABILTY_RANGES.entrySet()) {
            if (landTilesList.size() <= probRange.getKey()) {
                baseProbability = probRange.getValue();
                break;
            }

        }
        probabilityIncrement = LAKE_PROBABILTY_INCREMENTS.get(1);
        for (Map.Entry<Integer, Double> incrementRange : LAKE_PROBABILTY_INCREMENTS.entrySet()) {
            if (landTilesList.size() <= incrementRange.getKey()) {
                probabilityIncrement = incrementRange.getValue();
                break;
            }
        }

        for (Integer lakeSource : lakeSources) {
            propogateLakes(lakeSource, collection, lakes);
        }
        return lakes;
    }

    public int getNumLakes() {
        return numLakes;
    }

    private void propogateLakes(int sourceIndex, ComponentCollections collections, Set<Integer> lakes) {
        int maxDist = 0;
        double probability = baseProbability;
        Queue<PairUtil<Integer, Integer>> toVisit = new ArrayDeque<>();
        PairUtil<Integer, Integer> pair;
        while (rand.nextDouble() > probability) {
            probability += probabilityIncrement;
            maxDist++;
        }
        toVisit.add(new PairUtil<>(sourceIndex, 0));
        while (!toVisit.isEmpty()) {
            pair = toVisit.remove();

            if (pair.SECOND + 1 > maxDist) {
                continue;
            }
            for (Integer neighbour : collections.getTileNeighbourIdxs(pair.FIRST)) {
                if (!lakes.contains(neighbour) && !collections.isShoreTile(neighbour)) {
                    toVisit.add(new PairUtil<>(neighbour, pair.SECOND + 1));
                    lakes.add(neighbour);
                }

            }

        }

    }

}
