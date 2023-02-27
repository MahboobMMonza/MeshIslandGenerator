package ca.mcmaster.cas.se2aa4.a2.mesh;

import java.util.List;

import ca.mcmaster.cas.se2aa4.a2.components.*;
import ca.mcmaster.cas.se2aa4.a2.decorator.Decorator;

//Mesh interface contains all the properties that mesh should have
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

    public static final int MIN_HEIGHT = 300, MIN_WIDTH = 300;

}
