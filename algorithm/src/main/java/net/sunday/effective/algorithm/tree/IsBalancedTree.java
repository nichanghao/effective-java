package net.sunday.effective.algorithm.tree;

import net.sunday.effective.algorithm.tree.entity.Node;

/**
 * 判断一棵二叉树是否是平衡二叉树
 */
public class IsBalancedTree {

    public static <E> boolean isBalancedTree(Node<E> head) {
        if (head == null) {
            return false;
        }

        boolean[] res = new boolean[]{true};
        scroll(head, res);

        return res[0];
    }

    public static <E> int scroll(Node<E> head, boolean[] res) {
        if (head == null) {
            return 0;
        }

        int leftHeight = scroll(head.left, res);
        int rightHeight = scroll(head.right, res);
        if (Math.abs(leftHeight - rightHeight) > 1) {
            res[0] = false;
        }

        return Math.max(leftHeight, rightHeight) + 1;
    }

    /**
     * for test
     */
    @SuppressWarnings("Duplicates")
    public static void main(String[] args) {
        Node<Integer> head = new Node<>(1);
        head.left = new Node<>(2);
        head.right = new Node<>(3);
        head.left.left = new Node<>(4);
        head.left.right = new Node<>(5);
        head.right.left = new Node<>(6);
        head.right.right = new Node<>(7);

        System.out.println(isBalancedTree(head));
    }

}
