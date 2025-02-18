package hashmap;

import java.util.*;
import java.util.stream.Collector;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    // You should probably define some more!
    private double loadFactor;
    private int size = 0;
    /** Constructors */
    public MyHashMap() {
        buckets = createTable(16);
        this.loadFactor = 0.75;
    }

    public MyHashMap(int initialSize) {
        buckets = createTable(initialSize);
        this.loadFactor = 0.75;
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        buckets = createTable(initialSize);
        this.loadFactor = maxLoad;
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        Collection<Node>[] table = new Collection[tableSize];
        for (int i = 0; i < tableSize; i++) {
            table[i] = createBucket();
        }
        return table;
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!
    @Override
    public void clear() {
        size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        if (size == 0) {
            return false;
        }
        return get(key) != null;
    }

    @Override
    public V get(K key) {
        if (size == 0) {
            return null;
        }
        int idx = key.hashCode() % buckets.length;
        if (idx < 0) {
            idx += buckets.length;
        }
        Iterator it = buckets[idx].iterator();
        Node cur = null;
        while (it.hasNext()) {
            cur = (Node)it.next();
            if (cur.key.equals(key)) {
                return cur.value;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        Node cur = null;
        if (size/buckets.length >= loadFactor) {
            Collection<Node>[] newBuckets = createTable(buckets.length * 2);
            for (int i = 0; i < buckets.length; i++) {
                Iterator it = buckets[i].iterator();
                while (it.hasNext()) {
                    cur = (Node)it.next();
                    int idx = cur.key.hashCode() % (newBuckets.length);
                    if (idx < 0) {
                        idx += newBuckets.length;
                    }
                    newBuckets[idx].add(cur);
                }
            }
            buckets = newBuckets;
        }

        int idx = key.hashCode() % buckets.length;
        if (idx < 0) {
            idx += buckets.length;
        }
        Iterator it = buckets[idx].iterator();
        while (it.hasNext()) {
            cur = (Node)it.next();
            if (cur.key.equals(key)) {
                cur.value = value;
                return;
            }
        }
        buckets[idx].add(new Node(key,value));
        size++;
    }

    @Override
    public Set<K> keySet() {
        HashSet<K> set = new HashSet<>();
        for (int i = 0; i < buckets.length; i++) {
            Iterator it = buckets[i].iterator();
            while (it.hasNext()) {
                set.add(((Node)it.next()).key);
            }
        }
        return set;
    }

    @Override
    public V remove(K key) {
        Node cur = null;
        int idx = key.hashCode() % buckets.length;
        if (idx < 0) {
            idx += buckets.length;
        }
        Iterator it = buckets[idx].iterator();
        while (it.hasNext()) {
            cur = (Node)it.next();
            if (cur.key.equals(key)) {
                buckets[idx].remove(cur);
                return cur.value;
            }
        }
        return null;
    }

    @Override
    public V remove(K key, V value) {
        Node cur = null;
        int idx = key.hashCode() % buckets.length;
        if (idx < 0) {
            idx += buckets.length;
        }
        Iterator it = buckets[idx].iterator();
        while (it.hasNext()) {
            cur = (Node)it.next();
            if (cur.key.equals(key) && cur.value.equals(value)) {
                buckets[idx].remove(cur);
                return cur.value;
            }
        }
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }
}
