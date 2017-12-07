package avl_tree;

import com.google.gson.Gson;


public class AVLTree <E extends Comparable<E>> {


    private static final int LH = 1;
    private static final int RH = -1;
    private static final int EH = 0;


    private int depth;
    private AVLTreeNode<E> root;

    private static boolean taller = false;

    @Override
    public String toString() {
        return new Gson().toJson(root);
    }

    public void insert(E data){
        root = insert(data,root);
    }

    private AVLTreeNode<E> insert(E data , AVLTreeNode<E> node){


        if(node == null){
            node = new AVLTreeNode<>(data);
            node.balance = EH;
            taller = true;
            return node;

        }else if(data.compareTo(node.data) == 0 ){
            //存在相同的节点
            taller = false;
            return null;
        }else if(data.compareTo(node.data) > 0){
            //插入的点大,插入到右子树
            AVLTreeNode<E> rc =  insert(data,node.rChild);
            if(rc != null){
                node.rChild = rc;
            }

            if(taller) {
                switch (node.balance) {
                    case EH:
                        node.balance = RH;
                        break;
                    case LH:
                        node.balance = EH;
                        break;
                    case RH:
                        node = rightBalance(node);
                        break;
                }
            }

        }else{
            //插入的点小,插入到左子树
            AVLTreeNode<E> lc = insert(data , node.lChild);
            if(lc != null){
                node.lChild = lc;
            }

            if(taller) {
                switch (node.balance) {
                    case EH:
                        node.balance = LH;
                        break;
                    case RH:
                        node.balance = EH;
                        break;
                    case LH:
                        node = leftBalance(node);
                        break;
                }
            }
        }

        return node;

    }

    public AVLTreeNode<E> search(E element ){
        return search(element,root);
    }

    private AVLTreeNode<E> search(E element , AVLTreeNode<E> currentNode ){
        if(currentNode == null) return null;
        int cmpResult = element.compareTo(currentNode.data);
        if(cmpResult == 0 ){
            return currentNode;
        }else if(cmpResult > 0){
            return search(element,currentNode.rChild);
        }else {
            return search(element, currentNode.lChild);
        }
    }

    public void clear(){
        root = null;
        taller = false;
    }

    public void delete(E element){

    }

//    private void delete()



    private AVLTreeNode<E> leftRotate(AVLTreeNode<E> treeNode){
        AVLTreeNode<E> node = treeNode.rChild;
        treeNode.rChild = node.lChild;
        node.lChild = treeNode;
        return node;
    }

    private AVLTreeNode<E> rightRoate(AVLTreeNode<E> treeNode){
        AVLTreeNode<E> node = treeNode.lChild;
        treeNode.lChild = node.rChild;
        node.rChild = treeNode;
        return node;
    }

    private AVLTreeNode<E> rightBalance(AVLTreeNode<E> node){
        switch(node.rChild.balance){
            case RH : //RR型
                node.balance = 0;
                node.rChild.balance = 0 ;
                return leftRotate(node);
            case LH : // RL 型

                switch(node.rChild.lChild.balance){
                    case LH:
                        node.balance = RH;
                        node.rChild.balance = EH;
                        break;
                    case EH :
                        node.balance = EH;
                        node.rChild.balance = EH;
                        break;
                    case RH :
                        node.balance = EH;
                        node.rChild.balance = EH;
                }
                node.rChild.lChild.balance = EH;
                node.rChild = rightRoate(node.rChild);
                node =  leftRotate(node);
        }

        return node;
    }

    private AVLTreeNode<E> leftBalance(AVLTreeNode<E> node){

        switch(node.lChild.balance){
            case LH : //LL型
                node.balance = 0;
                node.lChild.balance = 0 ;
                return rightRoate(node);
            case RH : // LR 型

                switch(node.lChild.rChild.balance){
                    case LH:
                        node.balance = RH;
                        node.lChild.balance = EH;
                        break;
                    case EH :
                        node.balance = EH;
                        node.lChild.balance = EH;
                        break;
                    case RH :
                        node.balance = EH;
                        node.lChild.balance = LH;
                }
                node.lChild.rChild.balance = EH;
                node.lChild = leftRotate(node.lChild);
                node =  rightRoate(node);
        }

        return node;

    }

    public AVLTreeIterator<E> iterator(){
        return new AVLTreeIterator<>(root);
    }



}

