# Assignment A2: Mesh Generator

  - Mohammad Mahdi Mahboob [mahbom2@mcmaster.ca]
  - Khalid Farag [faragk1@mcmaster.ca]
  - Samih Dalvi [dalvis3@mcmaster.ca]

## How to run the product

### Installation instructions

This product is handled by Maven as a multi-module project. We assume here that you have cloned the project in a directory named `A2`

To install the different tooling on your computer, simply run:

```
mosser@azrael A2 % mvn install
```

After installation, you'll find an application named `generator.jar` in the `generator` directory, and a file named `visualizer.jar` in the `visualizer` on.

### Generator

To run the generator, go to the `generator` directory, and use `java -jar` to run the product. The product takes multiple arguments, each with its own switch as described below, the name of the file where the generated mesh will be stored as binary. Follow the instructions below.
```
usage: java -jar generator.jar -mt type_arg [option 1] arg1 [option 2] arg2
            ...
Options
 -bt,--borderthickness <arg>    Polygon Border Thickness :: MIN =0, MAX =
                                30
 -dh,--dimensionh <arg>         Dimension for the height :: MIN = 300
 -dw,--dimensionw <arg>         Dimension for the width :: MIN = 300
 -f,--filename <arg>            Sets File Name
 -h,--help                      Show usage help
 -mt,--meshtype <type_arg>           Mesh type (Grid or Vornoi) ::This is a
                                mandatory option
 -pb,--polygonborder <arg>      Polygon Border Colour (See Footer for more
                                information)
 -pf,--polygonfill <arg>        Polygon Fill Colour (See Footer for more
                                information
 -rl,--relaxationlevel <arg>    Relaxation Level :: MIN = 0
 -sc,--segmentcolour <arg>      Segment Colour (See Footer for more
                                information)
 -sl,--sidelength <arg>         Side Lengths :: MIN = 20
 -sp,--startpoints <arg>        Number of Start Points :: MIN = 20
 -st,--segmentthickness <arg>   Segment Thickness :: MIN = 0.25, Max = 30
                                )
 -vc,--vertexcolour <arg>       Vertex Colour (See Footer for more
                                information)
 -vt,--vertexthickness <arg>    Vertex Thickness :: MIN = 0.25, Max = 30
Colour must be in RGBA hex format. E.g.: Fully opaque orange would be
entered as "FFA500FF", and semi-transparent orange as "FFA50080"; see
https://rgbacolorpicker.com/rgba-to-hex for more examples and for hex
conversions
```

For a Grid Mesh with some default options for every color:

```
mosser@azrael A2 % cd generator
mosser@azrael generator % java -jar generator.jar -mt Grid -dh 1000 -dw 1000 -pb FF0000FF -pf FFFF00FF -sc 000000FF -vc 008080CE -vt 7 -st 2 -bt 7 -sl 100 -mt grid -f grid.mesh
mosser@azrael generator % ls -lh grid.mesh
-rw-r--r--  1 mosser  staff    29K 29 Jan 10:52 grid.mesh
mosser@azrael generator %
```
For a Voronoi Mesh with some default options for every color:

```
mosser@azrael A2 % cd generator
mosser@azrael generator % java -jar generator.jar -dh 1000 -dw 1000 -pb FF0000FF -pf FFFF00FF -sc 000000FF -vc 008080CE -vt 7 -st 2 -bt 7 -rl 23 -sp 55 -mt voronoi -f voronoi.mesh
mosser@azrael generator % ls -lh voronoi.mesh
-rw-r--r--  1 mosser  staff    29K 29 Jan 10:52 voronoi.mesh
mosser@azrael generator %
```
__NOTE__: Some options may be skipped and compensated for by the generator program, but the mesh type and file name must be specified before running.

### Visualizer

To visualize an existing mesh, go to the `visualizer` directory, and use `java -jar` to run the product. The product multiple arguments, each with it's own switch as described below. The name of the file to store the visualization (as an SVG image).
```
usage: java -jar visualizer/visualizer.jar [-i] inputfile [-o] outputfile
Help
 -h,--help           Show usage help
 -i,--input <arg>    Input Option
 -o,--output <arg>   Output Option
 -X,--debug          Debug Option
For debugging, Add option [-X]
```
For Normal Mode:
```
mosser@azrael A2 % cd visualizer
mosser@azrael visualizer % java -jar visualizer.jar -i ../generator/sample.mesh -o sample.svg

... (lots of debug information printed to stdout) ...

mosser@azrael visualizer % ls -lh sample.svg
-rw-r--r--  1 mosser  staff    56K 29 Jan 10:53 sample.svg
mosser@azrael visualizer %
```
For Debug Mode:
```
mosser@azrael A2 % cd visualizer
mosser@azrael visualizer % java -jar visualizer.jar -X -i ../generator/sample.mesh -o sample.svg

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
