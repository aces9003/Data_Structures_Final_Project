//HashMap226LP file

import java.util.Collection;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

/**
 * An interface specifying methods for a hash map.
 *
 * @author Matthias Philippine
 * 
 * @param <K>
 *            the type of keys stored in the hash map
 * @param <V>
 *            the type of the associated data stored in the map
 *
 */

public class HashMap226LP<K, V> implements HashMap226<K, V> {

    /** three.*/
    private static final int NUMBER = 3;
    /** initial capacity. */
    private static final int INIT_CAPACITY = 11;
    /** Shift mask to prevent negative numbers. */
    private static final int MASK = 0x7fffffff;
    /** num entries in array. */
    private int numPairs;
    /** current capacity. */
    private int sizeTable;
    /** array to replicate hashmap. */
    private Entry[] entries;


    /**
    private class.
    @author Matthias Philippine
    */
    private class Entry {

        /** key for map. */
        K key;
        /** value for map. */
        V value;
        /** boolean for ghost node when erased from map. */
        private boolean ghost = false;

        /**
        Constructor for Entry.
        @param k the key
        @param val the value
        */
        Entry(K k, V val) {
            this.value = val;
            this.key = k;
            this.ghost = false;
        }
    }

    /**
    Default constructor.
    */
    public HashMap226LP() {
        this(INIT_CAPACITY);
    }

