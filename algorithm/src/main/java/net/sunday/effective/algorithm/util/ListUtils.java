package net.sunday.effective.algorithm.util;


import net.sunday.effective.algorithm.list.entity.Node;

/**
 * 链表工具类
 */
public class ListUtils {

    //打印单向链表
    public static <E> void printList(Node<E> node) {
        while (node != null) {
            System.out.print(node.data + "-->");
            node = node.next;
        }
        System.out.println();
    }

    //打印双向链表
    public static <E> void printDoublyList(Node<E> node) {
        Node<E> end = null;
        while (node != null) {
            System.out.print(node.data+"-->");
            end = node;
            node = node.next;
        }
        System.out.println();

        while (end != null) {
            System.out.print(end.data+"-->");
            end = end.pre;
        }
        System.out.println();
    }

}
