import avl_tree.AVLTree;
import avl_tree.AVLTreeNode;
import queue.LinkQueue;
import stack.LinkStack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;



class TreeShow extends JFrame {
    int i =99 ;

    public TreeShow(AVLTree tree) throws HeadlessException {


//

        this.setTitle("Tree");  //设置窗体的标题
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        TreeView treeView = new TreeView(tree);

        add(treeView);

        setVisible(true);
//


    }

    class TreeView extends JPanel{
        private int radius=20;
        private int vGap=50;
        private AVLTree root;
        public TreeView( AVLTree root) {
            this.root = root;

            setSize(800,800);
            setLayout(new FlowLayout(FlowLayout.TRAILING));
            Button add = new Button();
            Button delete = new Button();

            delete.setLabel("delete");
            delete.setSize(200,80);
            add.setLabel("add");
            add.setSize(200,80);
            add(add);
            add(delete);

            add.addActionListener(e -> {
                String data = JOptionPane.showInputDialog("Please input a value");
                root.insert(Integer.valueOf(data));
                repaint();
            });

            delete.addActionListener(e->{
                String data = JOptionPane.showInputDialog("Please input a value");
                root.delete(Integer.valueOf(data));
                repaint();
            });

        }

        public void paintComponent(Graphics g){
            super.paintComponent(g);
            if (root.getRoot()!=null) {
                diaplay(g,root.getRoot(),getWidth()/2,30,getWidth()/4);
            }
        }
        private void diaplay(Graphics g, AVLTreeNode root, int x, int y, int hGap) {
            g.drawOval(x-radius, y-radius, 2*radius,  2*radius);
            g.drawString(root.data.toString(), x-6, y+4);
            g.drawString(String.valueOf(root.balance), x+4, y-radius);
            if(root.lChild!=null){
                connectLeftChild(g,x-hGap,y+vGap,x,y);
                diaplay(g, root.lChild, x-hGap, y+vGap, hGap/2);
            }
            if (root.rChild!=null) {
                connectRightChild(g,x+hGap,y+vGap,x,y);
                diaplay(g, root.rChild, x+hGap, y+vGap, hGap/2);
            }
        }
        private void connectRightChild(Graphics g, int x1, int y1, int x2, int y2) {
            // TODO Auto-generated method stub
            double d=Math.sqrt(vGap*vGap+(x2-x1)*(x2-x1));
            int x11=(int)(x1-radius*(x1-x2)/d);
            int y11=(int)(y1-radius*vGap/d);
            int x21=(int)(x2+radius*(x1-x2)/d);
            int y21=(int)(y2+radius*vGap/d);
            g.drawLine(x11, y11, x21, y21);
        }
        private void connectLeftChild(Graphics g, int x1, int y1, int x2, int y2) {
            // TODO Auto-generated method stub
            double d=Math.sqrt(vGap*vGap+(x2-x1)*(x2-x1));
            int x11=(int)(x1+radius*(x2-x1)/d);
            int y11=(int)(y1-radius*vGap/d);
            int x21=(int)(x2-radius*(x2-x1)/d);
            int y21=(int)(y2+radius*vGap/d);
            g.drawLine(x11, y11, x21, y21);
        }
    }


}

