# Assignment A2: Mesh Generator

  - Mohammad Mahdi Mahboob [mahbom2@mcmaster.ca]
  - Khalid Farag [faragk1@mcmaster.ca]
  - Samih Dalvi [dalvis3@mcmaster.ca]

## How to run the product

_This section needs to be edited to reflect how the user can interact with the feature released in your project_

### Installation instructions

This product is handled by Maven, as a multi-module project. We assume here that you have cloned the project in a directory named `A2`

To install the different tooling on your computer, simply run:

```
mosser@azrael A2 % mvn install
```

After installation, you'll find an application named `generator.jar` in the `generator` directory, and a file named `visualizer.jar` in the `visualizer` on.

### Generator

To run the generator, go to the `generator` directory, and use `java -jar` to run the product. The product takes one single argument (so far), the name of the file where the generated mesh will be stored as binary.

```
mosser@azrael A2 % cd generator
mosser@azrael generator % java -jar generator.jar sample.mesh
mosser@azrael generator % ls -lh sample.mesh
-rw-r--r--  1 mosser  staff    29K 29 Jan 10:52 sample.mesh
mosser@azrael generator %
```

### Visualizer

To visualize an existing mesh, go to the `visualizer` directory, and use `java -jar` to run the product. The product take two arguments (so far): the file containing the mesh, and the name of the file to store the visualization (as an SVG image).

```
mosser@azrael A2 % cd visualizer
mosser@azrael visualizer % java -jar visualizer.jar ../generator/sample.mesh sample.svg

... (lots of debug information printed to stdout) ...

mosser@azrael visualizer % ls -lh sample.svg
-rw-r--r--  1 mosser  staff    56K 29 Jan 10:53 sample.svg
mosser@azrael visualizer %
```
To visualize the SVG file:

  - Open it with a web browser
  - Convert it into something else with tools like `rsvg-convert`

## How to contribute to the project

When you develop features and enrich the product, remember that you have first to `package` (as in `mvn package`) it so that the `jar` file is re-generated by Maven.

## Backlog

### Definition of Done

The feature works as intended, is efficient enough for the given scale, and is well documented or easy to understand. Additionally, code is written in such a manner
that future MVPs that require modification of the feature can be implemented with minimal changes to its dependents.

### Product Backlog

Remember to view Issue tickets on GitHub for specific tasks, including non-MVP essentials such as testing.

__Note__: Features in part 2 will only be pre-released as beta to focus on v1.3 (Voronoi generation).

#### Status IDs

 * __B__: Blocked (requires another feature to be implemented)
 * __C__: Conditionally completed, requires cleanup
 * __D__: Done, requires minimum changes and occasional bug fixes
 * __FF__: Fast-forwarded this feature to combine it with the indicated feature(s)
 * __S__: Started, WIP


| ID | Feature title | Who? | Start | End | Status |
|:--:|---------------|------|-------|-----|--------|
| F01 | Generate vertex-colour-averaged square segments | Mohammad/Khalid | 02/06/2023 | 02/08/2023 | D |
| F02 (Pre-release) | Create a 500x500 regular grid with size of 20 with fixed vertex, polygon, and segment properties | Mohammad/Khalid/Samih | 02/20/2023 | 02/23/2023 | D |
| F03 | Add the option to customize the colour and thickness of all components | Mohammad | 02/24/2023 | 02/25/2023 | D |
| F04 | Command-line optionality for Generator | Khalid | 02/19/2023 | 02/26/2023 | D |
| F05 | Incorporate a debugging mode for Visualizer | Samih | 02/24/2023 | 02/25/2023 | D |
| F06 | Add the option to customize the size of the mesh with sane defaults | Mohammad/Samih | 02/24/2023 | 02/26/2023 | D |
| F07 | Add mesh size property for fast correct cropping | Mohammad/Samih | 02/24/2023 | 02/26/2023 | D |
| F08 | Command-line optionality for debug mode on the Visualizer | Khalid | 02/25/2023 | 02/26/2023 | D |
| F09 | Voronoi generator without neighbourhood relationships | Samih | 02/24/2023 | 02/26/2023 | FF (F10) |
| F10 | Voronoi generator with neighbourhood relationships | Samih | 02/24/2023 | 02/26/2023 | D |
