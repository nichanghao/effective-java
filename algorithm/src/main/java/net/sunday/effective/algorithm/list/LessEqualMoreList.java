package net.sunday.effective.algorithm.list;

import net.sunday.effective.algorithm.list.entity.Node;
import net.sunday.effective.algorithm.util.ListUtils;

/**
 * 将单向链表按某值划分成左边小、中间相等、右边大的形式
 * 【题目】 给定一个单向链表的头节点head，节点的值类型是整型，再给定一个整数pivot。实现一个调整链表的函数，
 * 将链表调整为左部分都是值小于 pivot的节点，中间部分都是值等于pivot的节点，右部分都是值大于 pivot的节点。
 * 除这个要求外，对调整后的节点顺序没有更多的要求。 例如：链表9->0->4->5->1，pivot=3。
 * 调整后链表可以是1->0->4->9->5，也可以是0->1->9->5->4。总之，满 足左部分都是小于3的节点，
 * 中间部分都是等于3的节点（本例中这个部分为空），右部分都是大于3的节点即可。对某部分内部的节点顺序不做要求。
 * 进阶： 在原问题的要求之上再增加如下两个要求。
 * 在左、中、右三个部分的内部也做顺序要求，要求每部分里的节点从左 到右的顺序与原链表中节点的先后次序一致。
 * 例如：链表9->0->4->5->1，pivot=3。调整后的链表是0->1->9->4->5。 在满足原问题要求的同时，
 * 左部分节点从左到右为0、1。在原链表中也 是先出现0，后出现1；中间部分在本例中为空，不再讨论；
 * 右部分节点从左到右为9、4、5。在原链表中也是先出现9，然后出现4，最后出现5。
 * 如果链表长度为N，时间复杂度请达到O(N)，额外空间复杂度请达到O(1)。
 */

public class LessEqualMoreList {

    public static Node<Integer> lessEqualMoreList(Node<Integer> head, Integer pivot) {
        if (head == null || head.next == null) {
            return head;
        }

        Node<Integer> lessStart = null, lessEnd = null;
        Node<Integer> equalStart = null, equalEnd = null;
        Node<Integer> moreStart = null, moreEnd = null;
        while (head != null) {
            if (head.data.equals(pivot)) {
                if (equalStart == null) {
                    equalEnd = equalStart = new Node<>(head.data);
                } else {
                    equalEnd.next = new Node<>(head.data);
                    equalEnd = equalEnd.next;
                }
            } else if (head.data < pivot) {
                if (lessStart == null) {
                    lessEnd = lessStart = new Node<>(head.data);
                } else {
                    lessEnd.next = new Node<>(head.data);
                    lessEnd = lessEnd.next;
                }
            } else {
                if (moreStart == null) {
                    moreEnd = moreStart = new Node<>(head.data);
                } else {
                    moreEnd.next = new Node<>(head.data);
                    moreEnd = moreEnd.next;
                }
            }

            head = head.next;
        }

        if (lessEnd != null) {
            lessEnd.next = equalStart != null ? equalStart : moreStart;
        }
        if (equalEnd != null) {
            equalEnd.next = moreStart;
        }

        return lessStart != null ? lessStart : equalStart != null ? equalStart : moreStart;

    }

    /**
     * for test
     */
    public static void main(String[] args) {
        Node<Integer> head1 = new Node<>(7);
        head1.next = new Node<>(9);
        head1.next.next = new Node<>(1);
        head1.next.next.next = new Node<>(8);
        head1.next.next.next.next = new Node<>(5);
        head1.next.next.next.next.next = new Node<>(2);
        head1.next.next.next.next.next.next = new Node<>(5);
        ListUtils.printList(head1);
        head1 = lessEqualMoreList(head1, 5);
        ListUtils.printList(head1);

    }
}
