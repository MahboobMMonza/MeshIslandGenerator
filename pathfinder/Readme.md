# A4 Pathfinder

## Author

  Mohammad Mahdi Mahboob [mahbom2@mcmaster.ca]

## Overview

This library is responsible for defining the contract of finding a path in a graph of nodes and edges from a given source
node to all other nodes, referencing and edges nodes based on their index in the graph. The pathfinder returns a list of lists of edge
indices to take from the source node to each node in question, a list of the costs to traverse from the source node to the other
nodes, and the cost value the pathfinder interpreted as infinity (i.e. unreachable). If a node is unreachable, then the
cost to it should be equal to the infinity value, and the list of edge indices should contain a single value, -1. For the source
node, the edges list will generally be empty to signify it is a source, and the cost of traversal should be 0 as represented by
a specified cost type. The 3 items being returned must be part of a `PathInfoTriple` object.

The library also contains two implementations of pathfinding algorithms: __UnweightedShortPath__, which finds the shortest path
from a source node using unweighted edges and breadth-first search, and __PosWeightedShortPath__, which finds the shortest path
from a source node using non-negative weighted edges and Dijkstra's Algorithm, which only works for Integer, Long, Double, and
Float type costs; the class of the cost type must be specified in the constructor of the __PosWeightedShortPath__ instance.

## Graph ADT

The library also specifies contracts describing a graph ADT, as well as all components associated with the graph. It also provides
simple implementations of the ADT for graphs, nodes, and edges.

The sample implementations are:

 * `BasicNode` for nodes
 * `BasicEdge` for simple directed edges
 * `BasicBiEdge` for simple bidirectional edges with the same cost to traverse from either end
 * `BasicGraph` for a graph implementation

### Implementing Pathfinding Algorithms

Pathfinders are parameterized to the cost type of the edges that are present in the graph. Only one cost type can be present in a graph to ensure that
calculations can be done smoothly. As a result, graphs implementing the `Graph` interface and edges implementing the `VersatileEdge` interface must be
parameterized to a single cost type, which must implement the `Comparable` interface and extend the `java.lang.Number` class. This is because costs are
numeric, and they must be compared to one another.

The pathfinder uses a given graph and a valid source node index to calculate the paths to all other nodes (if they exist) by relaxing all the edges
adjacent along nodes, using the adjacency list of edge indices for each node. All information regarding relationships between nodes and edges as well
as properties of nodes should be obtained through the graph that is provided. If an empty graph is provided, then all returned lists should be empty.

The pathfinding algorithm should run under the assumption that a graph has been configured correctly. The requirements for configuration are as follows:

 * Nodes must be referenced with 0-indexed values. The index corresponds to its index in the list of nodes.
 * Similarly, edges must be referenced with 0-indexed values. The index corresponds to its index in the list of edges.
 * Adjacency is defined as the list of edges associated with a given node. If a node indexed 3 is adjacent to nodes indexed 4 and 5 and connected
     via edges indexed 12 and 19, then the adjacency list for node 3 should resemble {12, 19}, in any order.
 * Edges contain information about the node indices that it connects, not the nodes themselves. This is because it is always assumed that graphs
     have been configured correctly, and graphs have access to the list of nodes, which is passed into the pathfinding algorithm.
 * Edge costs can be null. In the default pathfinding algorithms, null costs mean an edge cannot be traversed, and this is done to allow for
     configurations of edges based on user-given context (e.g. if an edge is a bridge between two locations that can be active or inactive, setting
     an edge to have a null cost can indicate that the bridge is not traversable).
 * Edges can be bidirectional and hold different costs depending on which direction it is being traversed. This would allow for space optimization for
     dense graphs. Note that for bidirectional edges, the adjacency list for both nodes should be updated to contain the edge.
 * Graphs contain the list of nodes, list of all edges, and for each node, a list of integers detailing which edges are adjacent to it.
 * Nodes can hold properties which are referenced to by key strings (since properties generally have labels). Requested properties will be returned as
     Object instances.

Any and all instances of Graph, Node, and VersatileEdge interfaces must fulfill the contracts outlined in each interface while respecting the spirit of
the implementation specifications outlined above.
