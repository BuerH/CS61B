package src;

public class UnionFind {
    private int[] disjSet;
    /* Creates a UnionFind data structure holding N items. Initially, all
       items are in disjoint sets. */
    public UnionFind(int N) {
        disjSet = new int[N];
        for (int i = 0; i < N; i++) {
            disjSet[i] = -1;
        }
    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        int cur = v;
        while (parent(cur) >= 0) {
            cur = parent(cur);
        }
        return -parent(cur);
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        return disjSet[v];
    }

    /* Returns true if nodes/vertices V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        return find(v1) == find(v2);
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid items are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
        if (v >= disjSet.length || v < 0) {
            throw new IllegalArgumentException();
        }
        if (disjSet[v] < 0) {
            return v;
        }
        disjSet[v] = find(disjSet[v]);
        return disjSet[v];
    }

    /* Connects two items V1 and V2 together by connecting their respective
       sets. V1 and V2 can be any element, and a union-by-size heuristic is
       used. If the sizes of the sets are equal, tie break by connecting V1's
       root to V2's root. Union-ing an item with itself or items that are
       already connected should not change the structure. */
    public void union(int v1, int v2) {

        int v1Root = find(v1);
        int v2Root = find(v2);
        if (v1Root == v2Root) {
            return;
        }
        int first = -disjSet[v1Root];
        int second = -disjSet[v2Root];

        if (first > second) {
            disjSet[v2Root] = v1Root;
            disjSet[v1Root] = -first-second;
        } else {
            disjSet[v1Root] = v2Root;
            disjSet[v2Root] = -first-second;
        }
    }

}
