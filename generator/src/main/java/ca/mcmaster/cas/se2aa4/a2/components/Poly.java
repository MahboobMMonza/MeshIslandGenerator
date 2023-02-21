package ca.mcmaster.cas.se2aa4.a2.components;
import java.util.List;

public interface Poly {

     void setCentroid(double x, double y);

     void setVertices(List<double[]> verticesList);

     void setNeighbours(List<double[]> neighbourCentroidList);

     void setCoulour(int r, int g, int b, int a);




}
