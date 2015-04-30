
/**
 * A union find data structure with int type items.
 * @author Mariano Pennini
 * Find method adapted from Weiss.
 *
 */
public class UnionFindQuickUnions implements UnionFind {
    /**The initial number of elements specified in the command-line.*/
    private int argument;
    /**The array to hold integers from 0-argument.*/
    private int[] forest;
    /**The current number of subsets.*/
    private int subsetCount;
    
    /**
     * Constructor that initializes the forest to 
     * contain the number of subsets specified by the command line.
     * @param commandLineNum The number of subsets specified in the 
     *  command line.
     */
    public UnionFindQuickUnions(int commandLineNum) {
        if (commandLineNum < 0) {
            throw new IllegalArgumentException();
        }
        this.subsetCount = 0;
        this.argument = commandLineNum;
        this.forest = new int[commandLineNum];
        for (int i = 0; i < commandLineNum; i++) {
            this.forest[i] = -1;
        }
    }
    
    /**
     * Determine the name of the set containing the specified element.
     * @param x the element whose set we wish to find
     * @return the name of the set containing x
     */
    public int find(int x) {
        
        if (this.forest[x] < 0) {
            
            return x;
            
        } else {
            
            return this.find(this.forest[x]);
        }
        
    }
    /**
     * Method to indicate whether two integers are in the same set.
     * @param a the first integer
     * @param b the second integer
     * @return true if the inteeres belong to the same set, false otherwise.
     */
    public boolean isConnected(int a, int b) {
        
        if (this.find(a) == this.find(b)) {
            return true;
        }
        return false;
    }
    
    /**
     * Merge two sets if they are not already the same set.
     * @param a an item in the first set to be merged (need not be set name)
     * @param b an item in the second set to be merged (need not be set name)
     */
    public void union(int a, int b) {
        
        if (this.forest[this.find(b)] < this.forest[this.find(a)]) {
            this.forest[this.find(b)] += this.forest[this.find(a)];
            this.forest[this.find(a)] = this.find(b);
        } else {
            this.forest[this.find(a)] += this.forest[this.find(b)];
            this.forest[this.find(b)] = this.find(a);
        }
          
    }
    
    /**
     * Return the number of subsets in the structure.
     * @return the number of subsets
     */
    public int getNumSubsets() {
        this.subsetCount = 0;
        
        for (int i = 0; i < this.argument; i++) {
            if (this.forest[i] < 0) {
                this.subsetCount++;
            }
            
        }
        return this.subsetCount;
    }
    
    /**
     * Returns a String representation of the implementation.  Normally
     * this would never be part of an interface like this, but will help us
     * test your implementation in a consistent way.  See assignment handout.
     * @return a String representing the current state of the structure
     */
    public String getCurrentState() {
        String rep = "";
        for (int i = 0; i < this.argument; i++) {
            rep += i + ": " + this.forest[i] + "\n";
        }
        return rep;
    }
}
