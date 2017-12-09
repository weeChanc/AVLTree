package avl_tree;


import static avl_tree.AVLTreeNodeStatus.*;

public class AVLTreeNode<E extends Comparable<E>> implements Cloneable {

    public E data;
    public int balance;
    public AVLTreeNode<E> lChild, rChild;

    public AVLTreeNode(E data) {
        this.data = data;
    }

    public AVLTreeNode() {
    }

    private boolean isLeaf() {
        return lChild == null && rChild == null;
    }

    public AVLTreeNodeStatus childTreeStatus() {
        if (isLeaf()) return NONE;
        if (lChild == null && rChild != null) return RIGHT;
        if (lChild != null && rChild == null) return LEFT;
        return BOTH;
    }

    @Override
    protected AVLTreeNode<E> clone() {

        AVLTreeNode<E> lc = null;
        AVLTreeNode<E> rc = null;
        AVLTreeNode<E> root = null;

        if (lChild != null)
            lc = (AVLTreeNode<E>) lChild.clone();

        if (rChild != null)
            rc = (AVLTreeNode<E>) rChild.clone();

        try {
            root = (AVLTreeNode<E>) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        root.lChild = lc;
        root.rChild = rc;
        return root;
    }
}

enum AVLTreeNodeStatus {
    LEFT, RIGHT, BOTH, NONE
}