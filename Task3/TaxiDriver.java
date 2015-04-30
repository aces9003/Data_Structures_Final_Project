// TaxiDriver.java

import java.util.Comparator;

/**
 * A TaxiDriver that represents an individual TaxiDriver
 * at one of the driver locations specified in driverLocations.txt.
 */
public class TaxiDriver implements Comparable<TaxiDriver> {
    /** driver ID from driverLocations.txt. */
    public int driverID;
    
    /** driver location from driverLocations.txt. */
    public String location;
    
    /** shortest path from driver to client found via Dijkstra's. */
    private String shortestPath;
    
    /** path weight of shortest path. */
    private Integer pathWeight;
    
    /**
     * 2 argument constructor for TaxiDriver.
     * @param id driver's ID
     * @param loc driver's starting location
     */
    public TaxiDriver(int id, String loc) {
        this.driverID = id;
        this.location = loc;
    }
    
    /**
     * 4 argument constructor for TaxiDriver.
     * @param id driver's ID
     * @param loc driver's starting location
     * @param path driver's shortest path to client
     * @param weight weight of shortest path
     */
    public TaxiDriver(int id, String loc, String path, int weight) {
        this.driverID = id;
        this.location = loc;
        this.shortestPath = path;
        this.pathWeight = weight;
    }
    
    // Accessors and Mutators
    
    /**
     * Setter for driver's shortest path.
     * @param path the String representation of the path
     */
    public void setShortestPath(String path) {
        this.shortestPath = path;
    }
    
    /**
     * Accessor for shortest path.
     * @return String representation of shortest path
     */
    public String getShortestPath() {
        return this.shortestPath;
    }
    
    /**
     * Mutator for shortest path's weight.
     * @param weight value of shortest path's weight
     */
    public void setPathWeight(int weight) {
        this.pathWeight = weight;
    }
    
    /**
     * Accessor for shortest path's weight.
     * @return value of shortest path's weight
     */
    public Integer getPathWeight() {
        return this.pathWeight;
    }
    
    @Override
    public int compareTo(TaxiDriver rhs) {
        return Comparators.PATHWEIGHT.compare(this, rhs);
    }
    
    /**
     * Comparator class to define how TaxiDriver objects are sorted.
     * @author Alex Sharata (ashara1)
     *
     */
    public static class Comparators {
        // Lambda expressions are used -- must use Java 8 or above.
        
        /** User defined comparator to compare TaxiDriver's pathweights. */
        public static final Comparator<TaxiDriver> PATHWEIGHT = 
                (TaxiDriver d1, TaxiDriver d2) -> 
                d1.getPathWeight().compareTo(d2.getPathWeight());
    }
}
