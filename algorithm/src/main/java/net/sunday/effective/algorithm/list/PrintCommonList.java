package net.sunday.effective.algorithm.list;


import net.sunday.effective.algorithm.list.entity.Node;
import net.sunday.effective.algorithm.util.ListUtils;

import java.util.Objects;

/**
 * 打印两个有序链表的公共部分
 * 【题目】 给定两个有序链表的头指针head1和head2，打印两个链表的公共部分。
 */
public class PrintCommonList {

    public static void printCommonList(Node<Integer> head1, Node<Integer> head2) {
        if (head1 == null || head2 == null) {
            return;
        }

        while (head1 != null && head2 != null) {
            if (Objects.equals(head1.data, head2.data)) {
                System.out.print(head1.data + "\t");
                head1 = head1.next;
                head2 = head2.next;
            } else if (head1.data < head2.data) {
                head1 = head1.next;
            } else {
                head2 = head2.next;
            }
        }

    }


    /**
     * for test
     */
    public static void main(String[] args) {
        Node<Integer> node1 = new Node<>(2);
        node1.next = new Node<>(3);
        node1.next.next = new Node<>(5);
        node1.next.next.next = new Node<>(6);

        Node<Integer> node2 = new Node<>(1);
        node2.next = new Node<>(2);
        node2.next.next = new Node<>(5);
        node2.next.next.next = new Node<>(7);
        node2.next.next.next.next = new Node<>(8);

        ListUtils.printList(node1);
        ListUtils.printList(node2);
        printCommonList(node1, node2);
    }

}
