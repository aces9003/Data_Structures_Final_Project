import java.util.List;
import java.util.Map;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.NoSuchElementException;



/**
 *This class represents a graph ADT using a hash map to
 *store an adjacency list of edges.
 * @author Mariano Pennini
 *
 */
public class Graph {
    
    /**Represents an infinity value.**/
    public static final int INFINITY = Integer.MAX_VALUE;
    /**The hash map to be used to store an array 
     * list of vertices with number key.**/
    private Map<Integer, Vertex> vertexMap = new HashMap<Integer, Vertex>();
    /**The final minimum weight to be reported to the user.**/
    private int minWeight = 0;
    /**Minimum priority queue that will hold the 
     * edges according to their weights.**/
    private MinPQ<Edge> edgePQ = new MinPQ<Edge>();
    /**Union Find data structure to determine 
     * if two edges are already connected.**/
    private UnionFindQuickUnions unionFind;

    /**
     * Add a new edge to the graph.
     * @param sourceNumber the name of the source vertex
     * @param destNumber the name of the destination vertex.
     * @param weight the weight to be placed on the edge.
     */
    public void addEdge(int sourceNumber, int destNumber, int weight) {
        
        String edgeString = sourceNumber + " " + destNumber;
        //System.out.println(edgeString);
        Vertex v = this.getVertex(sourceNumber);
        //System.out.print("Source: " + sourceNumber);
        Vertex w = this.getVertex(destNumber);
        //System.out.println("Dest: " + destNumber);
        //Edge e = new Edge(w, weight);
        v.adj.add(new Edge(w, weight));
        this.edgePQ.insert(weight, edgeString);
        //System.out.println("SIZE " + edgePQ.size());
        
        
    }
    
    /**
     * Driver routine to handle unreachables and print total cost.
     * It calls recursive routine to print shortest path to
     * destNode after a shortest path algorithm has run.
     * 
     * @param destNumber the name of the destination vertex.
     */
    public void printPath(int destNumber) {
        Vertex w = this.vertexMap.get(destNumber);
        if (w == null) {
            throw new NoSuchElementException("Destination vertex not found");
        } else if (w.weight == INFINITY) {
            System.out.println("Vertex " + destNumber + " is unreachable");
        } else {
            System.out.print(w.weight);
            this.printPath(w);
            System.out.println();
        }
    }
    
    /**
     * If vertexName is not present, add it to vertexMap.
     * In either case, return the Vertex.
     * @param vertexNumber the name of the vertex requested.
     * @return the vertex V that was requested.
     */
    private Vertex getVertex(int vertexNumber) {
        Vertex v = this.vertexMap.get(vertexNumber);
        if (v == null) {
            v = new Vertex(vertexNumber);
            this.vertexMap.put(vertexNumber, v);
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
        System.out.print(dest.vtxNumber);
    }
    
    /**
     * This class represents a weighted edge in a graph.
     * @author Mariano
     *
     */
    private class Edge {
        
        /**The second vertex in the edge.**/
        private Vertex dest;
        /**The weight associated with the edge.**/
        private int weight;
        
        
        /**
         * Constructor for the edge.
         * @param d the desired second vertex for the edge.
         * @param w the weight associated with the edge.
         */
        public Edge(Vertex d, int w) {
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
        public int     weight;
        
        /**
         * This method returns the shortest path.
         * @param d the destination vertex
         * @param w the weight associated with the vertex.
         */
        public Path(Vertex d, int w) {
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
     * {\Performs Kruska'ls algorithm on the graph.
     */
    public void kruskal() {
        while (!this.edgePQ.isEmpty()) {
           
        }
        
        
        
        
       /** while (!pq.isEmpty() && mst.size() < G.V() - 1) {
            Edge e = pq.delMin();
            int v = e.either();
            int w = e.other(v);
            if (!uf.connected(v, w)) { // v-w does not create a cycle
                uf.union(v, w);  // merge v and w components
                mst.enqueue(e);  // add edge e to mst
                weight += e.weight();
            }
        }**/

    }

    /**
     * This class represents a vertex in the graph.
     * @author Mariano
     *
     */
    private class Vertex {
        
        /**The name of the vertex.**/
        private int vtxNumber;
        /**Adjacent vertices.**/
        private List<Edge> adj;
        /**The weight of the edge.**/
        private int weight;
        /**The previous vertex on the shortest path.**/
        private Vertex prev;
        
        /**
         * This is the vertex constructor.
         * @param vtxNum the name of the vertex.
         */
        public Vertex(int vtxNum) { 
            this.vtxNumber = vtxNum; 
            this.adj = new LinkedList<Edge>(); 
            this.weight = Graph.INFINITY; 
            this.prev = null;
        }  
          
    }
    
    
}