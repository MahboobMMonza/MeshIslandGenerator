package ca.mcmaster.cas.se2aa4.a2.mesh;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import ca.mcmaster.cas.se2aa4.a2.components.*;
import ca.mcmaster.cas.se2aa4.a2.decorator.Decorator;

/**
 * FixedMesh class, which implements the Mesh interface.
 */
public class FixedMesh implements Mesh {

    private final Set<Poly> polysSet;
    private final Set<Seg> segsSet;
    private final Set<Vert> vertsSet;
    private List<Poly> polysList;
    private List<Seg> segsList;
    private List<Vert> vertsList;
    private boolean locked;

    /**
     * Creates a new FixedMesh and initializes all necessary fields.
     */
    public FixedMesh() {
        vertsSet = new TreeSet<>();
        polysSet = new TreeSet<>();
        segsSet = new TreeSet<>();
        locked = false;
    }

    @Override
    public boolean addPoly(Poly p) {
        Vert v = new Vertex(p.getCentroidX(), p.getCentroidY());
        v.setCentroid();
        return locked ? false : (vertsSet.add(v) && polysSet.add(p));
    }

    @Override
    public boolean addSeg(Seg s) {
        return locked ? false : segsSet.add(s);
    }

    @Override
    public boolean addVert(Vert v) {
        return locked ? false : vertsSet.add(v);
    }

    @Override
    public List<Poly> getPolys() {
        return polysList;
    }

    @Override
    public List<Seg> getSegs() {
        return segsList;
    }

    @Override
    public List<Vert> getVerts() {
        return vertsList;
    }

    /**
     * Applies the appropriate properties to each Poly in the mesh.
     *
     * @param decorator the decorator to decorate with.
     */
    private void decoratePolys(Decorator decorator) {
        for (Poly p : polysList) {
            decorator.decoratePoly(p);
        }
    }

    /**
     * Applies the appropriate properties to each Seg in the mesh.
     *
     * @param decorator the decorator to decorate with.
     */
    private void decorateSegs(Decorator decorator) {
        for (Seg s : segsList) {
            decorator.decorateSeg(s);
        }
    }

    /**
     * Applies the appropriate properties to each Vert in the mesh.
     *
     * @param decorator the decorator to decorate with.
     */
    private void decorateVerts(Decorator decorator) {
        for (Vert v : vertsList) {
            decorator.decorateVert(v);
        }
    }

    @Override
    public void lock() {
        if (locked)
            return;
        polysList = new ArrayList<>(polysSet);
        segsList = new ArrayList<>(segsSet);
        vertsList = new ArrayList<>(vertsSet);
        locked = true;
    }

    @Override
    public boolean decorateComponents(Decorator decorator) {
        if (!locked) {
            return false;
        }
        decorateVerts(decorator);
        decorateSegs(decorator);
        decoratePolys(decorator);
        return true;
    }

}
