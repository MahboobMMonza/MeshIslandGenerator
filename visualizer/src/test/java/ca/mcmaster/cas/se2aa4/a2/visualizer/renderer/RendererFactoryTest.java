package ca.mcmaster.cas.se2aa4.a2.visualizer.renderer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

/**
 * RendererTest
 */
public class RendererFactoryTest {

    @Test
    public void CorrectFactoryTestAllMatchSucceeds() {
        Renderer gr = RendererFactory.createRenderer(false);
        Renderer dr = RendererFactory.createRenderer(true);
        assertTrue(gr instanceof GraphicRenderer);
        assertTrue(dr instanceof DebugRenderer);
    }
}
