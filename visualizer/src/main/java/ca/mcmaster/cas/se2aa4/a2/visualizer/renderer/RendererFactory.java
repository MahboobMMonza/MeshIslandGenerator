package ca.mcmaster.cas.se2aa4.a2.visualizer.renderer;

/**
 * Renderer
 */
public class RendererFactory {

    public Renderer createRenderer(boolean debug) {
        return debug ? new DebugRenderer() : new GraphicRenderer();
    }
}
