package BTree;

public class BTree<K> {

    //B树的阶
    private final int order;

    public BTree(int order) {
        this.order = order;
    }



    class BTreeNode<K> {

        BTreeNode<K> parent ;
        BTreeNode<K>[] children = new BTreeNode[order];



    }
}
