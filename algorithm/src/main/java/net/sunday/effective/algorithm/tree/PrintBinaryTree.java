package net.sunday.effective.algorithm.tree;

import net.sunday.effective.algorithm.tree.entity.Node;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

public class PrintBinaryTree {

    /**
     * 递归方式先序遍历
     */
    public static <E> void preOrderRecur(Node<E> node) {
        if (node == null) {
            return;
        }

        System.out.print(node.value + "\t");
        preOrderRecur(node.left);
        preOrderRecur(node.right);
    }

    /**
     * 非递归方式先序遍历
     */
    public static <E> void preOrderNoRecur(Node<E> node) {
        if (node == null)
            return;

        Stack<Node<E>> stack = new Stack<>();
        stack.push(node);

        while (!stack.isEmpty()) {
            node = stack.pop();
            System.out.print(node.value + "\t");

            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }
        System.out.println();
    }

    /**
     * 递归方式中序遍历
     */
    public static <E> void inOrderRecur(Node<E> node) {
        if (node == null) {
            return;
        }

        inOrderRecur(node.left);
        System.out.print(node.value + "\t");
        inOrderRecur(node.right);
    }

    /**
     * 非递归方式中序遍历
     */
    public static <E> void inOrderNoRecur(Node<E> node) {
        if (node == null) {
            return;
        }

        Stack<Node<E>> stack = new Stack<>();
        while (!stack.isEmpty() || node != null) {
            if (node != null) {
                stack.push(node);
                node = node.left;
            } else {
                node = stack.pop();
                System.out.print(node.value + "\t");
                node = node.right;
            }
        }
        System.out.println();
    }

    /**
     * 递归方式后序遍历
     */
    public static <E> void posOrderRecur(Node<E> node) {
        if (node == null) {
            return;
        }

        posOrderRecur(node.left);
        posOrderRecur(node.right);
        System.out.print(node.value + "\t");
    }

    /**
     * 非递归方式后序遍历
     */
    public static <E> void posOrderNoRecur(Node<E> node) {
        if (node == null) {
            return;
        }

        Stack<Node<E>> helper = new Stack<>();
        Stack<Node<E>> stack = new Stack<>();
        helper.push(node);
        while (!helper.isEmpty()) {
            node = helper.pop();
            stack.push(node);

            if (node.left != null) {
                helper.push(node.left);
            }
            if (node.right != null) {
                helper.push(node.right);
            }
        }

        while (!stack.isEmpty()) {
            System.out.print(stack.pop().value + "\t");
        }

        System.out.println();
    }

    /**
     * 按层次遍历
     */
    public static <E> void printByLevel(Node<E> head) {
        if (head == null) {
            return;
        }

        Queue<Node<E>> queue = new ArrayDeque<>();
        // 从尾部添加元素
        queue.add(head);

        while (!queue.isEmpty()) {
            // 从头部获取元素
            head = queue.poll();
            System.out.print(head.value + "\t");

            if (head.left != null) {
                queue.add(head.left);
            }
            if (head.right != null) {
                queue.add(head.right);
            }
        }

        System.out.println();
    }

    /**
     * for test
     *
     * <pre>
     *         5
     *       /   \
     *      3     8
     *     / \   / \
     *    2  4  7  10
     *   /     /  /  \
     *  1     6  9   11
     * <pre/>
     */
    public static void main(String[] args) {
        Node<Integer> head = new Node<>(5);
        head.left = new Node<>(3);
        head.right = new Node<>(8);
        head.left.left = new Node<>(2);
        head.left.right = new Node<>(4);
        head.left.left.left = new Node<>(1);
        head.right.left = new Node<>(7);
        head.right.left.left = new Node<>(6);
        head.right.right = new Node<>(10);
        head.right.right.left = new Node<>(9);
        head.right.right.right = new Node<>(11);

        // recursive
        System.out.println("==============recursive==============");
        System.out.println("pre-order: ");
        preOrderRecur(head);
        System.out.println();
        System.out.println("in-order: ");
        inOrderRecur(head);
        System.out.println();
        System.out.println("pos-order: ");
        posOrderRecur(head);
        System.out.println();

        // noRecursive
        System.out.println("============noRecursive=============");
        System.out.println("pre-order: ");
        preOrderNoRecur(head);
        System.out.println("in-order: ");
        inOrderNoRecur(head);
        System.out.println("pos-order: ");
        posOrderNoRecur(head);

        //morris遍历
//        System.out.println("============morrisOrder=============");
//        System.out.println("pre-order: ");
//        morrisPreOrder(head);
//        System.out.println("in-order: ");
//        morrisInOrder(head);
//        System.out.println("pos-order: ");
//        morrisPosOrder(head);

        // another way to print by level
        System.out.println("============levelOrder=============");
        printByLevel(head);
    }
}
