//Left-leaning Red-Black(LLRB) Tree的实现
/*LLRB Tree Properties：
1) Using colored nodes as our representation, the root node must be colored black.
2) No node can have two red children.
3) No red node can have a red parent (every red node’s parent is black).
4) In a balanced LLRB tree, every path to a null reference goes through the same number of black nodes.
*/
public class RedBlackTree<T extends Comparable<T>> {

    /* Root of the tree. */
    RBTreeNode<T> root;

    static class RBTreeNode<T> {

        final T item;
        boolean isBlack;
        RBTreeNode<T> left;
        RBTreeNode<T> right;

        /**
         * Creates a RBTreeNode with item ITEM and color depending on ISBLACK
         * value.
         * @param isBlack
         * @param item
         */
        RBTreeNode(boolean isBlack, T item) {
            this(isBlack, item, null, null);
        }

        /**
         * Creates a RBTreeNode with item ITEM, color depending on ISBLACK
         * value, left child LEFT, and right child RIGHT.
         * @param isBlack
         * @param item
         * @param left
         * @param right
         */
        RBTreeNode(boolean isBlack, T item, RBTreeNode<T> left,
                   RBTreeNode<T> right) {
            this.isBlack = isBlack;
            this.item = item;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * Creates an empty RedBlackTree.
     */
    public RedBlackTree() {
        root = null;
    }

    /**
     * Flips the color of node and its children. Assume that NODE has both left
     * and right children
     * @param node
     */
    void flipColors(RBTreeNode<T> node) {
        // TODO: YOUR CODE HERE
        if (node != null) {
            node.isBlack = !node.isBlack;
        }
        if (node.left != null) {
            node.left.isBlack = !node.left.isBlack;
        }
        if (node.right != null) {
            node.right.isBlack = !node.right.isBlack;
        }
    }

    /**
     * Rotates the given node to the right. Returns the new root node of
     * this subtree. For this implementation, make sure to swap the colors
     * of the new root and the old root!
     * @param node
     * @return
     */
    RBTreeNode<T> rotateRight(RBTreeNode<T> node) {
        // TODO: YOUR CODE HERE
        RBTreeNode<T> newSubRoot = node.left;
        node.left = newSubRoot.right;
        newSubRoot.right = node;
        boolean temp = node.isBlack;
        node.isBlack = newSubRoot.isBlack;
        newSubRoot.isBlack = temp;
        return newSubRoot;
    }

    /**
     * Rotates the given node to the left. Returns the new root node of
     * this subtree. For this implementation, make sure to swap the colors
     * of the new root and the old root!
     * @param node
     * @return
     */
    RBTreeNode<T> rotateLeft(RBTreeNode<T> node) {
        // TODO: YOUR CODE HERE
        RBTreeNode<T> newSubRoot = node.right;
        node.right = newSubRoot.left;
        newSubRoot.left = node;
        boolean temp = node.isBlack;
        node.isBlack = newSubRoot.isBlack;;
        newSubRoot.isBlack = temp;
        return null;
    }

    /**
     * Helper method that returns whether the given node is red. Null nodes (children or leaf
     * nodes) are automatically considered black.
     * @param node
     * @return
     */
    private boolean isRed(RBTreeNode<T> node) {
        return node != null && !node.isBlack;
    }

    /**
     * Inserts the item into the Red Black Tree. Colors the root of the tree black.
     * @param item
     */
    public void insert(T item) {
        root = insert(root, item); //能够处理root为空
        root.isBlack = true;
    }

    /**
     * Inserts the given node into this Red Black Tree. Comments have been provided to help break
     * down the problem. For each case, consider the scenario needed to perform those operations.
     * Make sure to also review the other methods in this class!
     * @param node
     * @param item
     * @return
     */
    private RBTreeNode<T> insert(RBTreeNode<T> node, T item) {
        // TODO: Insert (return) new red leaf node.
        if (node == null){
            return new RBTreeNode<>(false, item);
        }

        // TODO: Handle normal binary search tree insertion.
        int cmp = item.compareTo(node.item);
        if (cmp < 0){
            node.left = insert(node.left, item);
        } else if (cmp > 0){
            node.right = insert(node.right, item);
        } else {
            return node; //已存在的结点直接返回
        }

        // TODO: Rotate left operation
        //最终插入后，级数最高的node即为新结点的parent
        //左黑右红 --> 左旋
        //各级递归都有自身的node，插入的同时维护结点。
        // 颜色辅助函数isRed()免去检查是否为null
        if (isRed(node.right) && !isRed(node.left)){
            rotateLeft(node);
        }
        // TODO: Rotate right operation
        //左子、左孙结点同时为红 --> 右旋
        if (isRed(node) && isRed(node.right)){
             rotateRight(node);
        }
        // TODO: Color flip
        //左右子结点同时为红 --> 颜色翻转
        flipColors(node);
        return node; //fix this return statement
    }

}
