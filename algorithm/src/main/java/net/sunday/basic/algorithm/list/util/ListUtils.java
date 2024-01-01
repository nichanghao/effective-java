package net.sunday.basic.algorithm.list.util;


import net.sunday.basic.algorithm.list.entity.Node;

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
        Node end = null;
        while (node != null) {
            System.out.print(node.data + "-->");
            end = node;
            node = node.next;
        }
        System.out.println();

        while (end != null) {
            System.out.print(end.data + "-->");
            end = end.prev;
        }
        System.out.println();
    }

    /**
     * 打印随机链表
     */
    public static <E> void printRandomList(Node<E> head) {
        Node<E> cur = head;
        System.out.println("order list:");
        while (cur != null) {
            System.out.print(cur.data + "\t");
            cur = cur.next;
        }
        System.out.println();
        System.out.println("=================");
        System.out.println("random list:");
        cur = head;
        while (cur != null) {
            System.out.print((cur.rand != null ? cur.rand.data : "null") + "\t");
            cur = cur.next;
        }
        System.out.println();
    }
}
