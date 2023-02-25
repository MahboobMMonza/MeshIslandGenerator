package ca.mcmaster.cas.se2aa4.a2.generator;

import ca.mcmaster.cas.se2aa4.a2.mesh.Mesh;

/**
 * Generator interface for creating all positional data for components stored in
 * a {@link ca.mcmaster.cas.se2aa4.a2.mesh.Mesh}.
 */
public interface Generator {

    /**
     * Generates all positional data for the components in the given Mesh.
     *
     * @param mesh   the mesh to add all components to.
     * @param height the height of the canvas.
     * @param width  the width of the canvas.
     * @param n      the additional property ot consider for this canvas. Varies
     *               with implementation.
     */
    void generate(final Mesh mesh, final int height, final int width);
}
