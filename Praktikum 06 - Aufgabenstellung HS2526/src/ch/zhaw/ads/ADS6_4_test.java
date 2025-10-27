/*package ch.zhaw.ads;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @(#)TreeTest.java
 *
 *
 * @author K Rege
 * @version 1.00 2018/3/17
 * @version 1.01 2021/8/1
 *
public class ADS6_4_test {
    MyTree<String> tree;

    static class MyTree<T extends Comparable<T>> extends AVLSearchTree<T> {
        TreeNode<T> getRoot () {
            return root;
        }
    }

    @BeforeEach
    public void setUp() {
        tree = new MyTree<>();
        tree.add("B");
        tree.add("A");
        tree.add("C");
        tree.add("D");
    }

    @Test
    public void testBalanced() {
        assertTrue(tree.balanced(), "should be balanced");
        TreeNode<String> n = tree.getRoot();
        n.right.right.right = new TreeNode<>("Z");
        assertFalse(tree.balanced(), "should not be balanced");

    }
}
*/