//import java.util.Collection;
import java.util.List;
//import java.util.Queue;
import java.util.Map;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.NoSuchElementException;
//import java.util.PriorityQueue;



/**
 *This class represents a graph ADT using a hash map to
 *store an adjacency list of edges.
 * @author Mariano Pennini, Alex Sharata, Matthias Phillippine
 *
 */
public class Graph {
    
    /**Represents an infinity value.**/
    public static final double INFINITY = Double.MAX_VALUE;
    /**The hash map to be used to store an array 
     * list of vertices with name key.**/
    private Map<String, Vertex> vertexMap = new HashMap<String, Vertex>();

    /**
     * Add a new edge to the graph.
     * @param sourceName the name of the source vertex
     * @param destName the name of the destination vertex.
     * @param weight the weight to be placed on the edge.
     */
    public void addEdge(String sourceName, String destName, double weight) {
        Vertex v = this.getVertex(sourceName);
        Vertex w = this.getVertex(destName);
        v.adj.add(new Edge(w, weight));
    }
    
    //DELETE THIS
  //DELETE THIS
  //DELETE THIS    
  //DELETE THIS
  //DELETE THIS
  //DELETE THIS
  //DELETE THIS
    /**
     * Driver routine to handle unreachables and print total cost.
     * It calls recursive routine to print shortest path to
     * destNode after a shortest path algorithm has run.
     * 
     * @param destName the name of the destination vertex.
     */
    public void printPath(String destName) {
        Vertex w = this.vertexMap.get(destName);
        if (w == null) {
            throw new NoSuchElementException("Destination vertex not found");
        } else if (w.weight == INFINITY) {
            System.out.println(destName + " is unreachable");
        } else {
            System.out.print("(Cost is: " + w.weight + ") ");
            this.printPath(w);
            System.out.println();
        }
    }

    /**
     * If vertexName is not present, add it to vertexMap.
     * In either case, return the Vertex.
     * @param vertexName the name of the vertex requested.
     * @return the vertex V that was requested.
     */
    private Vertex getVertex(String vertexName) {
        Vertex v = this.vertexMap.get(vertexName);
        if (v == null) {
            v = new Vertex(vertexName);
            this.vertexMap.put(vertexName, v);
        }
        return v;
    }

    /**
     * Recursive routine to print shortest path to dest
     * after running shortest path algorithm. The path
     * is known to exist.
     * 
     * @param dest the "destination" vertex.
     * 
     */
    private void printPath(Vertex dest) {
        
        if (dest.prev != null) {
            this.printPath(dest.prev);
            System.out.print(" to ");
        }
        System.out.print(dest.name);
    }
    
    /**
     * This class represents a weighted edge in a graph.
     * @author Mariano
     *
     */
    private class Edge {
        
        /**The second vertex int he edge.**/
        private Vertex dest;
        /**The weight associated with the edge.**/
        private double weight;
        
        
        /**
         * Constructor for the edge.
         * @param d the desired second vertex for the edge.
         * @param w the weight associated with the edge.
         */
        public Edge(Vertex d, double w) {
            this.dest = d;
            this.weight = w;
        }
    }

    /**
     * This class represents an entry in the priority queue.
     *
     */
    private class Path implements Comparable<Path> {
        
        /**The destination vertex, or d.**/
        public Vertex     dest;
        /**The weight of the edge associated with the vertex.**/
        public double     weight;
        
        /**
         * This method returns the shortest path.
         * @param d the destination vertex
         * @param w the weight associated with the vertex.
         */
        public Path(Vertex d, double w) {
            this.dest = d;
            this.weight = w;
        }
        
        /**
         * EDIT THIS.
         * @param rhs ??
         * @return ??
         */
        public int compareTo(Path rhs) {
            double otherCost = rhs.weight;
            
            return this.weight < otherCost ? -1 
                    : this.weight > otherCost ? 1 : 0;
        }
    }

    /**
     * This class represents a vertex in the graph.
     * @author Mariano
     *
     */
    private class Vertex {
        
        /**The name of the vertex.**/
        private String name;
        /**Adjacent vertices.**/
        private List<Edge> adj;
        /**The weight of the edge.**/
        private double weight;
        /**The previous vertex on the shortest path.**/
        private Vertex prev;
        
        /**
         * This is the vertex constructor.
         * @param vtxName the name of the vertex.
         */
        public Vertex(String vtxName) { 
            this.name = vtxName; 
            this.adj = new LinkedList<Edge>(); 
            this.weight = Graph.INFINITY; 
            this.prev = null;
        }  
          
    }
    
}