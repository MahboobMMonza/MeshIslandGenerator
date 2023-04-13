package ca.mcmaster.cas.se2aa4.a3.island.city;

import java.util.*;
import ca.mcmaster.cas.se2aa4.a3.island.components.*;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.utility.*;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.pathfinders.*;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.components.*;

/**
 * CityPopulator
 */
public class CityPopulator {

    static final int PRIME = 12582917;

    private Random rand;

    private int numCities;
    private List<Integer> cityCentroids = new ArrayList<>();
    private List<int[]> centroidPairs = new ArrayList<>();

    public CityPopulator(int numCities, long seed) {
        this.numCities = numCities;
        rand = new Random(seed);
    }

    public List<int[]> getCentroidPairs() {
        return centroidPairs;
    }

    public List<Integer> getCityCentroids() {
        return cityCentroids;
    }

    public void findPaths(ComponentCollections collection) {
        List<Node> nodes = new ArrayList<>();
        List<Integer> nodeCentroids = new ArrayList<>(), eligibleTiles = new ArrayList<>(),
                cityIdxs = new ArrayList<>();
        List<VersatileEdge<Integer>> edges = new ArrayList<>();
        List<List<Integer>> adjEdges = new ArrayList<>();
        Map<Integer, Integer> centroidToIdx = new HashMap<>();
        Set<Integer> addedPairs = new HashSet<>();
        Set<Integer> addedCities = new HashSet<>();
        Graph<Integer> graph = new BasicGraph<>();
        Pathfinder<Integer, Integer> pathfinder = new UnweightedShortPath<>();
        PathInfoTriple<Integer> result;
        int idx = 0, n1, n2;
        if (numCities <= 0) {
            return; // for now
        }
        for (int tileCentroid : collection.getAllTileIdxs()) {
            nodes.add(new BasicNode(idx));
            centroidToIdx.put(tileCentroid, idx);
            nodeCentroids.add(tileCentroid);
            adjEdges.add(new ArrayList<>());
            if (!collection.isLakeTile(tileCentroid)
                    && (collection.isInnerLandTile(tileCentroid) || collection.isShoreTile(tileCentroid))) {
                eligibleTiles.add(tileCentroid);
            }
            idx++;
        }
        idx = 0;
        for (int tileCentroid : eligibleTiles) {
            for (Integer neighbourCentroid : collection.getTileNeighbourIdxs(tileCentroid)) {
                if (!collection.isLakeTile(neighbourCentroid) && (collection.isShoreTile(neighbourCentroid)
                        || collection.isInnerLandTile(neighbourCentroid))) {
                    edges.add(new BasicEdge<>(idx, centroidToIdx.get(tileCentroid),
                            centroidToIdx.get(neighbourCentroid), 1));
                    idx++;
                }
            }
        }
        for (VersatileEdge<Integer> edge : edges) {
            adjEdges.get(edge.getN1Idx()).add(edge.getIndex());

        }
        graph.setAllNodes(nodes);
        graph.setAllEdges(edges);
        for (int i = 0; i < adjEdges.size(); i++) {
            graph.setAssociatedEdgeIdxs(i, adjEdges.get(i));
        }
        numCities = Math.min(numCities, eligibleTiles.size());
        while (addedCities.size() < numCities) {
            addedCities.add(centroidToIdx.get(eligibleTiles.get(rand.nextInt(0, eligibleTiles.size()))));
        }
        addedCities.forEach((cityIdx) -> cityIdxs.add(cityIdx));
        result = pathfinder.findAllSourcePaths(graph, cityIdxs.get(0));
        for (int cityIdx : cityIdxs) {
            cityCentroids.add(nodeCentroids.get(cityIdx));
            for (Integer edgeIdx : result.getPathEdgeIdxs().get(cityIdx)) {
                if (edgeIdx == -1) {
                    break;
                }
                n1 = edges.get(edgeIdx).getN1Idx();
                n2 = edges.get(edgeIdx).getN2Idx();
                if (!addedPairs.contains(calculateHash(n1, n2))) {
                    addedPairs.add(calculateHash(n1, n2));
                    addedPairs.add(calculateHash(n2, n1));
                    centroidPairs.add(new int[] { nodeCentroids.get(n1), nodeCentroids.get(n2) });
                }
            }
        }
    }

    private int calculateHash(int a, int b) {
        return a * PRIME + b;
    }
}
