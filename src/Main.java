import avl_tree.AVLTree;
import avl_tree.AVLTreeNode;
import queue.LinkQueue;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;


public class Main {

    static int i = 0 ;
    public static void main(String[] args) throws IOException {

        AVLTree<Integer> tree = new AVLTree<>();

        int[] insert = new int[]{341, 354, 706, 923, 101, 530, 119, 374, 973, 517, 862, 220, 697, 211, 446, 295, 474, 71, 165, 612};
        int[] del = new int[]{211, 530};
        for ( int t : insert){
            tree.insert(t);
        }

        //减去119  220节点不会变

        for ( int d : del){
            tree.delete(d);
        }





        JFrame shower = new TreeShow(tree);
        shower.setExtendedState( Frame.MAXIMIZED_BOTH );

    }


}
