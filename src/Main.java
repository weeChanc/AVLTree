import avl_tree.AVLTree;

import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        AVLTree<Integer> tree = new AVLTree<>();
        
        int[] ints = create();
        
        for(int i : ints){
            tree.insert(i);
        }
        
        for(int e : tree.iterator()){
            System.out.println(e);
        }
        

    }

    static int[] create(){
        int[] ints = new int[999];
        for(int i = 0; i < 999 ; i++){
            ints[i] = (int) (Math.random() * 9999);
        }
       return ints;
    }

}
