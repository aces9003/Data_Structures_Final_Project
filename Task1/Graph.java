import java.io.PrintWriter;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Map;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.ArrayList;



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
    /**Union Find data structure to determine 
     * if two edges are already connected.**/
    private UnionFindQuickUnions unionFind;
    /**Array list to hold the final edges that will be included in the MST.**/
    private ArrayList<Edge> finalList;
    /**Comparator used to sort edges by weight in the minPQ.**/
    private Comparator<Edge> compare;
    /**Minimum priority queue of edges.**/
    private PriorityQueue<Edge> edges = new PriorityQueue<Edge>(this.compare);

    /**
     * Add a new edge to the graph.
     * @param sourceNumber the name of the source vertex
     * @param destNumber the name of the destination vertex.
     * @param weight the weight to be placed on the edge.
     * @param edgeString the string representation 
     *  of the edge in coordinate form
     */
    public void addEdge(int sourceNumber, int destNumber, 
            int weight, String edgeString) {
        
        Vertex v = this.getVertex(sourceNumber);
        Vertex w = this.getVertex(destNumber);
        Edge e = new Edge(v, w, weight, edgeString);
        v.adj.add(e);
        this.edges.add(e);
        
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
     * Helper method that calls kruskal's method
     * for finding shortest path on the existing graph in this class.
     */
    public void kruskalPublic() {
        this.kruskal();
    }
    
    /**
     * Performs Kruskal's algorithm on the graph.
     */
    private void kruskal() {
        
        this.unionFind = new UnionFindQuickUnions(this.edges.size());
        this.finalList = new ArrayList<Edge>();
        Edge theEdge;
        
        if (this.vertexMap.size() <= 1) {
            this.finalList.add(this.finalList.get(1));
            return;
        } 
        
        while (!this.edges.isEmpty()) {
            theEdge = this.edges.poll();
            int root1 = this.unionFind.find(theEdge.source.vtxNumber);
            int root2 = this.unionFind.find(theEdge.dest.vtxNumber);
            
            if (root1 != root2) {
                this.finalList.add(theEdge);
                this.minWeight += theEdge.weight;
                this.unionFind.union(root1, root2);
            }
            
        }

    }
    
    /**
     * Calls helper method to print the specifications of the new graph
     * to a text file.
     * @param writer the object to use when printing to file.
     * @param fileName the file to print to.
     */
    public void printGraphPublic(PrintWriter writer, String fileName) {
        this.printGraph(writer, fileName);
        writer.close();
    }
    
    /**
     * Print the graph to a file with name specified by user.
     * @param writer the PrintWriter object to use
     * @param fileName the name fo the file to print to
     */
    public void printGraph(PrintWriter writer, String fileName) {
        
        writer.println(this.minWeight);
        for (int i = 0; i < this.finalList.size(); i++) {
            writer.println(this.finalList.get(i).edgeString
                    + "\t" + this.finalList.get(i).weight);
        }
        
        
    }
    
    
    /**
     * This class represents a weighted edge in a graph.
     * @author Mariano
     *
     */
    private class Edge implements Comparable<Edge> {
        
        /**The first vertex in the edge.**/
        private Vertex source;
        /**The second vertex in the edge.**/
        private Vertex dest;
        /**The weight associated with the edge.**/
        private int weight;
        /**The string represntation of the edge in coordinate form.**/
        private String edgeString;
        
        
        
        /**
         * Constructor for the edge.
         * @param s the first vertex in the edge
         * @param d the second vertex in the edge.
         * @param w the weight associated with the edge.
         * @param stringEdge the string representation 
         *  of the edge in coordinate form
         */
        public Edge(Vertex s, Vertex d, int w, String stringEdge) {
            this.source = s;
            this.dest = d;
            this.weight = w;
            this.edgeString = stringEdge;
        }
        
        @Override
        public int compareTo(Edge otherEdge) {
            return this.weight - otherEdge.weight;
        }
        
        @Override
        public String toString() {
            String result = this.edgeString + " " + this.weight;
            return result;
            
        }
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
        
        /**
         * This is the vertex constructor.
         * @param vtxNum the name of the vertex.
         */
        public Vertex(int vtxNum) { 
            this.vtxNumber = vtxNum; 
            this.adj = new LinkedList<Edge>(); 
        }  
          
    }
    
    
}