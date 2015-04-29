//MinPQ.java


/*************************************************************************
 *  Compilation:  javac MinPQ.java
 *  
 *  Generic min priority queue implementation with a binary heap.
 *  Can be used with a comparator instead of the natural order,
 *  but the generic Key type must still be Comparable.
 *
 *  We use a one-based array to simplify parent and child calculations.
 *
 *  Can be optimized by replacing full exchanges with half exchanges 
 *  (ala insertion sort).
 *
 *************************************************************************/

import java.lang.reflect.Array;
import java.util.Comparator;
import java.util.NoSuchElementException;


/**
 *  The MinPQ class represents a priority queue of generic keys.
 *  It supports the usual insert and delete-the-maximum
 *  operations, along with methods for peeking at the maximum key,
 *  testing if the priority queue is empty, and iterating through
 *  the keys.
 * 
 *  This implementation uses a binary heap.
 *  The insert and delete-the-maximum operations take
 *  logarithmic amortized time.
 *  The max, size, and is-empty operations 
 *  take constant time.
 *  Construction takes time proportional 
 *  to the specified capacity or the number of
 *  items used to initialize the data structure.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 *  @author Mariano Pennini
 *  @param <Key> the key object containing priority and name
 */

public class MinPQ<Key> {
    /**Store items at indices 1 to N.**/
    private Key[] pq;
    /**Number of items on priority queue.**/
    private int numOfItems;
    /**Optional Comparator.**/
    private Comparator<Key> comparator;
    
    /**
     * Initializes an empty priority queue with the given initial capacity.
     * @param initCapacity the initial capacity of the priority queue
     */
    public MinPQ(int initCapacity) {
        int capacity = initCapacity + 1;
        this.pq = (Key[]) Array.newInstance(Key.class, capacity);
        this.numOfItems = 0;
    }

    /**
     * Initializes an empty priority queue.
     */
    public MinPQ() {
        this(1);
    }

    /**
     * Initializes an empty priority queue with the given initial capacity,
     * using the given comparator.
     * @param initCapacity the initial capacity of the priority queue
     * @param comparator1 the order in which to compare the keys
     */
    public MinPQ(int initCapacity, Comparator<Key> comparator1) {
        int capacity = initCapacity + 1;
        this.comparator = comparator1;
        this.pq = (Key[]) Array.newInstance(Key.class, capacity);
        this.numOfItems = 0;
    }

    /**
     * Initializes an empty priority queue using the given comparator.
     * @param comparator1 the order in which to compare the keys
     */
    public MinPQ(Comparator<Key> comparator1) {
        this(1, comparator1);
    }

    /**
     * Initializes a priority queue from the array of keys.
     * Takes time proportional to the number of keys, 
     * using sink-based heap construction.
     * @param keys the array of keys
     */
    public MinPQ(Key[] keys) {
        this.numOfItems = keys.length;
        this.pq = (Key[]) Array.newInstance(Key.class, this.numOfItems + 1);
        //this.pq = (Key[]) new Object[keys.length + 1]; 
        for (int i = 0; i < this.numOfItems; i++) {
            this.pq[i + 1] = keys[i];
        }
        for (int k = this.numOfItems / 2; k >= 1; k--) {
            this.sink(k);
        }
            
        assert this.isMinHeap();
    }
      


    /**
     * Is the priority queue empty?
     * @return true if the priority queue is empty; false otherwise
     */
    public boolean isEmpty() {
        return this.numOfItems == 0;
    }

    /**
     * Returns the number of keys on the priority queue.
     * @return the number of keys on the priority queue
     */
    public int size() {
        return this.numOfItems;
    }

