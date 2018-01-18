package avl_tree;


import static avl_tree.AVLTreeNodeStatus.*;

public class AVLTreeNode<E extends Comparable<E>>   {

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

    //获取AVLTree的孩子状态,即左边有,还是右边有,还是两边都有孩子,或是没有孩子节点.
    public AVLTreeNodeStatus childTreeStatus() {
        if (isLeaf()) return NONE;
        if (lChild == null && rChild != null) return RIGHT;
        if (lChild != null && rChild == null) return LEFT;
        return BOTH;
    }
}

enum AVLTreeNodeStatus {
    LEFT, RIGHT, BOTH, NONE
}