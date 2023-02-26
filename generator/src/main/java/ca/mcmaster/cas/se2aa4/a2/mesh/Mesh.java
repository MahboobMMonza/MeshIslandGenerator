package ca.mcmaster.cas.se2aa4.a2.mesh;

import java.util.List;

import ca.mcmaster.cas.se2aa4.a2.components.*;
import ca.mcmaster.cas.se2aa4.a2.decorator.Decorator;

public interface Mesh {

    boolean addPoly(Poly p);

    boolean addSeg(Seg s);

    boolean addVert(Vert v);

    boolean setHeight(int h);

    boolean setWidth(int w);

    void lock();

    int getHeight();

    int getWidth();

    List<Poly> getPolys();

    List<Seg> getSegs();

    List<Vert> getVerts();

    boolean decorateComponents(Decorator decorator);

    static final int MIN_HEIGHT = 300, MIN_WIDTH = 300;

}
