package ca.mcmaster.cas.se2aa4.a2.creator;

import ca.mcmaster.cas.se2aa4.a2.generator.Generator;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import static ca.mcmaster.cas.se2aa4.a2.convertor.Convertor.convert;

import ca.mcmaster.cas.se2aa4.a2.decorator.Decorator;

/**
 * MeshCreator automates the process of creating a low-level io.Structs.Mesh
 * given a configured Mesh structure and a Generator with its required
 * arguments.
 */
public class MeshCreator {

    public static Mesh createMesh(final ca.mcmaster.cas.se2aa4.a2.mesh.Mesh aMesh, final Generator gen,
            final Decorator dec) {
        gen.generate(aMesh);
        aMesh.lock();
        aMesh.decorateComponents(dec);
        return convert(aMesh);
    }
}
