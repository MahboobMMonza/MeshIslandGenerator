package ca.mcmaster.cas.se2aa4.a4.pathfinder.utility;

import java.util.*;

/**
 * PathInfoTriple
 */
public class PathInfoTriple<T> {

    private List<List<Integer>> pathEdgeIdxs;
    private List<T> pathCosts;
    private T infinityValue;

    public List<List<Integer>> getPathEdgeIdxs() {
        return pathEdgeIdxs;
    }

    public void setPathEdgeIdxs(List<List<Integer>> pathEdgeIdxs) {
        this.pathEdgeIdxs = pathEdgeIdxs;
    }

    public List<T> getPathCosts() {
        return pathCosts;
    }

    public void setPathCosts(List<T> pathCosts) {
        this.pathCosts = pathCosts;
    }

    public T getInfinityValue() {
        return infinityValue;
    }

    public void setInfinityValue(T infinityValue) {
        this.infinityValue = infinityValue;
    }

}
