package com.mycompany.TDAs;

import com.mycompany.Modelos.Jugada;

/**
 *
 * @author Usuario
 */
public class Tree<E> {
    private TreeNode<E> root;

    public Tree() {
        this.root = null;
    }

    public Tree(Jugada[][] m1) {
        TreeNode matriz = new TreeNode<>(m1);
        this.root = matriz;
    }

    public boolean isEmpty() {
        return this.root == null;
    }

    public Jugada[][] getRoot() {
        return root.getContent();
    }

    public TreeNode<E> getRootNode() {
        return this.root;
    }

    public void setRoot(Jugada[][] matriz) {
        if (this.root == null) {
            this.root = new TreeNode<>(matriz);
        } else {
            this.root.setContent(matriz);
        }
    }

    public boolean isLeaf() {
        return this.root.getChildren().isEmpty();
    }

    public void tablero() {
        Integer[][] tabl= new Integer[3][3];

        for (Integer[] ta : tabl) {
            for (int j = 0; j < ta.length; j++) {
                ta[j] = -1;
            }
        }
       
    }

    public void addChildren(Tree<Jugada[][]> children) {
        if (this.root != null) {
            this.root.addChildrenNode(children);
        }
    }
}
