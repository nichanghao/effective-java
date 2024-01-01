package net.sunday.basic.algorithm.tree;

import net.sunday.basic.algorithm.tree.entity.Node;

/**
 * 在二叉树中找到一个节点的后继节点
 * 【题目】 现在有一种新的二叉树节点类型如下：
 * public class Node {
 * public int value; public Node left;public Node right; public Node parent;
 * public Node(int data) { this.value = data; }}
 * 该结构比普通二叉树节点结构多了一个指向父节点的parent指针。假设有一 棵Node类型的节点组成的二叉树，
 * 树中每个节点的parent指针都正确地指向自己的父节点，头节点的parent指向null。只给一个在二叉树中的某个节点
 * node，请实现返回node的后继节点的函数。在二叉树的中序遍历的序列中， node的下一个节点叫作node的后继节点。
 */
public class FindAfterNode {

    /**
     * 1. node有右子树时，后继结点（中序遍历）为右子树的最左节点
     * 2. node无右子树时，向上查找，找到第一个node节点为父节点的左子节点停止
     */
    public static <E> Node<E> findAfterNode(Node<E> node) {

        if (node.right != null) {
            node = node.right;
            while (node.left != null) {
                node = node.left;
            }
            return node;
        } else {
            while (node.parent != null && node.parent.right == node) {
                node = node.parent;
            }

            return node.parent;
        }

    }

    //for test
    @SuppressWarnings("all")
    public static void main(String[] args) {
        Node<Integer> node1 = new Node<>(1);
        Node<Integer> node2 = new Node<>(2);
        Node<Integer> node3 = new Node<>(3);
        Node<Integer> node4 = new Node<>(4);
        Node<Integer> node5 = new Node<>(5);
        Node<Integer> node6 = new Node<>(6);
        Node<Integer> node7 = new Node<>(7);

        node1.left = node2;
        node1.right = node3;
        node2.left = node4;
        node2.right = node5;
        node2.parent = node1;
        node4.parent = node2;
        node5.parent = node2;
        node3.left = node6;
        node3.right = node7;
        node3.parent = node1;
        node6.parent = node3;
        node7.parent = node3;


        System.out.println("in-order:");
        PrintBinaryTree.inOrderNoRecur(node1);
        System.out.println(node2.value + "的后继节点：" + findAfterNode(node2).value);
        System.out.println(node5.value + "的后继节点：" + findAfterNode(node5).value);
        System.out.println(node1.value + "的后继节点：" + findAfterNode(node1).value);


    }

}
