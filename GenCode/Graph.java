// Graph.java

import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;

/**
 * Our group's Graph class that we developed together to be
 * used and modified by each of us for our respective Tasks.
 * @author Mariano Pennini, Alex Sharata, Matthias Philippine
 *
 */
public class Graph {
    /** Represents infinity. */
    public static final Integer INF = Integer.MAX_VALUE;
    
    /** The hash map to be used to store an array
     * list of vertices with name key.*/
    Map<String, Vertex> mapOfVertices = new HashMap<String, Vertex>();
    
    /** StringBuilder object to build a TaxiDriver's shortest path. */
    StringBuilder shortestPathBuilder = new StringBuilder();
    
    /** A String object holding a TaxiDriver's shortest path. */
    String currentShortestPath = new String();
    
    /** Value for weight of a TaxiDriver's shortest path. */
    Integer currentShortestPathWeight = 0;
    
    /**
     * Creates a new edge in the graph.
     * @param v1 starting vertex
     * @param v2 ending vertex
     * @param weight weight of edge being created
     */
    public void createEdge(String v1, String v2, Integer weight) {
        Vertex vert1 = this.getVertex(v1);
        Vertex vert2 = this.getVertex(v2);
        vert1.adjacencyList.add(new Edge(vert2, weight));
    }
    
    // IMPLEMENT DIJKSTRA'S FOR TASK 3 HERE
    
    // PRIVATE HELPER METHODS
    
    /**
     * Retrieves the specified vertex from the mapOfVertices
     * and creates a key-value pair in the mapOfVertices if
     * the specified vertex doesn't exist.
     * @param v specified vertex
     * @return the vertex specified by the key v
     */
    private Vertex getVertex(String v) {
        Vertex vert = this.mapOfVertices.get(v);
        if (vert == null) {
            vert = new Vertex(v);
            this.mapOfVertices.put(v, vert);
        }
        return vert;
    }
    
    // PRIVATE INNER CLASSES
    
    /**
     * Inner Vertex class used for a vertex in the graph.
     * @author Mariano Pennini, Alex Sharata, Matthias Philippine
     *
     */
    private class Vertex {
        /** Vertex's name (which is a location on the map). */
        public String locationName;
        
        /** Adjacency List holding all the adjacent vertices. */
        public List<Edge> adjacencyList;
        
        /** Weight of edge created a vertex in the adjacency list. */
        public Integer weight;
        
        /** Represents the previously added vertex in the shortest path. */
        public Vertex previousVertex;
        
        /** Integer to keep track of number of times visited. */
        public int visited;
        
        /**
         * Vertex constructor with one argument.
         * @param loc name of map's location for newly created vertex
         */
        public Vertex(String loc) {
            this.locationName = loc;
            this.adjacencyList = new LinkedList<Edge>();
            this.unvisitVertex();
        }
        
        /**
         * Public helper method to make the Vertex unvisited for algorithm.
         */
        public void unvisitVertex() {
            this.weight = Graph.INF;
            this.previousVertex = null;
            this.visited = 0;
        }
        
    }
    
    /**
     * Inner Edge class made of two vertices and a weight.
     * @author Mariano Pennini, Alex Sharata, Matthias Philippine
     *
     */
    private class Edge {
        /** Last vertex in the edge. */
        public Vertex v2;
        
        /** Edge's weight. */
        public Integer weight;
        
        /**
         * Edge constructor with two arguments.
         * @param v ending vertex
         * @param w edge weight
         */
        public Edge(Vertex v, Integer w) {
            this.v2 = v;
            this.weight = w;
        }
    }
    
    /**
     * Inner Path class.
     * @author Mariano Pennini, Alex Sharata, Matthias Philippine
     *
     */
    private class Path implements Comparable<Path> {
        /** final vertex in the path. */
        public Vertex finalVert;
        
        /** total weight of the path. */
        public Integer totalWeight;
        
        /**
         * Path constructor with two arguments.
         * @param v final vertex in the path
         * @param w total weight of the path
         */
        public Path(Vertex v, Integer w) {
            this.finalVert = v;
            this.totalWeight = w;
        }
        
        @Override
        public int compareTo(Path rhs) {
            Integer otherPathWeight = rhs.totalWeight;
            if (this.totalWeight < otherPathWeight) {
                return -1;
            } else if (this.totalWeight > otherPathWeight) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}

