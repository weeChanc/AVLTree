package avl_tree;




public class AVLTree<E extends Comparable<E>> {


    private static final int LH = 1;
    private static final int RH = -1;
    private static final int EH = 0;

    public static int c = 0 ;


    private int depth;
    private AVLTreeNode<E> root;

    private boolean taller = false;
    private boolean shorter = false;

    @Override
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        for(E data : iterator()){
            sb.append(data.toString() + "\n");
        }

        return sb.toString();
    }

    /**
     * 插入操作的外部接口
     *
     * @param data 插入的内容
     */
    public void insert(E data) {
        root = insert(data, root);
    }


    /**
     * @param data 插入的内容
     * @param node 比较的节点
     * @return 返回修高后的节点
     */
    private AVLTreeNode<E> insert(E data, AVLTreeNode<E> node) {

        if (node == null) {
            node = new AVLTreeNode<>(data);
            node.balance = EH;
            taller = true;
            return node;

        } else if (data.compareTo(node.data) == 0) {
            //存在相同的节点
            taller = false;
            return node;
        } else if (data.compareTo(node.data) > 0) {
            //插入的点大,插入到右子树
            node.rChild = insert(data, node.rChild);

            //树长高
            if (taller) {
                //修改平衡因子
                switch (node.balance) {
                    case EH:
                        node.balance = RH;
                        taller = true;
                        break;
                    case LH:
                        node.balance = EH;
                        taller = false;
                        break;
                    case RH:
                        node = rightBalance(node);
                        taller = false;
                        break;
                }
            }

        } else {
            //插入的点小,插入到左子树
            node.lChild = insert(data, node.lChild);
            //树长高
            if (taller) {
                //修改平衡因子
                switch (node.balance) {
                    case EH:
                        taller = true;
                        node.balance = LH;
                        break;
                    case RH:
                        taller = false;
                        node.balance = EH;
                        break;
                    case LH:
                        node = leftBalance(node);
                        taller = false;
                        break;
                }
            }
        }

        return node;

    }

    public AVLTreeNode<E> search(E element) {
        return search(element, root);
    }

    private AVLTreeNode<E> search(E element, AVLTreeNode<E> currentNode) {
        if (currentNode == null) return null;
        int cmpResult = element.compareTo(currentNode.data);
        if (cmpResult == 0) {
            return currentNode;
        } else if (cmpResult > 0) {
            return search(element, currentNode.rChild);
        } else {
            return search(element, currentNode.lChild);
        }
    }

    public void clear() {
        root = null;
        taller = false;
    }

    public void delete(E element) {
        c++;
        this.root = delete(element, root);

    }

    private AVLTreeNode<E> findLeftMaxNode(AVLTreeNode<E> start) {
        if (start == null) return null;
        AVLTreeNode<E> node = start.lChild;
        if (node == null) return null;

        while (node.rChild != null) {
            node = node.rChild;
        }
        return node;
    }

    private AVLTreeNode<E> findRightMaxNode(AVLTreeNode<E> start) {
        if (start == null) return null;
        AVLTreeNode<E> node = start.rChild;
        if (node == null) return null;

        while (node.lChild != null) {
            node = start.lChild;
        }
        return node;
    }

    private AVLTreeNode<E> delete(E element, AVLTreeNode<E> node) {

        if(node == null) return null;

        int cmpResult = element.compareTo(node.data);
        if (cmpResult == 0) {
            //找到该节点删除
            AVLTreeNodeStatus status = node.childTreeStatus();
            switch (status) {
                case NONE:
                    shorter = true;
                    return null;   //左右子树空
                //左子树不空 右空
                case LEFT:
                    shorter = true;
                    return node.lChild;
                //右子树不空 左空
                case RIGHT:
                    shorter = true;
                    return node.rChild;
                //左右子树均不为空
                case BOTH:
                    AVLTreeNode<E> newNode = findLeftMaxNode(node);
//
//                    newNode.lChild = node.lChild;
//                    newNode.rChild = node.rChild;\\
                    node.lChild = delete(newNode.data, node.lChild);
                    node.data = newNode.data;
                    if (shorter) {
                        switch (node.balance) {
                            case LH:
                                node.balance = EH;
                                shorter = false;
                                break;
                            case EH:
                                shorter = false;
                                node.balance = RH;
                                break;
                            case RH:
                                node = rightBalance(node);
                                break;

                        }


                    }

            }
            //从右子树中删除
        } else if (cmpResult > 0) {
            node.rChild = delete(element, node.rChild);
            if (shorter) {
                switch (node.balance) {
                    case LH:
                        node = leftBalance(node);
                        shorter = node.lChild.balance != EH;
                        break;
                    case RH:
                        node.balance = EH;
                        shorter = true;
                        break;
                    case EH:
                        node.balance = LH;
                        shorter = false;
                        break;
                }
            }
            //从左子树中删除
        } else  {
            node.lChild = delete(element, node.lChild);

            if (shorter) {
                switch (node.balance) {
                    case LH:
                        node.balance = EH;
                        shorter = true;
                        break;
                    case EH:
                        node.balance = RH;
                        shorter = false;
                        break;
                    case RH:
                        node = rightBalance(node);
                        shorter = node.rChild.balance != EH;
                }
            }
//            else{
//                node.balance = RH;
//            }
        }

        return node;
    }


    private AVLTreeNode<E> leftRotate(AVLTreeNode<E> treeNode) {
        AVLTreeNode<E> node = treeNode.rChild;
        treeNode.rChild = node.lChild;
        node.lChild = treeNode;
        return node;
    }

    private AVLTreeNode<E> rightRoate(AVLTreeNode<E> treeNode) {
        AVLTreeNode<E> node = treeNode.lChild;
        treeNode.lChild = node.rChild;
        node.rChild = treeNode;
        return node;
    }

    private AVLTreeNode<E> rightBalance(AVLTreeNode<E> node) {
        switch (node.rChild.balance) {

            case RH: //RR型
                node.balance = 0;
                node.rChild.balance = 0;
                node = leftRotate(node);
                break;

            case LH: // RL 型

                switch (node.rChild.lChild.balance) {
                    case LH:
                        node.balance = EH;
                        node.rChild.balance = RH;
                        break;

                    case EH:
                        node.balance = EH;
                        node.rChild.balance = EH;
                        break;

                    case RH:
                        node.balance = LH;
                        node.rChild.balance = EH;
                        break;

                }

                node.rChild.lChild.balance = EH;
                node.rChild = rightRoate(node.rChild);
                node = leftRotate(node);
                break;


            case EH:
                //删除出现的情况
                System.out.println("执行");
                node.balance = RH;
                node.lChild.balance = LH;
                node = leftRotate(node);
                break;
        }

        return node;
    }

    private AVLTreeNode<E> leftBalance(AVLTreeNode<E> node) {

        switch (node.lChild.balance) {
            case LH: //LL型
                node.balance = EH;
                node.lChild.balance = EH;
                node = rightRoate(node);
                break;
            case RH: // LR 型

                switch (node.lChild.rChild.balance) {
                    case LH:
                        node.balance = RH;
                        node.lChild.balance = EH;
                        break;
                    case EH:
                        node.balance = EH;
                        node.lChild.balance = EH;
                        break;
                    case RH:
                        node.balance = EH;
                        node.lChild.balance = LH;
                }
                node.lChild.rChild.balance = EH;
                node.lChild = leftRotate(node.lChild);
                node = rightRoate(node);
                break;

            case EH:
                System.out.println("执行");
                //删除出现的情况
//                switch (node.lChild.balance){
//                    case EH:
//                        node.balance = EH;
//                        node.lChild.balance = RH;
//                        break;
//                    case LH:
//                        node.balance = RH;
//                        node.lChild.balance = RH;
//                        break;
//                    case RH:
//                        node.
//
//                }
                node.balance = LH;
                node.lChild.balance = RH;
                node = rightRoate(node);
        }

        return node;

    }

    public AVLTreeIterator<E> iterator() {
        return new AVLTreeIterator<>(root);
    }

    public AVLTreeNode<E> getRoot() {
        return root;
    }
}

