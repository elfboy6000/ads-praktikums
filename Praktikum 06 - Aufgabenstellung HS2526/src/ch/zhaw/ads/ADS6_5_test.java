/*package ch.zhaw.ads;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @(#)TreeTest.java
 *
 *
 * @author K Rege
 * @version 1.00 2018/3/17
 * @version 1.01 2021/8/1
 *
public class ADS6_5_test {
    Tree<String> tree;

    @Test
    public void testMixed() {
        tree = new AVLSearchTree<>();
        List<String> list = new LinkedList<>();
        for (int i = 0; i < 1000; i++) {
            char c = (char) ('A' + (Math.random() * 26));
            int op = (int) (Math.random() * 2);
            switch (op) {
                case 0:
                    list.add(Character.toString(c));
                    tree.add(Character.toString(c));
                    break;
                case 1:
                    list.remove(Character.toString(c));
                    tree.remove(Character.toString(c));
                    break;
            }
        }
        assertEquals(list.size(), tree.size());
        Collections.sort(list);
        String expected = String.join("", list);
        Visitor<String> v = new MyVisitor<>();
        tree.traversal().inorder(v);
        assertEquals(expected, v.toString(), "mixed");

        assertTrue(tree.balanced(), "balanced");
    }
}
*/