package net.sunday.basic.algorithm.tree;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.sunday.basic.algorithm.tree.entity.Node;

/**
 * 判断一棵二叉树是否是平衡二叉树
 */
public class IsBalancedTree {

    @AllArgsConstructor
    static class BalancedTreeResult {
        /**
         * 是否为平衡二叉树
         */
        Boolean isBalancedTree;

        /**
         * 树的高度
         */
        Integer height;

    }

    public static <E> boolean isBalancedTree(Node<E> head) {
        if (head == null)
            return true;

        return process(head).isBalancedTree;
    }

    private static <E> BalancedTreeResult process(Node<E> head) {
        if (head == null) {
            return new BalancedTreeResult(true, 0);
        }

        BalancedTreeResult leftResult = process(head.left);
        BalancedTreeResult rightResult = process(head.right);

        // 当前节点的高度
        int height = (leftResult.height > rightResult.height ? leftResult.height : rightResult.height) + 1;

        if (leftResult.isBalancedTree && rightResult.isBalancedTree
                && Math.abs(leftResult.height - rightResult.height) <= 1)
            return new BalancedTreeResult(true, height);

        return new BalancedTreeResult(false, height);
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
