package ch.zhaw.ads;

import java.util.LinkedList;
import java.util.Queue;

public class AVLTreeTraversal<T extends Comparable<T>> implements Traversal<T> {
    private final TreeNode<T> root;

    public AVLTreeTraversal(TreeNode<T> root) {
        this.root = root;
    }

    private void inorder(TreeNode<T> node, Visitor<T> vis) {
        if (node != null) {
            inorder(node.left, vis);
            for (T v : node.values) vis.visit(v);
            inorder(node.right, vis);
        }
    }

    public void inorder(Visitor<T> vis) {
        inorder(root, vis);
    }

    private void preorder(TreeNode<T> node, Visitor<T> vis) {
        if (node != null) {
            for (T v : node.values) vis.visit(v);
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
            for (T v : node.values) vis.visit(v);
        }
    }

    public void postorder(Visitor<T> vis) {
        postorder(root, vis);
    }

    void levelorder(TreeNode<T> node, Visitor<T> visitor) {
        Queue<TreeNode<T>> q = new LinkedList<>();
        if (node != null) {
            q.offer(node);
        }
        while (!q.isEmpty()) {
            node = q.poll();
            for (T v : node.values) visitor.visit(v);
            if (node.left != null) {
                q.offer(node.left);
            }
            if (node.right != null) {
                q.offer(node.right);
            }
        }
    }

    public void levelorder(Visitor<T> vis) {
        levelorder(root, vis);
    }

    private void interval(T min, T max, Visitor<T> visitor, TreeNode<T> node) {
        if (node != null) {
            if (0 > node.getValue().compareTo(min)) {
                interval(min, max, visitor, node.right);
            } else if (0 < node.getValue().compareTo(max)) {
                interval(min, max, visitor, node.left);
            } else {
                for (T v : node.values) visitor.visit(v);
                interval(min, max, visitor, node.left);
                interval(min, max, visitor, node.right);
            }
        }
    }

    @Override
    public void interval(T min, T max, Visitor<T> v) {
        interval(min, max, v, this.root);
    }
}
