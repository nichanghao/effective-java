package net.sunday.effective.algorithm.list;

import net.sunday.effective.algorithm.list.entity.Node;
import net.sunday.effective.algorithm.util.ListUtils;

/**
 * 反转单向和双向链表
 * 【题目】 分别实现反转单向链表和反转双向链表的函数。
 * 【要求】 如果链表长度为N，时间复杂度要求为O(N)，额外空间复杂度要求为O(1)
 */

public class ReverseList {

    /**
     * 反转单向链表
     */
    public static <E> Node<E> reverseList(Node<E> head) {
        if (head == null || head.next == null) {
            return head;
        }

        Node<E> pre = null, next = null;
        while (head != null) {
            next = head.next;
            head.next = pre;
            pre = head;

            head = next;
        }

        return pre;
    }

    /**
     * 反转双向链表
     */
    public static <E> Node<E> reverseDoublyList(Node<E> head) {
        if (head == null || head.next == null) {
            return head;
        }

        Node<E> pre = null, next = null;
        while (head != null) {
            next = head.next;
            head.next = pre;
            head.pre = next;

            pre = head;
            head = next;
        }

        return pre;
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
        head5.pre = head4;
        head6.pre = head5;
        Node<Integer> node2 = reverseDoublyList(head4);
        ListUtils.printDoublyList(node2);

    }

}
