package ca.mcmaster.cas.se2aa4.a2.mesh;

import java.util.List;

import ca.mcmaster.cas.se2aa4.a2.components.*;
import ca.mcmaster.cas.se2aa4.a2.decorator.Decorator;

public interface Mesh {

    boolean addPoly(Poly p);

    boolean addSeg(Seg s);

    boolean addVert(Vert v);

    void lock();

    List<Poly> getPolys();

    List<Seg> getSegs();

    List<Vert> getVerts();

    boolean decorateComponents(Decorator decorator);

}
