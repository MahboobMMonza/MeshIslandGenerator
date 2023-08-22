# Assignment A2\A3: Mesh Generator\Island Generator

### Authors

  - Mohammad Mahdi Mahboob [mahbom2@mcmaster.ca]
  - Khalid Farag [faragk1@mcmaster.ca]
  - Samih Dalvi [dalvis3@mcmaster.ca]

# Assignment A4: Island Evolution

### Author

  Mohammad Mahdi Mahboob [mahbom2@mcmaster.ca]

## How to run the product

### Installation instructions

This product is handled by Maven as a multi-module project. We assume here that
you have cloned the project in a directory named `A2`

To install the different tooling on your computer, simply run:

```
mosser@azrael A2 % mvn install
```

After installation, you'll find an application named `generator.jar` in the
`generator` directory, and a file named `visualizer.jar` in the `visualizer`
one, and a file named `island.jar` in the `island` subdirectory.

### Generator

To run the generator, go to the `generator` directory, and use `java -jar` to run the product. The product takes multiple arguments, each with its own switch as described below, the name of the file where the generated mesh will be stored as binary. Follow the instructions below.
```
usage: java -jar generator.jar [-bt <arg>] [-dh <arg>] [-dw <arg>] [-f <arg>] [-h] -mt <arg> [-pb <arg>] [-pf <arg>]
       [-rl <arg>] [-sc <arg>] [-sl <arg>] [-sp <arg>] [-st <arg>] [-vc <arg>] [-vt <arg>]

Generate a PNG image of a grid or voronoi mesh with a given length

Options

 -bt,--border-thickness <arg>    Polygon Border Thickness :: MIN = 0, MAX = 30
 -dh,--height <arg>              Dimension for the Mesh Height :: MIN = 300
 -dw,--width <arg>               Dimension for the Mesh Width :: MIN = 300
 -f,--file-name <arg>            Sets File Name
 -h,--help                       Show usage help
 -mt,--mesh-type <arg>           Mesh type (Grid or Vornoi) :: THIS IS A MANDATORY OPTION
 -pb,--polygon-border <arg>      Polygon Border Colour (See Footer for more information)
 -pf,--polygon-fill <arg>        Polygon Fill Colour (See Footer for more information
 -rl,--relaxation-level <arg>    Relaxation Level for Voronoi Mesh :: MIN = 0
 -sc,--segment-colour <arg>      Segment Colour (See Footer for more information)
 -sl,--side-length <arg>         Side Length for Grid Mesh :: MIN = 20
 -sp,--start-points <arg>        Number of Start Points for Voronoi Mesh :: MIN = 20
 -st,--segment-thickness <arg>   Segment Thickness :: MIN = 0.25, Max = 30
 -vc,--vertex-colour <arg>       Vertex Colour (See Footer for more information)
 -vt,--vertex-thickness <arg>    Vertex Thickness :: MIN = 0.25, Max = 30

Colour must be in RGBA hex format. E.g.: Fully opaque orange would be entered as "FFA500FF", and semi-transparent orange
as "FFA50080"; see https://rgbacolorpicker.com/rgba-to-hex for more examples and for hex conversions.
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
mosser@azrael generator % java -jar generator.jar -dh 1000 -dw 1000 -pb FF0000FF -pf FFFF00FF -sc 000000FF -vc 008080CE -vt 1 -st 0.25 -bt 0 -sp 3000 -rl 200 -mt voronoi -f voronoi.mesh
mosser@azrael generator % ls -lh voronoi.mesh

-rw-r--r--  1 mosser  staff    29K 29 Jan 10:52 voronoi.mesh
mosser@azrael generator %
```

__NOTE__: Some options may be skipped and compensated for by the generator program, but the mesh type and file name must be specified before running.

### Island

To create an island for an existing mesh, go to the `island` directory, and use
`java -jar` to run the product. The product multiple arguments, each with its
own switch as described below. The name of the file where the generated mesh
will be stored as binary.

#### Cities

Cities can also be rendered on islands, with roads connecting each city to a captial city. The option for this feature is `-c`, and input details
can be found below. The capital city is displayed as a red dot, with other cities shown as black dots. The roads are shown as black line segments.
The roads show the shortest distance from the capital city to the other cities. The shortest distance is defined as the least number of tiles required
to traverse from a source tile to a target tile. Tiles are used as the distance metric since cities are always located at the centre of a tile, in order
to be dual to rivers, which flow along edges and corners of the tile. Additionally, tile sizes are typically the same, so the distance from one centroid
to its neighbouring centroid is almost the same in relaxed Voronoi meshes, with generally negligible differences in distance.


```
usage: java -jar island.jar -m <mode> -i <input file> -o <output
            file> [option 1] arg1 [option 2] arg2 ...
Help
 -a,--aquifers <arg>       The maximum number of aquifers :: MIN = 0
 -at,--aquifertype <arg>   Aquifer type :: Default is Basic
 -b,--biomes <arg>         Biome type :: Default is Desert
 -e,--altitude <arg>       Elevation type :: Default is Normal
 -h,--help                 Show usage help
 -i,--input <arg>          Input Option :: This is a required option
 -l,--lakes <arg>          The maximum number of lakes :: MIN = 0
 -lt,--laketype <arg>      Lake type :: Default is Basic
 -m,--mode <arg>           Mode type :: This is a required option
 -mt,--moisture <arg>      Moisture type :: Default is Normal
 -o,--output <arg>         Output Option :: This is a required option
 -r,--rivers <arg>         The maximum number of rivers :: MIN = 0
 -c,--cities <arg>         The number of cities :: MIN = 0
 -s,--seed <arg>           Seed :: Provides a random seed if not provided
 -sh,--shape <arg>         Shape type :: Default is Round
 -so,--soil <arg>          Soil Absorption Type :: Default is Normal
Files must contain .mesh at the end
```
__Command Line Options__
| Command | Options Available / Description |
|:-------:|-------------------|
|  -a,--aquifers | max # of aquifers |
|  -at,--aquifertype | basic (extensible for future iterations) |
|  -b,--biomes | frosted, desert, basic, rainforest |
|  -e,--altitude | normal, steep, flat, lofty, basic |
|  -l,--lakes | max # of lakes |
|  -lt,--laketype | basic (extensible for future iterations) |
|  -r,--rivers | max # of rivers |
|  -c,--cities | # of cities |
|  -s,--seed | seed value (leave blank for random seed) |
|  -sh,--shape | round, oval, rectangle, lagoon |
|  -so,--soil | fertile, normal, poor |
|  -mt, --moisture | normal, basic |
|  -m,--mode | custom, lagoon |

