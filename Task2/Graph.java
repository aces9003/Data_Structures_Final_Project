import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;



/**
 *This class represents a graph ADT using a list to
 *store an adjacent verticies for task 2.
 * @author Mariano Pennini, Alex Sharata, Matthias Phillippine
 *
 */
public class Graph {

    /** map of graph to avoid linked list.*/
    private Map<String, Vertex> vertexMap; 
    /** count of elements in graph.*/
    private int count;

    /**
     * This class represents a vertex in the graph.
     * @author Matthias
     *
     */
    private class Vertex {
        
        /** The name of the vertex.**/
        private String name;
        /**Adjacent vertices.**/
        private ArrayList<Vertex> adj;
        /** int for in-degrees.*/
        private int inDegree;
        
        /**
         * This is the vertex constructor.
         * @param vtxName the name of the vertex.
         */
        public Vertex(String vtxName) { 
            this.name = vtxName; 
            this.adj = new ArrayList<Vertex>(); 
            this.inDegree = 0;
        }

        /**
        Get name.
        @return String char.
        */
        public String getChar() {
            return this.name;
        }

        /**
        Get inDegree.
        @return int char.
        */
        public int getInDegree() {
            return this.inDegree;
        }

        /**
        Get inDegree.
        */
        public int incrementInDegree() {
            return this.inDegree++;
        }

        /**
        Increment inDegree.
        */
        public int decrementInDegree() {
            return this.inDegree--;
        }

        /**
        Get adjacent verticies.
        @return String char.
        */
        public ArrayList<Vertex> getAdj() {
            return this.adj;
        }

        /**
        add adjacent verticies.
        */
        public void addAdj(Vertex v) {
             this.adj.add(v);
        }
    }

    /**
    Graph constructor.
    */
    public Graph() {
        this.vertexMap = new HashMap<String, Vertex>();
        this.count = 0;
    }

    /**
     * Add a new vertex to the graph.
     * @param sourceChar the name of the source vertex.
     */
    public void addVertex(String sourceChar) {
        Vertex v = new Vertex(sourceChar);
        if (!this.vertexMap.containsKey(sourceChar)) {
            this.vertexMap.put(sourceChar,v);
            this.count++;
        }
    }

    /**
     * Add a new edge to the graph.
     * @param sourceChar the name of the source vertex
     * @param destChar the name of the destination vertex.
     */
    public void addEdge(String sourceChar, String destChar) {
        
        //check if verticies exist in graph
        if (!this.vertexMap.containsKey(sourceChar)) {
            Vertex v = new Vertex(sourceChar);
            this.vertexMap.put(sourceChar, v);
            this.count++;
        }
        if (!this.vertexMap.containsKey(sourceChar)) {
            Vertex w = new Vertex(destChar);
            this.vertexMap.put(destChar, w);
            this.count++;
        }

        //add edge properties (indegree of dest, adj list source)
        this.vertexMap.get(destChar).incrementInDegree();
        this.vertexMap.get(sourceChar).adj.add(this.vertexMap.get(destChar));
    }

    /**
     * Remove a vertex and edges of the graph.
     * @param vertex the name of the source vertex
     */
    public void removeVertex(String vertex) {
         Vertex tempV = this.vertexMap.get(vertex);
        //update inDegrees for adjacent verticies
        ArrayList<Vertex> tempS = tempV.getAdj();
        for (int i = 0; i < tempS.size(); i++) {
            tempS.get(i).inDegree--;
        }

        //remove vertex from map
        vertexMap.remove(vertex);
    }

    /**
    Returns the vertex with no indegrees.
    @return Vertex with 0 inDegree.
    */
    public Vertex getInDegreeZeroVertex() {
        ArrayList<Vertex> temp = new ArrayList<Vertex>(vertexMap.values());
        for (int i = 0; i < temp.size(); i++) {
            Vertex tempV = temp.get(i);
            if (tempV.getInDegree() == 0) {
                return tempV;
            }
        }
        System.out.println("There's a problem, no 0 indegree");
        return null;
    }
}