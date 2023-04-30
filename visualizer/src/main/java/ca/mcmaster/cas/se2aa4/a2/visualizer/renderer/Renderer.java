package ca.mcmaster.cas.se2aa4.a2.visualizer.renderer;

import java.awt.Graphics2D;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;

/**
 * Renderer
 */
public interface Renderer {

    public void render(Mesh aMesh, Graphics2D canvas);
}
