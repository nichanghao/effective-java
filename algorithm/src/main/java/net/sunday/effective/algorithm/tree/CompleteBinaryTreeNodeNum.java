package net.sunday.effective.algorithm.tree;

import net.sunday.effective.algorithm.tree.entity.Node;

import static net.sunday.effective.algorithm.util.AssertUtils.assertTrue;


/**
 * 已知一棵完全二叉树，求其节点的个数,要求：时间复杂度低于O(N)，N为这棵树的节点个数
 */
public class CompleteBinaryTreeNodeNum {


    public static <E> int getNodeNum(Node<E> node) {
        if (node == null) {
            return 0;
        }

        return getNodeNum(node, 1, getHeight(node));
    }

    private static <E> int getNodeNum(Node<E> node, int level, int height) {
        if (level == height) {
            return 1;
        }

        if (getHeight(node.right) + level == height) {
            return (1 << height - level) + getNodeNum(node.right, level + 1, height);
        } else {
            return (1 << height - level - 1) + getNodeNum(node.left, level + 1, height);
        }
    }

    private static <E> int getHeight(Node<E> node) {
        int height = 0;
        while (node != null) {
            node = node.left;
            height++;
        }

        return height;
    }

    /**
     * for test
     */
    @SuppressWarnings("all")
    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.left.right = new Node(5);
        head.right.left = new Node(6);
        assertTrue(getNodeNum(head) == 6);
    }
}
