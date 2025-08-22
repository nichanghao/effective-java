package net.sunday.effective.algorithm.tree;

import net.sunday.effective.algorithm.tree.entity.Node;

import java.util.ArrayDeque;
import java.util.Deque;

public class IsBSTAndCBT {

    /**
     * 判断是否为二叉搜索树
     */
    public static boolean isBinarySearchTree(Node<Integer> head) {
        if (head == null) {
            return true;
        }

        boolean left = isBinarySearchTree(head.left);
        boolean right = isBinarySearchTree(head.right);
        if (!left || !right) {
            return false;
        }

        if (head.left != null && head.left.value > head.value) {
            return false;
        }
        if (head.right != null && head.right.value < head.value) {
            return false;
        }

        return true;
    }

    /**
     * 判断是否为完全二叉树(宽度优先遍历)
     */
    public static <E> boolean isCompleteBinaryTree(Node<E> head) {
        if (head == null) {
            return true;
        }

        Deque<Node<E>> queue = new ArrayDeque<>();
        queue.add(head);

        boolean isNullable = false;
        while (!queue.isEmpty()) {
            head = queue.poll();
            if (head.left == null && head.right != null) {
                return false;
            }
            if (isNullable && head.left != null) {
                return false;
            }


            if (head.left != null) {
                queue.add(head.left);
            } else {
                isNullable = true;
            }

            if (head.right != null) {
                queue.add(head.right);
            } else {
                isNullable = true;
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
        System.out.println("is BST: " + isBinarySearchTree(head));
        System.out.println("is CBT: " + isCompleteBinaryTree(head));
    }

}
