package src;

import java.util.Stack;

public class RedBlackTree<T extends Comparable<T>> {

    /* Root of the tree. */
    public RBTreeNode<T> root;

    public static class RBTreeNode<T> {

        public final T item;
        public boolean isBlack;
        public RBTreeNode<T> left;
        public RBTreeNode<T> right;

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
        public RBTreeNode(boolean isBlack, T item, RBTreeNode<T> left,
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
    public void flipColors(RBTreeNode<T> node) {
        node.isBlack = !node.isBlack;
        node.left.isBlack = !node.left.isBlack;
        node.right.isBlack = !node.right.isBlack;
    }

    /**
     * Rotates the given node to the right. Returns the new root node of
     * this subtree. For this implementation, make sure to swap the colors
     * of the new root and the old root!
     * @param node
     * @return
     */
    public RBTreeNode<T> rotateRight(RBTreeNode<T> node) {
        boolean rNode = node.left.isBlack;//记录左孩子颜色
        RBTreeNode<T> lc = node.left.right;
        node.left.isBlack = node.isBlack; //左孩子颜色变为根的颜色
        RBTreeNode<T> mid = node;         //记录根节点
        node = node.left;                 //左孩子变为根节点
        mid.isBlack = rNode;              //右孩子变为原来左孩子颜色
        mid.left = lc;                    //原先的根节点右节点变为原左孩子的右孩子
        node.right = mid;                 //现在根节点的右节点变为之前的根节点
        return node;
    }

    /**
     * Rotates the given node to the left. Returns the new root node of
     * this subtree. For this implementation, make sure to swap the colors
     * of the new root and the old root!
     * @param node
     * @return
     */
    protected RBTreeNode<T> rotateLeft(RBTreeNode<T> node) {
        boolean rNode = node.right.isBlack; //右孩子颜色
        RBTreeNode<T> rc = node.right.left;
        node.right.isBlack = node.isBlack;  //右孩子颜色变为根的颜色
        RBTreeNode<T> mid = node;           //记录根节点的数据
        node = node.right;                  //根节点变为右节点
        mid.isBlack = rNode;                //原先的根节点颜色变为右孩子颜色
        mid.right = rc;                     //原先的根节点右节点变为原右孩子的左孩子
        node.left = mid;                    //左节点变为原先的根节点
        return node;
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
        root = insert(root, item);
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
        RBTreeNode<T> newNode = new RBTreeNode<>(false, item);
        if (node == null) {
            node = newNode;
            return node;
        }
        // TODO: Handle normal binary search tree insertion.
        RBTreeNode<T> mid = node;
        Stack<RBTreeNode<T>> stack = new Stack<>();
        while (mid != null) {
            parent = mid;
            if (item.compareTo(mid.item) > 0) {
                mid = mid.right;
                if (parent.right == null) {
                    parent.right = newNode;
                }
            } else if (item.compareTo(mid.item) <= 0) {
                mid = mid.left;
                if (parent.left == null) {
                    parent.left = newNode;
                }
            }
        }
        mid = node;
        RBTreeNode<T> gparent = null;
        while (mid != parent) {
            gparent = mid;
            if (item.compareTo(mid.item) > 0) {
                mid = mid.right;
            } else if (item.compareTo(mid.item) <= 0) {
                mid = mid.left;
            }
        }
        // TODO: Rotate left operation
        if ((parent != node || parent.left == null) && isRed(parent.right)) {
            parent = rotateLeft(parent);
            if (gparent != null) {
                if (parent.item.compareTo(gparent.item) > 0) {
                    gparent.right = parent;
                } else if (parent.item.compareTo(gparent.item) <= 0) {
                    gparent.left = parent;
                }
            }
        }
        // TODO: Rotate right operation
        if (isRed(parent)) {
            if (isRed(parent.left)) {

                parent = rotateRight(gparent);
            }
        }
        // TODO: Color flip
        if (isRed(parent.left) && isRed(parent.right)) {
            flipColors(parent);
        }
        return node; //fix this return statement
    }

}