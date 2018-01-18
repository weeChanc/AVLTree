import avl_tree.AVLTree;
import widget.AVLTreeShower;


import javax.swing.*;
import java.awt.*;
import java.io.IOException;


public class Main {

    public static void main(String[] args) throws IOException {

        AVLTree<Integer> tree = new AVLTree<>();

//        for(int i = 1 ; i < 20 ; i++){
//            tree.insert((int) (System.currentTimeMillis()%(20*i)));
//        }
//        tree.delete(5);
//        tree.clear();

        JFrame shower = new AVLTreeShower(tree,0);
        shower.setExtendedState( Frame.MAXIMIZED_BOTH );

    }


}
