package ca.mcmaster.cas.se2aa4.a3.island.water.river;

import java.util.*;

import static ca.mcmaster.cas.se2aa4.a3.island.water.WaterUtil.*;

import ca.mcmaster.cas.se2aa4.a3.island.water.*;
import ca.mcmaster.cas.se2aa4.a3.island.components.*;
import ca.mcmaster.cas.se2aa4.a3.island.elevation.ElevationLevels;

/**
 * River
 */
public class River {

    static final int UNVISITED = -1;
    static final float MIN_INIT_FLOW = 3.0f, MAX_INIT_FLOW = 5.0f;

    private int numRivers;
    private Random rand;
    private double baseProbability, probabilityIncrement;

    public static final TreeMap<Integer, Double> STOPPING_PROBABILTY_RANGES;
    public static final TreeMap<Integer, Double> RIVER_PROBABILTY_INCREMENTS;

    static {
        STOPPING_PROBABILTY_RANGES = new TreeMap<>();
        STOPPING_PROBABILTY_RANGES.put(1, 1.0);
        STOPPING_PROBABILTY_RANGES.put(10, 0.55);
        STOPPING_PROBABILTY_RANGES.put(30, 0.50);
        STOPPING_PROBABILTY_RANGES.put(75, 0.45);
        STOPPING_PROBABILTY_RANGES.put(150, 0.40);
        STOPPING_PROBABILTY_RANGES.put(300, 0.35);
        STOPPING_PROBABILTY_RANGES.put(500, 0.25);
        STOPPING_PROBABILTY_RANGES.put(1000, 0.20);
        STOPPING_PROBABILTY_RANGES.put(2000, 0.15);
        STOPPING_PROBABILTY_RANGES.put(Integer.MAX_VALUE, 0.10);

        RIVER_PROBABILTY_INCREMENTS = new TreeMap<>();
        RIVER_PROBABILTY_INCREMENTS.put(1, 0.0);
        RIVER_PROBABILTY_INCREMENTS.put(10, 0.2);
        RIVER_PROBABILTY_INCREMENTS.put(30, 0.17);
        RIVER_PROBABILTY_INCREMENTS.put(75, 0.15);
        RIVER_PROBABILTY_INCREMENTS.put(150, 0.1);
        RIVER_PROBABILTY_INCREMENTS.put(300, 0.07);
        RIVER_PROBABILTY_INCREMENTS.put(500, 0.05);
        RIVER_PROBABILTY_INCREMENTS.put(1000, 0.04);
        RIVER_PROBABILTY_INCREMENTS.put(2000, 0.03);
        RIVER_PROBABILTY_INCREMENTS.put(Integer.MAX_VALUE, 0.02);
    }

    public River(long seed, int maxRivers) {
        rand = new Random(seed);
        numRivers = maxRivers;
        // this.numRivers = rand.nextInt(0, maxRivers + 1);
    }

    public PairUtil<PairUtil<List<List<Integer>>, List<Integer>>, List<Float>> assignRiverTiles(ComponentCollections collection) {
        // returns a list of tile indices for each river
        List<Integer> innerLand = new ArrayList<>(), curRiver, sourcePoints = new ArrayList<>();
        List<Integer> tileEdges = new ArrayList<>();
        List<List<Integer>> riverTiles = new ArrayList<>();
        Map<Integer, Integer> parents;
        Set<Integer> riverSources = new HashSet<>();
        PairUtil<PairUtil<Integer, Map<Integer, Integer>>, Boolean> riverProp;
        List<Float> riverThicknesses = new ArrayList<>();
        collection.getInnerLand().forEach(innerLand::add);
        numRivers = Math.min(innerLand.size(), numRivers);
        while (riverSources.size() < numRivers) {
            riverSources.add(innerLand.get(rand.nextInt(0, innerLand.size())));
            riverThicknesses.add(rand.nextFloat(MIN_INIT_FLOW, MAX_INIT_FLOW));
        }
        baseProbability = getRangeValue(innerLand.size(), STOPPING_PROBABILTY_RANGES);
        probabilityIncrement = getRangeValue(innerLand.size(), RIVER_PROBABILTY_INCREMENTS);
        for (Integer sourceIdx : riverSources) {
            riverProp = propogateRivers(sourceIdx, collection);
            // recreate the path and reverse it, then identify the edges in question
            riverTiles.add(new ArrayList<>());
            curRiver = riverTiles.get(riverTiles.size() - 1);
            curRiver.add(riverProp.FIRST.FIRST);
            parents = riverProp.FIRST.SECOND;
            while (parents.get(curRiver.get(curRiver.size() - 1)) != UNVISITED) {
                curRiver.add(parents.get(curRiver.get(curRiver.size() - 1)));
            }
            Collections.reverse(curRiver);
            // Add back the last tile if endorheic
            if (riverProp.SECOND) {
                curRiver.add(riverProp.FIRST.FIRST);
            }
            tileEdges.clear();
            collection.getTileEdgeIdxs(sourceIdx).forEach(tileEdges::add);
            sourcePoints.add(tileEdges.get(rand.nextInt(0, tileEdges.size())));
        }
        return new PairUtil<>(new PairUtil<>(riverTiles, sourcePoints), riverThicknesses);
    }

