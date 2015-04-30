import java.util.ArrayList;
import java.util.HashMap;



/**
 *This class represents a graph ADT using a list to
 *store an adjacent verticies for task 2.
 * @author Mariano Pennini, Alex Sharata, Matthias Phillippine
 *
 */
public class Graph {

    /** map of graph to avoid linked list.*/
    private HashMap<Character, Vertex> vertexMap; 
    /** count of elements in graph.*/
    private int count;

    /**
     * This class represents a vertex in the graph.
     * @author Matthias
     *
     */
    private class Vertex {
        
        /** The name of the vertex.**/
        private Character name;
        /**Adjacent vertices.**/
        private ArrayList<Vertex> adj;
        /** int for in-degrees.*/
        private int inDegree;
        
        /**
         * This is the vertex constructor.
         * @param vtxName the name of the vertex.
         */
        public Vertex(Character vtxName) { 
            this.name = vtxName; 
            this.adj = new ArrayList<Vertex>(); 
            this.inDegree = 0;
        }

        /**
        Set name.
        @param newName is char.
        */
        public void setChar(Character newName) {
            this.name = newName;
        }

        /**
        Get name.
        @return char.
        */
        public Character getChar() {
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
        @return int is degree.
        */
        public int incrementInDegree() {
            return this.inDegree++;
        }

        /**
        Increment inDegree.
        @return int is degree.
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
        @param v is vertex.
        */
        public void addAdj(Vertex v) {
            this.adj.add(v);
        }
    }

    /**
    Graph constructor.
    */
    public Graph() {
        this.vertexMap = new HashMap<Character, Vertex>();
        this.count = 0;
    }

    /**
     * Add a new vertex to the graph.
     * @param sourceChar the name of the source vertex.
     */
    public void addVertex(Character sourceChar) {
        Vertex v = new Vertex(sourceChar);
        if (!this.vertexMap.containsKey(sourceChar)) {
            this.vertexMap.put(sourceChar, v);
            this.count++;
        }
    }

    /**
     * Add a new edge to the graph.
     * @param sourceChar the name of the source vertex
     * @param destChar the name of the destination vertex.
     */
    public void addEdge(Character sourceChar, Character destChar) {
        
        //check if verticies exist in graph
        if (!this.vertexMap.containsKey(sourceChar)) {
            Vertex v = new Vertex(sourceChar);
            this.vertexMap.put(sourceChar, v);
            this.count++;
            //System.out.println("Added sourceChar.");
        }
        if (!this.vertexMap.containsKey(destChar)) {
            Vertex w = new Vertex(destChar);
            this.vertexMap.put(destChar, w);
            this.count++;
            //System.out.println("Added destChar");
        }

        //add edge properties (indegree of dest, adj list source)
        //System.out.println(destChar);
        this.vertexMap.get(destChar).incrementInDegree();
        this.vertexMap.get(sourceChar).adj.add(this.vertexMap.get(destChar));
    }

    /**
     * Remove a vertex and edges of the graph.
     * @param vertex the name of the source vertex
     */
    public void removeVertex(Character vertex) {
        Vertex tempV = this.vertexMap.get(vertex);
        //update inDegrees for adjacent verticies
        ArrayList<Vertex> tempS = tempV.getAdj();
        for (int i = 0; i < tempS.size(); i++) {
            tempS.get(i).inDegree--;
        }

        //remove vertex from map
        this.vertexMap.remove(vertex);
        this.count--;
    }

    /**
    Returns the vertex with no indegrees.
    @return Vertex with 0 inDegree.
    */
    public Vertex getInDegreeZeroVertex() {
        ArrayList<Vertex> temp = new ArrayList<Vertex>(this.vertexMap.values());
        for (int i = 0; i < temp.size(); i++) {
            Vertex tempV = temp.get(i);
            if (tempV.getInDegree() == 0) {
                return tempV;
            }
        }
        System.out.println("There's a problem, no 0 indegree");
        return null;
    }

    /**
    Sorts the map topologically.
    @return map of sorted values.
    */
    public HashMap<Character, ArrayList<Character>> topologicalSort() {
        HashMap<Character, ArrayList<Character>> dict = 
            new HashMap<Character, ArrayList<Character>>();
        Vertex last = new Vertex('a');

        //fill initial dictionary
        while (this.count > 1) {
            Vertex temp = this.getInDegreeZeroVertex();
            ArrayList<Character> postChars = this.getAdjacentList(temp);
            postChars.remove(temp.getChar());
            dict.put(temp.getChar(), postChars);
            if (this.count == 2) {
                last.setChar(temp.getAdj().get(0).getChar());
            }
            this.removeVertex(temp.getChar());
        }
        last.addAdj(null);
        dict.put(last.getChar(), new ArrayList<Character>());

        return dict;
    }

    /**
    Calls recursive method and generates values for map.
    @param src is root vertex in graph.
    @return ArrayList<Character> is list.
    */
    public ArrayList<Character> getAdjacentList(Vertex src) {

        //generate string to be parsed with all values
        String str = Character.toString(src.getChar());
        str += " " + this.getAdjacentListHelper(src.getAdj());

        //parse through string and add to array to be returned
        String[] split = str.split("\\s+");
        ArrayList<Character> returning = new ArrayList<Character>();
        for (int i = 0; i < split.length; i++) {
            if (!returning.contains(split[i].charAt(0))) {
                returning.add(split[i].charAt(0));
            }
        }
        return returning;
    }

    /**
    Helper method.
    @param adjacent to start
    @return String values to add.
    */
    private String getAdjacentListHelper(ArrayList<Vertex> adjacent) {
        //check if end
        if (adjacent.isEmpty()) {
            return "";
        }
        String str = "";
        for (int i = 0; i < adjacent.size(); i++) {
            str += adjacent.get(i).getChar() + " ";
            str += this.getAdjacentListHelper(adjacent.get(i).getAdj());
        }
        return str;
    }

    /**
    Testing method.
    @param dict is dictionary.
    */
    public void printTest(HashMap<Character, ArrayList<Character>> dict) {
        ArrayList<Character> keys = new ArrayList<Character>(dict.keySet());
        ArrayList<ArrayList<Character>> values = 
            new ArrayList<ArrayList<Character>>(dict.values());

        for (int i = 0; i < keys.size(); i++) {
            System.out.print("Key: " + keys.get(i) + ".   Values: ");
            if (!values.get(i).isEmpty()) {
                for (int j = 0; j < values.get(i).size(); j++) {
                    System.out.print(values.get(i).get(j) + " ");
                }
            }
            System.out.println();
        }
    }
}