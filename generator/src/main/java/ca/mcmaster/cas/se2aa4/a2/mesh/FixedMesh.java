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
    private int height, width;
    private boolean locked;

    static void warnUser(int givenValue, int defaultValue, String property) {
        if (givenValue < defaultValue) {
            System.out.println(
                    String.format("WARNNING: The interpreted value of %1$s was set below the minimum value"
                            + "of %2$s for argument %3$s. Setting %3$s to default value %2$s.",
                            givenValue, defaultValue, property.toUpperCase()));
        }
    }

    /**
     * Creates a new FixedMesh and initializes all necessary fields.
     */
    public FixedMesh() {
        vertsSet = new TreeSet<>();
        polysSet = new TreeSet<>();
        segsSet = new TreeSet<>();
        setHeight(MIN_HEIGHT);
        setWidth(MIN_WIDTH);
        locked = false;
    }

    /**
     * Creates a new FixedMesh and initializes all necessary fields.
     */
    public FixedMesh(int height, int width) {
        this();
        setHeight(height);
        setWidth(width);
    }

    @Override
    public boolean setHeight(int h) {
        if (locked)
            return false;
        height = Math.max(MIN_HEIGHT, h);
        warnUser(h, MIN_HEIGHT, "height");
        return true;
    }

    @Override
    public boolean setWidth(int w) {
        if (locked)
            return false;
        width = Math.max(MIN_WIDTH, w);
        warnUser(w, MIN_WIDTH, "width");
        return true;
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
    public boolean decorateComponents(Decorator decorator) {
        if (!locked) {
            return false;
        }
        decorateVerts(decorator);
        decorateSegs(decorator);
        decoratePolys(decorator);
        return true;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
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

    @Override
    public void lock() {
        if (locked)
            return;
        polysList = new ArrayList<>(polysSet);
        segsList = new ArrayList<>(segsSet);
        vertsList = new ArrayList<>(vertsSet);
        locked = true;
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

}
