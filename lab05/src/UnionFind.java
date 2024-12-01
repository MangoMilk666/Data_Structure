//work with non-negative integers as the items in our disjoint sets.
public class UnionFind {
    // TODO: Instance variables
    private int[] parent;


    /* Creates a UnionFind data structure holding N items. Initially, all
       items are in disjoint sets. */
    public UnionFind(int N) {
        // TODO: YOUR CODE HERE
        parent = new int[N];
        for (int i=0; i< N; i++){
            parent[i] = -1;
        }
    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        // TODO: YOUR CODE HERE
        if (v < 0 || v >= parent.length){
            throw new IllegalArgumentException("Out of bounds of set");
        }
        int root = find(v);
        return (-1)*parent(root);
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        // TODO: YOUR CODE HERE
        if (v < 0 || v >= parent.length){
            throw new IllegalArgumentException("Out of bounds of set");
        }
        return parent[v];
    }

    /* Returns true if nodes/vertices V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        // TODO: YOUR CODE HERE
        int p = find(v1);
        int q = find(v2);
        if (p == q){
            return true;
        }
        return false;
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid items are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
        // TODO: YOUR CODE HERE
        //通过两个遍历进行路径压缩
        if (v < 0 || v >= parent.length){
            throw new IllegalArgumentException("Out of bounds of set");
        }
        int p = v;
        //root上方为weight的相反数
        while (parent[p] >= 0){
            p = parent[p];
        }
        int root = p;
        p = v;
        while (parent[p] >= 0){
            int temp = parent[p];
            parent[p] = root;
            p = temp;
        }
        //root结点会返回自身
        return root;
    }

    //  Recursive method, more concise and understandable than iteration
    public int find_recursive(int v) {
        if (v < 0 || v >= parent.length) {
            throw new IllegalArgumentException("Out of bounds of set");
        }
        if (parent[v] < 0) {
            return v; // 如果当前节点是根节点，直接返回
        } else {
            parent[v] = find(parent[v]); // 路径压缩，将当前节点直接连接到根节点
            return parent[v];
        }
    }

    /* Connects two items V1 and V2 together by connecting their respective
       sets. V1 and V2 can be any element, and a union-by-size heuristic is
       used. If the sizes of the sets are equal, tie break by connecting V1's
       root to V2's root. Union-ing an item with itself or items that are
       already connected should not change the structure. */
    public void union(int v1, int v2) {
        // TODO: YOUR CODE HERE
        int p = find(v1);
        int q = find(v2);
        if (p == q){
            return;
        }
        if (parent[p] < parent[q]){
            //q树入p树
            parent[p] += parent[q];
            parent[q] = p;
        } else {
            //p树入q树
            parent[q] += parent[p];
            parent[p] = q;
        }
    }

}
