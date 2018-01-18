package widget;

import avl_tree.AVLTree;
import avl_tree.AVLTreeNode;
import javax.swing.*;
import java.awt.*;


public class AVLTreeShower extends JFrame {

    public static int num = 0;
    AVLTreeShower another ;
    AVLTree anotherTree;

    public AVLTreeShower(AVLTree tree , int tag) throws HeadlessException {

        AVLTree anotherTree  = new AVLTree();
        this.anotherTree = anotherTree;
        if(num == 0 ) {
            num ++;
            another = new AVLTreeShower(anotherTree, 3);
            another.setVisible(true);
        }
        this.setTitle("Tree");  //设置窗体的标题
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        TreeView treeView = new TreeView(tree, tag);
        add(treeView);

        setSize(800, 800);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        Toolkit tk = this.getToolkit();
        Dimension dm = tk.getScreenSize();
        if(tag == 1){
        this.setLocation(800,100);
        this.setTitle("右Tree");
        }
        if(tag == 2 ) {
            this.setTitle("左Tree");
            this.setLocation(0, 100);
        }
        if(tag == 3){
            this.setTitle("被合并的树");
        }
    }

    class TreeView extends JPanel {
        private Integer key;
        private int radius = 20;
        private int vGap = 50;
        private AVLTree root;

        public TreeView(AVLTree root,int tag) {
            this.root = root;

            setSize(800, 800);
            setLayout(new FlowLayout(FlowLayout.TRAILING));
            if(tag != 1 && tag != 2) {

                Button combine = new Button("合并");
                Button add = new Button();
                Button delete = new Button();
                Button split2 = new Button();
                Button search = new Button();
                Button clear = new Button();
                combine.setSize(200,80);
                clear.setLabel("清空");
                split2.setLabel("分裂");
                delete.setLabel("删除");
                delete.setSize(200, 80);
                search.setSize(200, 80);
                clear.setSize(200,80);
                search.setLabel("查找关键字");
                add.setLabel("插入");
                add.setSize(200, 80);
                add(add);
                add(delete);
                add(split2);
                add(search);
                add(clear);
                if(tag != 3)
                add(combine);

                combine.addActionListener(lis->{
                    root.combine(anotherTree);
                    AVLTreeShower.this.repaint();
                });

                clear.addActionListener(l->{
                    root.clear();
                    repaint();
                });

                search.addActionListener(e ->{
                    String data = JOptionPane.showInputDialog("Please input a value to search");
                    this.key = Integer.valueOf(data);
                    repaint();
                });


            split2.addActionListener(e -> {
                String data = JOptionPane.showInputDialog("Please input a value");
                new AVLTreeShower(this.root.split(Integer.valueOf(data))[1],1).setVisible(true);
                new AVLTreeShower(this.root.split(Integer.valueOf(data))[0],2).setVisible(true);

            });

            add.addActionListener(e -> {
                String data = JOptionPane.showInputDialog("Please input a value");
                root.insert(Integer.valueOf(data));
                repaint();
            });

            delete.addActionListener(e -> {
                String data = JOptionPane.showInputDialog("Please input a value");
                root.delete(Integer.valueOf(data));
                repaint();
            });

            }

        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (root.getRoot() != null) {
                diaplay(g, root.getRoot(), getWidth() / 2, 30, getWidth() / 4);
            }
        }

        private void diaplay(Graphics g, AVLTreeNode root, int x, int y, int hGap) {
            if(key != null && root.data.compareTo(key) == 0 )g.setColor(Color.RED);else g.setColor(Color.BLACK);
            g.drawOval(x - radius, y - radius, 2 * radius, 2 * radius);
            g.drawString(root.data.toString(), x - 6, y + 4);
            g.drawString(String.valueOf(root.balance), x + 4, y - radius);
            if (root.lChild != null) {
                g.setColor(Color.BLACK);
                connectLeftChild(g, x - hGap, y + vGap, x, y);
                diaplay(g, root.lChild, x - hGap, y + vGap, hGap / 2);
            }
            if (root.rChild != null) {
                g.setColor(Color.BLACK);
                connectRightChild(g, x + hGap, y + vGap, x, y);
                diaplay(g, root.rChild, x + hGap, y + vGap, hGap / 2);
            }
        }

        private void connectRightChild(Graphics g, int x1, int y1, int x2, int y2) {
            // TODO Auto-generated method stub
            double d = Math.sqrt(vGap * vGap + (x2 - x1) * (x2 - x1));
            int x11 = (int) (x1 - radius * (x1 - x2) / d);
            int y11 = (int) (y1 - radius * vGap / d);
            int x21 = (int) (x2 + radius * (x1 - x2) / d);
            int y21 = (int) (y2 + radius * vGap / d);
            g.drawLine(x11, y11, x21, y21);
        }

        private void connectLeftChild(Graphics g, int x1, int y1, int x2, int y2) {
            // TODO Auto-generated method stub
            double d = Math.sqrt(vGap * vGap + (x2 - x1) * (x2 - x1));
            int x11 = (int) (x1 + radius * (x2 - x1) / d);
            int y11 = (int) (y1 - radius * vGap / d);
            int x21 = (int) (x2 - radius * (x2 - x1) / d);
            int y21 = (int) (y2 + radius * vGap / d);
            g.drawLine(x11, y11, x21, y21);
        }
    }


}

