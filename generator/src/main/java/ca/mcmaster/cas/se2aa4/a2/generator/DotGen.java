package ca.mcmaster.cas.se2aa4.a2.generator;

import java.io.IOException;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.Random;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;

public class DotGen {

    private final int width = 500;
    private final int height = 500;
    private final int square_size = 20;

    public Mesh generate() {
        Set<Vertex> vertices = new HashSet<>();
        // Create all the vertices
        for (int x = 0; x < width; x += square_size) {
            for (int y = 0; y < height; y += square_size) {
                vertices.add(Vertex.newBuilder().setX((double) x).setY((double) y).build());
                vertices.add(Vertex.newBuilder().setX((double) x + square_size).setY((double) y).build());
                vertices.add(Vertex.newBuilder().setX((double) x).setY((double) y + square_size).build());
                vertices.add(Vertex.newBuilder().setX((double) x + square_size).setY((double) y + square_size).build());
            }
        }
        // Distribute colors randomly. Vertices are immutable, need to enrich them
        Set<Vertex> verticesWithColors = new HashSet<>();
        Random bag = new Random();
        for (Vertex v : vertices) {
            int red = bag.nextInt(255);
            int green = bag.nextInt(255);
            int blue = bag.nextInt(255);
            String colorCode = red + "," + green + "," + blue;
            Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
            Vertex colored = Vertex.newBuilder(v).addProperties(color).build();
            verticesWithColors.add(colored);
        }

        // Convert the coloured vertices into a indexed list
        ArrayList<Vertex> vertList = new ArrayList<>(verticesWithColors);
        System.out.println("Sorting the vertices");
        Collections.sort(vertList, Comparator.comparingDouble(Vertex::getY).thenComparing(Vertex::getX));
        for (Vertex v : vertList) {
            System.out.println(String.format("(%f, %f)", v.getX(), v.getY()));
        }

        // Create segments based on vertList
        /*   0          1           2           25          26          27          28          51
         * (0, 0) :: (20, 0) :: (40, 0) :::: (500, 0) :: (0, 20) :: (20, 20) :: (40, 20) :::: (500, 20)
         */
        List<Segment> allSegments = new ArrayList<>();
        // Add horizontal lines
        int totalRowCols = width / square_size + 1;
        for (int i = 0; i < totalRowCols; i++) {
            for (int j = 0; j < totalRowCols - 1; j++) {
                Property color = Property.newBuilder().setKey("rgb_color").setValue("0,0,0").build();
                allSegments.add(Segment.newBuilder().setV1Idx(i * (totalRowCols) + j).setV2Idx(i * (totalRowCols) + j + 1).addProperties(color)
                        .build());
                // System.out.println(String.format("%d %d", i * (totalRowCols) + j, i * (totalRowCols) + j + 1));
                // System.out.println(String.format("[(%f, %f), (%f, %f)]", vertList.get(i * (totalRowCols) + j).getX(),
                //         vertList.get(i * (totalRowCols) + j).getY(), vertList.get(i * (totalRowCols) + 1 + j).getX(),
                //         vertList.get(i * (totalRowCols) + 1 + j).getY()));
            }
        }
        for (int i = 0; i < totalRowCols - 1; i++) {
            for (int j = 0; j < totalRowCols; j++) {
                Property color = Property.newBuilder().setKey("rgb_color").setValue("0,0,0").build();
                allSegments.add(Segment.newBuilder().setV1Idx(i * totalRowCols + j).setV2Idx((i + 1) * totalRowCols + j).addProperties(color)
                        .build());
                // System.out.println(String.format("%d %d", i * (totalRowCols) + j, i * (totalRowCols + 1) + j));
                // System.out.println(String.format("[(%f, %f), (%f, %f)]", vertList.get(i * (totalRowCols) + j).getX(),
                //         vertList.get(i * (totalRowCols) + j).getY(), vertList.get(i * (totalRowCols + 1) + j).getX(),
                //         vertList.get(i * (totalRowCols + 1) + j).getY()));
            }
        }
        return Mesh.newBuilder().addAllVertices(vertList).addAllSegments(allSegments).build();
    }

}
