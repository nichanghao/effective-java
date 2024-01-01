package net.sunday.basic.algorithm.tree;

import net.sunday.basic.algorithm.tree.entity.Node;

import java.util.Stack;

public class PrintBinaryTree {

    /**
     * 递归方式先序遍历
     */
    public static <E> void preOrderRecur(Node<E> node) {
        if (node == null)
            return;

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
        if (node == null)
            return;

        inOrderRecur(node.left);
        System.out.print(node.value + "\t");
        inOrderRecur(node.right);
    }

    /**
     * 非递归方式中序遍历
     */
    public static <E> void inOrderNoRecur(Node<E> node) {
        if (node == null)
            return;

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

        Stack<Node<E>> stack = new Stack<>();
        Stack<Node<E>> help = new Stack<>();
        stack.push(node);
        while (!stack.isEmpty()) {
            node = stack.pop();
            help.push(node);

            if (node.left != null) {
                stack.push(node.left);
            }
            if (node.right != null) {
                stack.push(node.right);
            }
        }

        while (!help.isEmpty()) {
            System.out.print(help.pop().value + "\t");
        }
        System.out.println();

    }

    /**
     * for test
     */
    @SuppressWarnings("Duplicates")
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

    }

}
