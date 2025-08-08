package net.sunday.effective.algorithm.list;

import net.sunday.effective.algorithm.list.entity.Node;


/**
 * 两个单链表相交的一系列问题
 * 【题目】 在本题中，单链表可能有环，也可能无环。给定两个单链表的头节点 head1和head2，这两个链表可能相交，
 * 也可能不相交。请实现一个函数， 如果两个链表相交，请返回相交的第一个节点；如果不相交，返回null 即可。
 * 要求：如果链表1的长度为N，链表2的长度为M，时间复杂度请达到 O(N+M)，额外空间复杂度请达到O(1)。
 */

public class FindFirstIntersectNode {

    public static <E> Node<E> findFirstIntersectNode(Node<E> head1, Node<E> head2) {
        if (head1 == null || head2 == null) {
            return null;
        }

        Node<E> loop1 = getLoopNode(head1);
        Node<E> loop2 = getLoopNode(head2);

        if (loop1 == null && loop2 == null) {
            return getNoLoopNode(head1, head2);
        } else if (loop1 != null && loop2 != null) {
            return getLoopNode(head1, head2, loop1, loop2);
        }
        return null;
    }

    /**
     * 判断链表是否有环,并返回第一个相交的环节点
     */
    private static <E> Node<E> getLoopNode(Node<E> head) {
        if (head == null || head.next == null || head.next.next == null) {
            return null;
        }

        Node<E> slow = head.next, fast = head.next.next;
        while (slow != fast) {
            if (fast == null || fast.next == null) {
                return null;
            }
            slow = slow.next;
            fast = fast.next.next;
        }

        fast = head;
        while (fast != slow) {
            fast = fast.next;
            slow = slow.next;
        }

        return fast;
    }

    /**
     * 2个无环的链表相交
     */
    private static <E> Node<E> getNoLoopNode(Node<E> head1, Node<E> head2) {
        Node<E> p1 = head1, p2 = head2;

        // 找出2条链表的长度差
        int size = 0;
        while (p1 != null) {
            p1 = p1.next;
            size++;
        }
        while (p2 != null) {
            p2 = p2.next;
            size--;
        }

        // p1 指向较长的链表的头部
        p1 = size > 0 ? head1 : head2;
        p2 = p1 == head1 ? head2 : head1;

        size = Math.abs(size);
        while (size > 0) {
            p1 = p1.next;
            size--;
        }

        while (p1 != p2) {
            if (p1 == null || p2 == null) {
                return null;
            }

            p1 = p1.next;
            p2 = p2.next;
        }

        return p1;
    }

    /**
     * 2个有环链表相交
     */
    private static <E> Node<E> getLoopNode(Node<E> head1, Node<E> head2, Node<E> loop1, Node<E> loop2) {
        Node<E> p1 = head1, p2 = head2;
        if (loop1 == loop2) {
            // 说明2条链表`环部分`和`环外部分`都重合了
            int size = 0;
            while (p1 != loop1) {
                size++;
                p1 = p1.next;
            }
            while (p2 != loop2) {
                size--;
                p2 = p2.next;
            }

            p1 = size > 0 ? head1 : head2;
            p2 = p1 == head1 ? head2 : head1;

            size = Math.abs(size);
            while (size-- > 0) {
                p1 = p1.next;
            }
            while (p1 != p2) {
                p1 = p1.next;
                p2 = p2.next;
            }

            return p1;
        } else {
            // 判断2条链表是否共用1个环
            p1 = loop1.next;
            while (p1 != loop1) {
                if (p1 == loop2) {
                    return loop1;
                }
                p1 = p1.next;
            }

            return null;
        }
    }

    /**
     * for test
     */
    @SuppressWarnings("all")
    public static void main(String[] args) {
        // 1->2->3->4->5->6->7->null
        Node head1 = new Node<>(1);
        head1.next = new Node<>(2);
        head1.next.next = new Node<>(3);
        head1.next.next.next = new Node<>(4);
        head1.next.next.next.next = new Node<>(5);
        head1.next.next.next.next.next = new Node<>(6);
        head1.next.next.next.next.next.next = new Node<>(7);

        // 0->9->8->6->7->null
        Node head2 = new Node<>(0);
        head2.next = new Node<>(9);
        head2.next.next = new Node<>(8);
        head2.next.next.next = head1.next.next.next.next.next; // 8->6
        System.out.println(findFirstIntersectNode(head1, head2).data);

        // 1->2->3->4->5->6->7->4...
        head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1.next.next.next = new Node(4);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(6);
        head1.next.next.next.next.next.next = new Node(7);
        head1.next.next.next.next.next.next = head1.next.next.next; // 7->4

        // 0->9->8->2...
        head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next; // 8->2
        System.out.println(findFirstIntersectNode(head1, head2).data);

        // 0->9->8->6->4->5->6..
        head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next.next.next.next.next; // 8->6
        System.out.println(findFirstIntersectNode(head1, head2).data);

    }

}
