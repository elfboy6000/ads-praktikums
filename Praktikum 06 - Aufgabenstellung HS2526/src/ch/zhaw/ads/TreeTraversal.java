package ch.zhaw.ads;

import java.util.LinkedList;
import java.util.Queue;

public class TreeTraversal<T extends Comparable<T>> implements Traversal<T> {
    private final TreeNode<T> root;

    public TreeTraversal(TreeNode<T> root) {
        this.root = root;
    }

    private void inorder(TreeNode<T> node, Visitor<T> vis) {
        if (node != null) {
            inorder(node.left, vis);
            vis.visit(node.getValue());
            inorder(node.right, vis);
        }
    }

    public void inorder(Visitor<T> vis) {
        inorder(root, vis);
    }

    private void preorder(TreeNode<T> node, Visitor<T> vis) {
        if (node != null) {
            vis.visit(node.getValue());
            preorder(node.left, vis);
            preorder(node.right, vis);
        }
    }

    public void preorder(Visitor<T> vis) {
        preorder(root, vis);
    }

    private void postorder(TreeNode<T> node, Visitor<T> vis) {
        if (node != null) {
            postorder(node.left, vis);
            postorder(node.right, vis);
            vis.visit(node.getValue());
        }
    }

    public void postorder(Visitor<T> vis) {
        postorder(root, vis);
    }

    void levelorder(TreeNode<T> node, Visitor<T> visitor) {
        Queue<TreeNode<T>> q = new LinkedList<TreeNode<T>>();
        if (node != null) {
            q.add(node);
        }
        while (!q.isEmpty()) {
            node = q.remove();
            visitor.visit(node.getValue());
            if (node.left != null) {
                q.add(node.left);
            }
            if (node.right != null) {
                q.add(node.right);
            }
        }
    }

    public void levelorder(Visitor<T> vis) {
        levelorder(root, vis);
    }

    private void interval(T min, T max, Visitor<T> v, TreeNode<T> node) {
        if (node != null) {
            if (0 > node.getValue().compareTo(min)) {
                interval(min, max, v, node.right);
            } else if (0 < node.getValue().compareTo(max)) {
                interval(min, max, v, node.left);
            } else {
                v.visit(node.getValue());
                interval(min, max, v, node.left);
                interval(min, max, v, node.right);
            }
        }
    }

    @Override
    public void interval(T min, T max, Visitor<T> v) {
        interval(min, max, v, this.root);
    }
}
