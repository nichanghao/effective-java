package net.sunday.basic.algorithm.tree;

import net.sunday.basic.algorithm.tree.entity.Node;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 1. 判断一棵树是否是搜索二叉树
 * 2. 判断一棵树是否是完全二叉树
 */
public class IsBSTAndCBT {

    /**
     * 判断是否为二叉搜索树
     */
    public static boolean isBinarySearchTree(Node<Integer> node) {
        if (node == null)
            return true;

        boolean leftIsBST = isBinarySearchTree(node.left);
        boolean rightIsBST = isBinarySearchTree(node.right);

        if (!leftIsBST || !rightIsBST) {
            return false;
        }

        if (node.left != null && node.left.value > node.value) {
            return false;
        }

        if (node.right != null && node.value > node.right.value) {
            return false;
        }

        return true;
    }

    /**
     * 判断是否为完全二叉树
     * 宽度优先遍历
     */
    public static boolean isCompleteBinaryTree(Node<Integer> node) {
        if (node == null)
            return true;

        Queue<Node<Integer>> queue = new ArrayDeque<>();
        queue.add(node);

        boolean isLeaf = false;
        while (!queue.isEmpty()) {
            node = queue.poll();

            if (node.left == null && node.right != null)
                return false;

            if (isLeaf && node.left != null) {
                return false;
            }

            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            } else {
                isLeaf = true;
            }
        }

        return true;

    }

    /**
     * for test
     */
    public static void main(String[] args) {
        Node<Integer> head = new Node<>(4);
        head.left = new Node<>(2);
        head.right = new Node<>(6);
        head.left.left = new Node<>(1);
        head.left.right = new Node<>(3);
        head.right.left = new Node<>(5);

        PrintBinaryTree.inOrderNoRecur(head);
        System.out.println(isBinarySearchTree(head));
        System.out.println(isCompleteBinaryTree(head));
    }

}
