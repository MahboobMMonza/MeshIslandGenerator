package ca.mcmaster.cas.se2aa4.a2.decorator;

import ca.mcmaster.cas.se2aa4.a2.components.*;

public interface Decorator {

    boolean setPolyFillColour(final String colourString);

    boolean setPolyBorderColour(final String colourString);

    boolean setSegColour(final String colourString);

    boolean setVertColour(final String colourString);

    boolean setSegThickness(final String thickness);

    boolean setVertThickness(final String thickness);

    boolean setPolyBorderThickness(final String thickness);

    String getPolyFillColour();

    String getPolyBorderColour();

    String getSegColour();

    String getVertColour();

    float getPolyBorderThickness();

    float getSegThickness();

    float getVertThickness();

    void decoratePoly(final Poly p);

    void decorateSeg(final Seg s);

    void decorateVert(final Vert v);
}