    private PairUtil<PairUtil<Integer, Map<Integer, Integer>>, Boolean> propogateRivers(int sourceIdx,
            ComponentCollections collection) {
        Deque<Integer> toVisit = new ArrayDeque<>();
        Map<Integer, List<Integer>> categorizedElevationNeighbours = new TreeMap<>();
        int nodeIdx, nodeElevID, lastNode = sourceIdx, numTiles = UNVISITED, next;
        boolean stop = false, hasLower, endorheic = false;
        Map<Integer, Integer> parentMap = new HashMap<>();
        List<Integer> potentialLowers = new ArrayList<>(), potentialLasts = new ArrayList<>(),
                potentialNexts = new ArrayList<>();
        for (ElevationLevels values : ElevationLevels.values()) {
            categorizedElevationNeighbours.put(values.getElevationID(), new ArrayList<>());
        }
        parentMap.put(sourceIdx, UNVISITED);
        toVisit.push(sourceIdx);
        while (!stop) {
            numTiles++;
            potentialLowers.clear();
            potentialNexts.clear();
            potentialLasts.clear();
            nodeIdx = toVisit.pop();
            nodeElevID = collection.getTileElevationLevel(nodeIdx).getElevationID();
            hasLower = false;
            for (List<Integer> list : categorizedElevationNeighbours.values()) {
                list.clear();
            }
            for (Integer neighbour : collection.getTileNeighbourIdxs(nodeIdx)) {
                if (!parentMap.containsKey(neighbour)) {
                    categorizedElevationNeighbours.get(collection.getTileElevationLevel(neighbour).getElevationID())
                            .add(neighbour);
                    parentMap.put(neighbour, nodeIdx);
                }
            }
            for (Map.Entry<Integer, List<Integer>> elevNeighbours : categorizedElevationNeighbours.entrySet()) {
                if (!elevNeighbours.getValue().isEmpty() && elevNeighbours.getKey() < 0) {
                    stop = true;
                    potentialLasts.addAll(elevNeighbours.getValue());
                } else if (elevNeighbours.getKey() < nodeElevID && !elevNeighbours.getValue().isEmpty()) {
                    hasLower = true;
                    potentialLowers.addAll(elevNeighbours.getValue());
                } else if (elevNeighbours.getKey() == nodeElevID) {
                    potentialNexts.addAll(elevNeighbours.getValue());
                }
            }
            if (stop) {
                lastNode = potentialLasts.get(rand.nextInt(0, potentialLasts.size()));
            } else if (hasLower) {
                next = potentialLowers.get(rand.nextInt(0, potentialLowers.size()));
                toVisit.push(next);
            } else if (potentialNexts.size() == 0) {
                stop = true;
                lastNode = nodeIdx;
                endorheic = true;
            } else {
                next = potentialNexts.get(rand.nextInt(0, potentialNexts.size()));
                toVisit.push(next);
            }
        }
        return new PairUtil<>(new PairUtil<>(lastNode, parentMap), endorheic);
    }
}
