package net.sunday.effective.algorithm.tree.entity;

/**
 * 二叉树的节点
 */
public class Node<E> {

    public Node<E> left;
    public Node<E> right;
    public Node<E> parent;
    public E value;

    public Node(E value) {
        this.value = value;
    }
}
