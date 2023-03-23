package ca.mcmaster.cas.se2aa4.a3.island.preprocessor;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.*;

/**
 * MeshPreprocessor
 */
public class MeshPreprocessor {

    private static final String HEIGHT = "height", WIDTH = "width";

    public static int getHeight(Mesh mesh) {
        int height = findProperty(mesh, HEIGHT);
        if (height != 0) {
            return height;
        }
        for (Vertex vertex : mesh.getVerticesList()) {
            height = (int) Math.ceil(Math.max(height, vertex.getY()));
        }
        return height;
    }

    public static int getWidth(Mesh mesh) {
        int width = findProperty(mesh, WIDTH);
        if (width != 0) {
            return width;
        }
        for (Vertex vertex : mesh.getVerticesList()) {
            width = (int) Math.ceil(Math.max(width, vertex.getX()));
        }
        return width;
    }

    private static int findProperty(Mesh aMesh, String property) {
        int propVal = 0;
        for (Property p : aMesh.getPropertiesList()) {
            if (p.getKey().equals(property)) {
                propVal = Integer.parseInt(p.getValue());
                return propVal;
            }
        }
        return 0;
    }

}
