package ca.mcmaster.cas.se2aa4.a2.components;

public interface Seg {
    
    void setColour(int r, int g, int b, int a);

    void setThickness(float t);

    void setEndpoints(double x1, double y1, double x2, double y2);

    int[] getColour();

    double getX1();

    double getX2();

    double getY1();

    double getY2();

}
