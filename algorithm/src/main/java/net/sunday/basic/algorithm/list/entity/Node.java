package net.sunday.basic.algorithm.list.entity;

/**
 * 链表的节点
 */
public class Node<E> {

    public Node<E> next;
    public Node<E> prev;
    /**
     * 指向链表中的随机一个位置
     */
    public Node<E> rand;
    public E data;

    public Node(E data) {
        this.data = data;
    }

}
