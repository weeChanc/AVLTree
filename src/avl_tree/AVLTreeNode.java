package avl_tree;

public class AVLTreeNode<E extends Comparable<E>>  {

    E data;
    int balance;
    AVLTreeNode<E> lChild, rChild;

    public AVLTreeNode(E data) {
        this.data = data;
    }

    public boolean isLeaf(){
        return lChild == null && rChild == null;
    }

}