For Custom Mode:
```
mosser@azrael A2 % cd island
mosser@azrael island % java -jar island.jar -i  ../generator/voronoi.mesh -o sample.mesh -b desert -l 5 -a 5 -s 12323894724383 -r 5 -sh round -e normal -so normal -at basic -lt basic -m custom

```
For Lagoon Mode:
```
mosser@azrael A2 % cd island
mosser@azrael island % java -jar island.jar -i  ../generator/voronoi.mesh -o sample.mesh --mode lagoon
```
### Visualizer

To visualize an existing mesh, go to the `visualizer` directory, and use `java
-jar` to run the product. The product multiple arguments, each with its own
switch as described below. The name of the file to store the visualization (as
an SVG image).
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
mosser@azrael visualizer % java -jar visualizer.jar -i ../island/sample.mesh -o sample.svg

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

When you develop features and enrich the product, remember that you have first
to `package` (as in `mvn package`) it so that the `jar` file is re-generated by
Maven.

## Backlog (A3)

### Definition of Done

The feature works as intended, is efficient enough for the given scale, and is well documented or easy to understand. Additionally, code is written in such a manner
that future MVPs that require modification of the feature can be implemented with minimal changes to its dependents.

### Product Backlog

Remember to view Issue tickets on GitHub for specific tasks, including non-MVP essentials such as testing.

__Note__: Features in part 2 will only be pre-released as beta to focus on v1.3 (Voronoi generation).

#### Status IDs

 * __B__: Blocked (requires another feature to be implemented)
 * __C__: Conditionally completed, can require cleanup but is basically done
 * __X__: Feature is no longer being developed
 * __D__: Done, requires minimum changes and occasional bug fixes
 * __FF__: Fast-forwarded this feature to combine it with the indicated feature(s)
 * __S__: Started, WIP


| ID | Feature title | Who? | Start | End | Status |
|:--:|---------------|------|-------|-----|--------|
| F01 | Shore level elevation with uniform slightly higher non-shore tiles | Mohammad | 03/21/2023 | 03/23/2023 | C |
| F02 | Non-shore level elevations can vary based on some factors/rules (e.g. noise generator) | Mohammad/Khalid/Samih| 03/22/2023 | 03/23/2023 | C |
| F03 | [Shapes 1] Lagoon islands | Mohammad | 03/21/2023 | 03/23/2023 | C |
| F04 | [Reproducibility 1] Always use the same random seed | Khalid/Samih | 03/23/2023 | 03/23/2023 | C |
| F05 | [Reproducibility 2] User can provide a random seed to generate with, else use the default seed | Khalid | 03/23/2023 | 03/23/2023 | C |
| F06 | [Lakes 1] Flat lakes where each lake tile has same elevation | Samih | 03/23/2023 | 03/24/2023  | C |
| F07 | [Lakes 2]Ability to add more lake profiles | Khalid | 03/25/2023 | 03/26/2023 | C |
| F08 | [Aquifers 1] All aquifers of the same “moisture potential”, randomly distributed | Khalid | 03/24/2023 | 03/24/2023 | C |
| F09 | [Moisture 1] Simple moisture profile that can be affected by tile type | Mohammad/Samih | 03/22/2023 | 03/23/2023 | C |
| F10 | [Moisture 2] Singular soil profile, elevation has effect on the absorption ability | Mohammad/Samih/Khalid | 03/24/2023 | 03/25/2023 | C |
| F11 | [Moisture 3] Singular soil profile, river has effect on absorption ability | Mohammad | 03/26/2023 | 03/26/2023 | C |
| F12 | [Moisture 4] Adjustable soil profile | Mohammad/Khalid | 03/24/2023 | 03/26/2023 | C |
| F13 | [Rivers 1] River springs are controlled by user input | Mohammad | 03/26/2023 | 03/26/2023 | C |
| F14 | [Rivers 2] Rivers flow to lowest elevation point |  |  |  | FF (F13) |
| F15 | [Rivers 3] Rivers thicken when 2 streams join together | | | | FF (F13) |
| F16 | [Rivers 4] “Sink” for the rivers is a lake if not the ocean (lake created separately to the randomly generated ones, and not counted as such) |  |  |  | FF (F13) |
| F17 | [Biomes 1] Singular biome profile (colour palette), affected based on the elevation and tile type | Mohammad | 03/22/2023 | 03/23/2023 | C |
| F18 | [Biomes 2] Multiple biome profiles that can be selected at the command line | Mohammad/Khalid/Samih | 03/25/2023 | 03/25/2023 | C |
| F19 | [Shapes 2] Different shape profiles can be selected on the command line | Mohammad/Samih/Khalid | 03/22/2023 | 03/23/2023 | C |