    /**
     * Returns a largest key on the priority queue.
     * @return a largest key on the priority queue
     * @throws java.util.NoSuchElementException if the priority queue is empty
     */
    public Key min() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Priority queue underflow");
        }
            
        return this.pq[1];
    }
    
    /**
     * Returns a smallest value on the priority queue.
     * @return a smallest value on the priority queue
     * @throws java.util.NoSuchElementException if the priority queue is empty
     */
    public int minPriority() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Priority queue underflow");
        }
            
        return this.pq[1].priority;
    }

    /**
     * Helper function to double the size of the heap array.
     * @param capacity the current capacity of the array.
     */
    private void resize(int capacity) {
        assert capacity > this.numOfItems;
        Key[] temp = (Key[]) Array.newInstance(Key.class, capacity);
        //Key[] temp = (Key[]) new Object[capacity];
        for (int i = 1; i <= this.numOfItems; i++) {
            temp[i] = this.pq[i];
        }
        this.pq = temp;
    }


    /**
     * Adds a new key to the priority queue.
     * @param input the string associated with the key
     * @param i the priority associated with the key.
     */
    public void insert(int i, String input) {
        
        Key x = new Key(i, input);
        // double size of array if necessary
        if (this.numOfItems == this.pq.length - 1) {
            this.resize(2 * this.pq.length);
        }

        // add x, and percolate it down to maintain heap invariant
        
        this.pq[++this.numOfItems] = x;
        this.swim(this.numOfItems);
        assert this.isMinHeap();
    }
    
    /**
     * Searches through the Q array and returns the index 
     * position at which the input is located.
     * @param input the String to be searched for
     * @return the index at which the string is located
     */
    public int index(String input) {
        for (int i = 1; i <= this.numOfItems; i++) {
            if (this.pq[i].grocery.equals(input)) {
                return i;
            }
        }
        return -1;
    }
    /**
     * Removes and returns a smallest key on the priority queue.
     * @return a smallest key on the priority queue
     * @throws java.util.NoSuchElementException if priority queue is empty.
     */
    public Key delMin() {
        final int magic = 4;
        
        if (this.isEmpty()) {
            throw new NoSuchElementException("Priority queue underflow");
        }
        
        this.exch(1, this.numOfItems);
        Key min = this.pq[this.numOfItems--];
        this.sink(1);
        this.pq[this.numOfItems + 1] = null;
        if ((this.numOfItems > 0) 
                && (this.numOfItems == (this.pq.length - 1) / magic)) {
            this.resize(this.pq.length / 2);
        }
        assert this.isMinHeap();
        return min;
    }
    
    /**
     * Compares the priority numbers of elements and swaps 
     * them if the first element is larger than the second.
     * This means that the largest priority numbers will be located 
     * at the end and the smaller ones at the beginning of the PQ.
     * @param index the index of the first element to be considered.
     */
    public void bubble(int index) {
        for (int i = 1; i < this.numOfItems; i++) {
            if (this.pq[i].priority > this.pq[i + 1].priority) {
                this.exch(i, i + 1);
            }
        }
    }
    
    /**
     * Concatenates the groceries and their priority numbers into a string.
     * @return the concatenated string
     */
    public String toString() {
        String output = "";
        
        for (int i = 1; i <= this.numOfItems; i++) {
            
            output += this.pq[i].grocery + " "  
                   + "(" + (this.pq[i].priority) + ")" + "\n";
            
        }
        return output;
    }
    /**
     * Determines whether a grocery exists in the PQ.
     * I acknowledge that this is not an efficient way of doing this, 
     * but I could not get a more efficient method to work.
     * @param input the string to be searched for
     * @return true if the element is found, false otherwise
     */
    public boolean contains(String input) {
        if (this.index(input) >= 0) {
            return true;
        }
        return false;
    }
    /**
     * Adds 1 to the priority of the grocery in the PQ.
     * @param input the grocery whose priority is to be added.
     */
    public void addQPriority(String input) {
        
        this.pq[this.index(input)].priority++;
        this.sink(this.pq[this.index(input)].priority);
        
    }

    /***********************************************************************
    * Helper functions to restore the heap invariant.
    **********************************************************************/
    /**
     * Helps to restore the heap invariant once a value is removed.
     * Only used in the insert method.
     * @param k The initial index to be evaluated
     */
    private void swim(int k) {
        while (k > 1 && this.greater(k / 2, k)) {
            this.exch(k, k / 2);
            k = k / 2;
        }
    }
    
    /**
     * Helps to restore the heap invariant.
     * @param k the initial index to be evaluated
     * @return the final index position of the element.
     */
    private int sink(int k) {
        while (2 * k <= this.numOfItems) {
            int j = 2 * k;
            if (j < this.numOfItems && this.greater(j, j + 1)) {
                j++;
            }
            if (!this.greater(k, j)) {
                break;
            }
            this.exch(k, j);
            k = j;
        }
        return k;
    }

   /***********************************************************************
    * Helper functions for compares and swaps.
    **********************************************************************/
    
    /**
     * Determines if the value at position i is greater
     *  or less than the value at position j.
     * @param i the position in the array to be compared first
     * @param j the subsequent position in the array
     * @return true if... false if...
     */
    private boolean greater(int i, int j) {
        if (this.comparator == null) {
            
            return ((Comparable<Integer>) 
                    this.pq[i].priority).compareTo(this.pq[j].priority) > 0;
           
        } else {
            return this.pq[i].priority > this.pq[j].priority;
        }
    }
    
    /**
     * Exchanges elements according to their 
     *  index and associated priority number.
     * @param i the smaller index of the pq
     * @param j the larger index of the pq
     */
    private void exch(int i, int j) {
        Key swap = this.pq[i];
        this.pq[i] = this.pq[j];
        this.pq[j] = swap;
    }

    /**
     * Finds if subtree of pq[1..N] rooted 
     *  at k is a min heap using isMinHeap(int k).
     * @return true if heap is min, false otherwise
     */
    
    private boolean isMinHeap() {
        return this.isMinHeap(1);
    }

    /**Finds if subtree of pq[1..N] rooted at k is a min heap.
     * 
     * @param k the index of the node
     * @return true if heap is min, false otherwise
     */
    private boolean isMinHeap(int k) {
        if (k > this.numOfItems) {
            return true;
        }
        int left = 2 * k, right = 2 * k + 1;
        if (left  <= this.numOfItems && this.greater(k, left)) {
            return false;
        }
        if (right <= this.numOfItems && this.greater(k, right)) {
            return false;
        }
        return this.isMinHeap(left) && this.isMinHeap(right);
    }
    
    /**
     * Holds the String key and priority value that 
     * is associated with the grocery item.
     * @author Mariano
     *
     */
    private class Key {
        /**The priority of the item.**/
        private int priority;
        /**The grocery name.**/
        private String grocery;
        
        /**Constructor for a key.
         * 
         * @param p the priority value associated 
         *  with the key (How many times it has been inserted)
         * @param g the name of the grocery
         */
        public Key(int p, String g) {
            this.priority = p;
            this.grocery = g;
        }
    }

}