package ca.mcmaster.cas.se2aa4.a3.island.water.aquifer;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import ca.mcmaster.cas.se2aa4.a3.island.water.*;
import ca.mcmaster.cas.se2aa4.a3.island.components.ComponentCollections;

public class BasicAquifer implements Aquifer {

    private int numAquifers;
    private Random rand;
    private double baseProbability, probabilityIncrement;
    private static final Map<Integer, Double> STOPPING_PROBABILITY_RANGES;
    private static final Map<Integer, Double> AQUIFER_PROBABILITY_INCREMENTS;

    static {
        STOPPING_PROBABILITY_RANGES = new TreeMap<>();
        STOPPING_PROBABILITY_RANGES.put(1, 1.0);
        STOPPING_PROBABILITY_RANGES.put(10, 0.85);
        STOPPING_PROBABILITY_RANGES.put(30, 0.75);
        STOPPING_PROBABILITY_RANGES.put(75, 0.65);
        STOPPING_PROBABILITY_RANGES.put(150, 0.55);
        STOPPING_PROBABILITY_RANGES.put(300, 0.45);
        STOPPING_PROBABILITY_RANGES.put(500, 0.35);
        STOPPING_PROBABILITY_RANGES.put(1000, 0.25);
        STOPPING_PROBABILITY_RANGES.put(2000, 0.20);
        STOPPING_PROBABILITY_RANGES.put(Integer.MAX_VALUE, 0.15);

        AQUIFER_PROBABILITY_INCREMENTS = new TreeMap<>();
        AQUIFER_PROBABILITY_INCREMENTS.put(1, 0.0);
        AQUIFER_PROBABILITY_INCREMENTS.put(10, 0.2);
        AQUIFER_PROBABILITY_INCREMENTS.put(20, 0.1);
        AQUIFER_PROBABILITY_INCREMENTS.put(75, 0.1);
        AQUIFER_PROBABILITY_INCREMENTS.put(150, 0.1);
        AQUIFER_PROBABILITY_INCREMENTS.put(300, 0.05);
        AQUIFER_PROBABILITY_INCREMENTS.put(500, 0.05);
        AQUIFER_PROBABILITY_INCREMENTS.put(1000, 0.05);
        AQUIFER_PROBABILITY_INCREMENTS.put(2000, 0.05);
        AQUIFER_PROBABILITY_INCREMENTS.put(Integer.MAX_VALUE, 0.05);
    }

    public BasicAquifer(long seed, int maxAquifer) {
        this.rand = new Random(seed);
        this.numAquifers = rand.nextInt(0, maxAquifer + 1);
    }

    @Override
    public Set<Integer> assignAquiferTiles(ComponentCollections collection) {
        Set<Integer> aquifers = new HashSet<>();
        List<Integer> aquiferSources;
        List<Integer> landTilesList = new ArrayList<>();
        collection.getInnerLand().forEach((idx) -> landTilesList.add(idx));
        numAquifers = Math.min(landTilesList.size(), numAquifers);
        while (aquifers.size() < numAquifers) {
            aquifers.add(landTilesList.get(rand.nextInt(0, landTilesList.size())));
        }
        aquiferSources = new ArrayList<>(aquifers);
        baseProbability = STOPPING_PROBABILITY_RANGES.get(1);
        for (Map.Entry<Integer, Double> probRange : STOPPING_PROBABILITY_RANGES.entrySet()) {
            if (landTilesList.size() < probRange.getKey()) {
                baseProbability = probRange.getValue();
                break;
            }
        }
        probabilityIncrement = AQUIFER_PROBABILITY_INCREMENTS.get(1);
        for (Map.Entry<Integer, Double> incrementRange : AQUIFER_PROBABILITY_INCREMENTS.entrySet()) {
            if (landTilesList.size() <= incrementRange.getKey()) {
                probabilityIncrement = incrementRange.getValue();
                break;
            }
        }

        for (Integer aquiferSource : aquiferSources) {
            propogateAquifers(aquiferSource, collection, aquifers);
        }
        return aquifers;
    }

    public int getNumAquifers() {
        return numAquifers;
    }

    private void propogateAquifers(int sourceIdx, ComponentCollections collection, Set<Integer> aquifers) {
        int maxDist = 0;
        double probability = baseProbability;
        Queue<PairUtil<Integer, Integer>> toVisit = new ArrayDeque<>();
        PairUtil<Integer, Integer> pair;

        while (rand.nextDouble() > probability) {
            probability += probabilityIncrement;
            maxDist++;
        }
        toVisit.add(new PairUtil<>(sourceIdx, 0));
        while (!toVisit.isEmpty()) {
            pair = toVisit.remove();

            if (pair.SECOND + 1 > maxDist) {
                continue;
            }

            for (Integer neighbour : collection.getTileNeighbourIdxs(pair.FIRST)) {
                if (!aquifers.contains(neighbour)
                        && (collection.isShoreTile(neighbour) || collection.isInnerLandTile(neighbour))) {
                    toVisit.add(new PairUtil<>(neighbour, pair.SECOND + 1));
                    aquifers.add(neighbour);
                }
            }
        }
    }

}
