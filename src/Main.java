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

            while(i < 20) {
                i++;
                tree.insert(i);
            }




        JFrame shower = new TreeShow(tree);
        shower.setExtendedState( Frame.MAXIMIZED_BOTH );

    }


}
