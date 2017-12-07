package avl_tree;

import stack.LinkStack;

import java.util.Iterator;

final public class AVLTreeIterator <E extends Comparable<E>> implements Iterable<E> ,Iterator<E> {

    private AVLTreeNode<E> currentNode;
    private LinkStack<AVLTreeNode<E>> stack = new LinkStack<>();

    public AVLTreeIterator(AVLTreeNode<E> root) {
        this.currentNode = root;
        while(currentNode != null){
            stack.push(currentNode);
            currentNode = currentNode.lChild;
        }
    }

    @Override
    public boolean hasNext() {
        return !stack.empty() || currentNode != null;
    }

    @Override
    public E next() {
        while(currentNode != null){
            stack.push(currentNode);
            currentNode = currentNode.lChild;
        }
        AVLTreeNode<E> node  = stack.pop();
        if(node.rChild != null){
            currentNode = node.rChild;
        }

        return node.data;

    }


    @Override
    public Iterator<E> iterator() {
        return this;
    }
}
