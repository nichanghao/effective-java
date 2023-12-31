package net.sunday.basic.algorithm.list;

import net.sunday.basic.algorithm.list.entity.Node;
import net.sunday.basic.algorithm.list.util.ListUtils;

/**
 * 反转链表
 */
public class ReverseList {

    /**
     * 反转单向链表
     */
    public static <E> Node<E> reverseList(Node<E> head) {
        if (head == null || head.next == null) {
            return head;
        }

        Node<E> prev = null, next = null;
        while (head != null) {
            next = head.next;
            head.next = prev;
            prev = head;
            head = next;
        }

        return prev;

    }

    /**
     * 反转双向链表
     */
    public static <E> Node<E> reverseDoublyList(Node<E> head) {
        if (head == null || head.next == null) {
            return head;
        }

        Node<E> prev = null, next = null;
        while (head != null) {
            next = head.next;
            head.prev = next;
            head.next = prev;
            prev = head;
            head = next;
        }

        return prev;

    }

    /**
     * for test
     */
    public static void main(String[] args) {
        Node<Integer> head1 = new Node<>(1);
        Node<Integer> head2 = new Node<>(2);
        Node<Integer> head3 = new Node<>(3);
        head1.next = head2;
        head2.next = head3;
        ListUtils.printList(head1);

        Node<Integer> node1 = reverseList(head1);
        ListUtils.printList(node1);


        Node<Integer> head4 = new Node<>(4);
        Node<Integer> head5 = new Node<>(5);
        Node<Integer> head6 = new Node<>(6);
        head4.next = head5;
        head5.next = head6;
        head5.prev = head4;
        head6.prev = head5;
        Node<Integer> node2 = reverseDoublyList(head4);
        ListUtils.printDoublyList(node2);

    }
}
