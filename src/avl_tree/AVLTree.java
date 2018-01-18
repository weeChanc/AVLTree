package avl_tree;

/**
 * @param <E>
 * 平衡二叉树
 * E是储存元素的泛型
 */
public class AVLTree<E extends Comparable<E>> {
    public AVLTree() {
    }

    //定义左高 右高 等高
    private static final int LH = 1;
    private static final int RH = -1;
    private static final int EH = 0;

    private AVLTreeNode<E> root;

    private boolean taller = false;
    private boolean shorter = false;

    //找不到节点的返回值
    private AVLTreeNode<E> CANFIND = new AVLTreeNode<>();

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

    //清除树
    public void clear() {
        root = null;
        taller = false;
        shorter=false;
    }

    //删除节点
    public void delete(E element) {
        this.root = delete(element, root);

    }

    //分裂树
    public AVLTree<E>[] split(E element){
        AVLTree<E>[] trees = new AVLTree[2];
        AVLTree<E> treeleft = new AVLTree<>();
        AVLTree<E> treeright = new AVLTree<>();
        AVLTreeIterator<E> iterator = this.iterator();
        while(iterator.hasNext()){
            E e = iterator.next();
            if(e.compareTo(element) <= 0){
                treeleft.insert(e);
            }else{
                treeright.insert(e);
            }
        }
        trees[0] = treeleft;
        trees[1] = treeright;
        return trees;
    }

    //从左子树的最大节点中寻找替代节点
    private AVLTreeNode<E> findLeftMaxNode(AVLTreeNode<E> start) {
        if (start == null) return null;
        AVLTreeNode<E> node = start.lChild;
        if (node == null) return null;

        while (node.rChild != null) {
            node = node.rChild;
        }
        return node;
    }

    private AVLTreeNode<E> delete(E element, AVLTreeNode<E> node) {

        if(node == null) return CANFIND;

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
//                    node.lChild = delete(newNode.data, node.lChild);

                    AVLTreeNode<E> deleted = delete(newNode.data,node.lChild);
                    if(deleted != CANFIND) {
                        node.lChild = deleted;
                    }else{
                        shorter = false;
                        return node;
                    }

                    node.data = newNode.data;
                    if (shorter) {
                        switch (node.balance) {
                            case LH:
                                node.balance = EH;
                                shorter = true;
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
            AVLTreeNode<E> deleted = delete(element,node.rChild);
            if(deleted != CANFIND) {
                node.rChild = deleted;
            }else{
                shorter = false;
                return node;
            }

            if (shorter) {
                switch (node.balance) {
                    case LH:
                        shorter = node.lChild.balance != EH;
                        node = leftBalance(node);
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

            AVLTreeNode<E> deleted = delete(element,node.lChild);
            if(deleted != CANFIND) {
                node.lChild = deleted;
            }else{
                shorter = false;
                return node;
            }

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
                        shorter = node.rChild.balance != EH;
                        node = rightBalance(node);
                        if(node.rChild == null){
                            System.out.println("null of rChild");
                        }

                }
            }
//            else{
//                node.balance = RH;
//            }
        }

        return node;
    }

    public void combine(AVLTree<E> another){
        for(E e : another.iterator()){
            this.insert(e);
        }
    }

    //左旋操作,返回选后根节点
    private AVLTreeNode<E> leftRotate(AVLTreeNode<E> treeNode) {
        AVLTreeNode<E> node = treeNode.rChild;
        treeNode.rChild = node.lChild;
        node.lChild = treeNode;
        return node;
    }

    //右旋操作,返回选后根节点
    private AVLTreeNode<E> rightRoate(AVLTreeNode<E> treeNode) {
        AVLTreeNode<E> node = treeNode.lChild;
        treeNode.lChild = node.rChild;
        node.rChild = treeNode;
        return node;
    }

    //右平衡
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


                //删除左子树节点的时候,右边出现不平衡
            case EH:
                //删除出现的情况
                System.out.println("执行");
                node.balance = RH;
                node.rChild.balance = LH;
                node = leftRotate(node);
                break;
        }

        return node;
    }

    //左平衡
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

    //获取该中序遍历树的一个迭代器
    public AVLTreeIterator<E> iterator() {
        return new AVLTreeIterator<>(root);
    }

    //获取树的根节点
    public AVLTreeNode<E> getRoot() {
        return root;
    }



}

