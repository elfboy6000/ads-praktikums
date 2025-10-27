package ch.zhaw.ads;

public class TreeTraversal<T extends Comparable<T>> implements Traversal<T> {
    private final TreeNode<T> root;

    public TreeTraversal(TreeNode<T> root) {
        this.root = root;
    }

    public void inorder(Visitor<T> vis) {
        if(root != null){
            TreeTraversal<T> leftSubtree = new TreeTraversal<>(root.left);
            leftSubtree.inorder(vis);
            vis.visit(root.getValue());
            TreeTraversal<T> rightSubtree = new TreeTraversal<>(root.right);
            rightSubtree.inorder(vis);
        }

    }

    public void preorder(Visitor<T> vis) {
        if(root != null){
            vis.visit(root.getValue());
            TreeTraversal<T> leftSubtree = new TreeTraversal<>(root.left);
            leftSubtree.preorder(vis);
            TreeTraversal<T> rightSubtree = new TreeTraversal<>(root.right);
            rightSubtree.preorder(vis);
        }
    }

    public void postorder(Visitor<T> vis) {
        if(root != null){
            TreeTraversal<T> leftSubtree = new TreeTraversal<>(root.left);
            leftSubtree.postorder(vis);
            TreeTraversal<T> rightSubtree = new TreeTraversal<>(root.right);
            rightSubtree.postorder(vis);
            vis.visit(root.getValue());
        }
    }

    @Override
    public void levelorder(Visitor<T> vistor) {
        if (root == null) {
            return;
        }

        java.util.Queue<TreeNode<T>> queue = new java.util.LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            TreeNode<T> currentNode = queue.poll();
            vistor.visit(currentNode.getValue());

            if (currentNode.left != null) {
                queue.add(currentNode.left);
            }
            if (currentNode.right != null) {
                queue.add(currentNode.right);
            }
        }

    }

    @Override
    public void interval(T min, T max, Visitor<T> vistor) {
        if (root == null) {
            return;
        }

        if (root.getValue().compareTo(min) >= 0 && root.getValue().compareTo(max) <= 0) {
            TreeTraversal<T> leftSubtree = new TreeTraversal<>(root.left);
            leftSubtree.interval(min, max, vistor);
            vistor.visit(root.getValue());
            TreeTraversal<T> rightSubtree = new TreeTraversal<>(root.right);
            rightSubtree.interval(min, max, vistor);
        } else if (root.getValue().compareTo(min) < 0) {
            TreeTraversal<T> rightSubtree = new TreeTraversal<>(root.right);
            rightSubtree.interval(min, max, vistor);
        } else {
            TreeTraversal<T> leftSubtree = new TreeTraversal<>(root.left);
            leftSubtree.interval(min, max, vistor);
        }
    }
}