    /**
    Parameter constructor.
    @param capacity is capacity
    */
    public HashMap226LP(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity is too small");
        }
        this.sizeTable = capacity;
        this.numPairs = 0;
        this.entries = (Entry[]) 
        java.lang.reflect.Array.newInstance(Entry.class, this.sizeTable);
        for (int i = 0; i < this.sizeTable; i++) {
            this.entries[i] = null;
        }
    }

    /**
     * Associate the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key, the old value
     * is replaced.
     *
     * @param key
     *            the key with which the specified value is to be associated
     * @param value
     *            value to be associated with the specified key (may be null)
     * @return 
     *         the previous value associated with key, or null if there was
     *         no previous mapping for key.  (A null return value can also
     *         indicate that the map previously associated null with key.)
     */
    public V put(K key, V value) {
        //check if value is valid
        if (value == null) {
            return null;
        }

        //resize and rehash if needed
        if (this.numPairs > (this.sizeTable / 2)) {
            this.resize(this.sizeTable);
        }
        
        //insert
        V temp = null;
        int hashed = hash(key);
        int where = this.findKey(key, hashed);

        //check if contains, replace
        if (where != -1) {
            temp = (V) this.entries[where].value;
            this.entries[where].value = value;
            return temp;            
        }

        //insert if not contains
        Entry temp2 = this.entries[hashed];
        while (temp2 != null && !temp2.ghost) {            
            hashed++;
            temp2 = this.entries[hashed % this.sizeTable];
        }
        //found empty slot
        if (temp2 == null) {
            this.entries[hashed] = new Entry(key, value);
            this.numPairs++;
        } else {              //found ghost slot that matches
            temp2.key = key;
            temp2.value = value;
            temp2.ghost = false;
        }
        return temp;
    }

    /**
    Return int of where this map contains a mapping for the specified key.
    @param key the key whose presence in this map is to be tested
      @param hashed the hash found and used as parameter
      @return index key if found, -1 if not  
     */
    public int findKey(Object key, int hashed) {

        Entry temp = this.entries[hashed];
        while (temp != null) {
            if (!temp.ghost && temp.key.equals(key)) {
                return hashed;
            }
            hashed++;
            temp = this.entries[(hashed) % this.sizeTable];
        }
        return -1;
    }


    /**
     * Return the unique value to which the specified key is mapped, or null if
     * this map contains no mapping for the key.  (A return value of null does
     * not necessarily indicate that the map contains no mapping for the key, as
     * null values are permitted in this map.  Use containsKey to distinguish
     * the no-mapping-present case from the map-with-null-value case.)
     *
     * @param key
     *             the key whose associated value is to be returned
     * @return
     *          the value to which the specified key is mapped, or null if this
     *          map contains no mapping for the key
     */
    public V get(Object key) {
        int where = hash(key);
        if (this.findKey(key, where) == -1) {
            return null;
        }
        return (V) this.entries[where].value;
    }


    /**
     * Return true if this map contains a mapping for the specified key.
     *
     * @param key
     *             the key whose presence in this map is to be tested
     * @return
     *             true if this map contains a mapping for the specified key
     */
    public boolean containsKey(Object key) {
        int hashed = hash(key);
        Entry temp = this.entries[hashed];
        while (temp != null) {
            if (temp.key.equals(key) && !temp.ghost) {
                return true;
            }
            temp = this.entries[hashed++];
        }
        return false;
    }


    /**
     * Remove the mapping for the specified key from this map if present.
     *
     * @param key
     *             the key whose mapping is to be removed from the map
     * @return
     *          the previous value associated with key, or null if there
     *          was no mapping for key.  (A null return can also indicate
     *          that the map previously associated null with the key.)
     */
    public V remove(Object key) { 
        if (!this.containsKey(key)) {
            return null;
        }

        //find key and delete value
        int hashed = hash(key);
        int where = this.findKey(key, hashed);
        if (where != -1) {
            V val = (V) this.entries[where].value;
            this.entries[where].value = null;
            this.entries[where].key = null;
            this.entries[where].ghost = true;
            return val;
        } 
        return null;
    }


    /**
     * Remove all of the mappings from this map.  The map will be empty
     * after this call returns.
     */
    public void clear() { 
        this.numPairs = 0;
        for (int i = 0; i < this.sizeTable; i++) {
            this.entries[i] = null;
        }
    }

    /**
     * Return the number of key-value mappings in this map.
     * 
     * @return
     *          the number of key-value mappings in this map
     */
    public int size() {
        return this.numPairs;
    }

    /**
     * Return a Set view of the keys contained in this map.  
     * 
     * @return
     *          a Set view of the keys contained in this map
     */
    public Set<K> keySet() {
        HashSet<K> kSet = new HashSet<K>();
        for (int i = 0; i < this.sizeTable; i++) {
            if (this.entries[i] != null) {
                kSet.add((K) this.entries[i].key);
            }
        }
        return  kSet;
    }


    /**
     * Return a Collection view of the values contained in this map. 
     * 
     * @return
     *          a Collection view of the values contained in this map
     */
    public Collection<V> values() { 
        Collection<V> vCollect = new ArrayList<V>();
        for (int i = 0; i < this.numPairs; i++) {
            if (this.entries[i] != null) {
                vCollect.add((V) this.entries[i].value);
            }
        }
        return vCollect;
    }

    /**
     another helper to rehash.
     @param entry the entry
     */
    private void enter(Entry entry) {
        int hashed = hash(entry.key);
        int where = this.findKey(entry.key, hashed);
        if (where != -1) {
            this.entries[where].value = entry.value;          
        } else {
            Entry temp = this.entries[hashed];
            while (temp != null) {            
                hashed++;
                temp = this.entries[hashed % this.sizeTable];
            }
            this.entries[hashed] = entry;
            this.numPairs++;
        } 
    }
    
    /**
    Helper  to re-hash entries[].
    @param noNeed is not needed, it is for interface.
     */
    public void resize(int noNeed) {
        int tempSize = this.sizeTable;
        this.sizeTable = this.nextPrime(tempSize);
        //this.sizeTable = this.isPrime(tempSize);
        HashMap226LP<K, V> temp = 
            new HashMap226LP<K, V>(this.sizeTable); 
        //re enter all values with new hash
        for (int i = 0; i < tempSize; i++) {
            if (this.entries[i] != null) {
                temp.enter(this.entries[i]);
            }
        }
        
        this.entries = temp.entries;
    }

    /**
     * Taken from the textbook. 
     * @author Weiss
     * Internal method to find a prime number at least as large as n.
     * @param n the starting number (must be positive).
     * @return a prime number larger than or equal to n.
     */
    private int nextPrime(int n) {
        n = n * 2 + 1; 
        while (!isPrime(n)) {
            n += 2;
        }

        return n;
    }

    /**
     * taken from textbook.
     * @author Weiss
     * Internal method to test if a number is prime.
     * Not an efficient algorithm.
     * @param n the number to test.
     * @return the result of the test.
     */
    private static boolean isPrime(int n) {
        if (n == 2 || n == NUMBER) {
            return true;
        }

        if (n == 1 || n % 2 == 0) {
            return false;
        }

        for (int i = NUMBER; i * i <= n; i += 2) {
            if (n % i == 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * Helper method makes the array number.
     * @param key the key object
     * @return the has number
     */
    private int hash(Object key) {
        if (key == null) {
            return 0;
        } 
        int hashed = key.hashCode();
        return ((hashed & MASK) % this.sizeTable);
    }
}