package net.sunday.effective.algorithm.list;

import net.sunday.effective.algorithm.list.entity.Node;

import java.util.HashMap;
import java.util.Map;


/**
 * 复制含有随机指针节点的链表
 * 【题目】 一种特殊的链表节点类描述如下：
 * public class Node {
 * public int value;
 * public Node next;
 * public Node rand;
 * public Node(int data) { this.value = data; }
 * }
 * Node类中的value是节点值，next指针和正常单链表中next指针的意义一 样，都指向下一个节点，
 * rand指针是Node类中新增的指针，这个指针可能指向链表中的任意一个节点，也可能指向null。
 * 给定一个由Node节点类型组成的无环单链表的头节点head，请实现一个 函数完成这个链表中所有结构的复制，
 * 并返回复制的新链表的头节点。 进阶：不使用额外的数据结构，只用有限几个变量，且在时间复杂度为 O(N)
 * 内完成原问题要实现的函数。
 */

public class CopyRandomList {

    /**
     * 使用哈希表的方式进行拷贝
     */
    public static Node<Integer> copyRandomList(Node<Integer> head) {
        if (head == null) {
            return null;
        }

        Map<Node<Integer>, Node<Integer>> helper = new HashMap<>();
        Node<Integer> curr = head;
        while (curr != null) {
            helper.put(curr, new Node<>(curr.data));
            curr = curr.next;
        }

        curr = head;
        while (curr != null) {
            helper.get(curr).next = helper.get(curr.next);
            if (curr.rand != null) {
                helper.get(curr).rand = helper.get(curr.rand);
            }

            curr = curr.next;
        }

        return helper.get(head);
    }

    /**
     * 将拷贝节点放在原链表的后面进行拷贝
     */
    public static Node<Integer> copyRandomList2(Node<Integer> head) {
        if (head == null) {
            return null;
        }

        Node<Integer> curr = head, next;
        while (curr != null) {
            next = curr.next;
            curr.next = new Node<>(curr.data);
            curr.next.next = next;
            curr = next;
        }

        curr = head;
        while (curr != null) {
            if (curr.rand != null) {
                curr.next.rand = curr.rand.next;
            }
            curr = curr.next.next;
        }

        curr = head;
        Node<Integer> result = curr.next;
        while (curr.next != null) {
            next = curr.next;
            curr.next = curr.next.next;
            curr = next;
        }

        return result;
    }

    /**
     * for test
     */
    @SuppressWarnings("all")
    public static void main(String[] args) {
        Node head = null;
        Node res1 = null;
        Node res2 = null;
        head = new Node<>(1);
        head.next = new Node<>(2);
        head.next.next = new Node<>(3);
        head.next.next.next = new Node<>(4);
        head.next.next.next.next = new Node<>(5);
        head.next.next.next.next.next = new Node<>(6);

        head.rand = head.next.next.next.next.next; // 1 -> 6
        head.next.rand = head.next.next.next.next.next; // 2 -> 6
        head.next.next.rand = head.next.next.next.next; // 3 -> 5
        head.next.next.next.rand = head.next.next; // 4 -> 3
        head.next.next.next.next.rand = null; // 5 -> null
        head.next.next.next.next.next.rand = head.next.next.next; // 6 -> 4

        printRandomList(head);
        res1 = copyRandomList(head);
        printRandomList(res1);
        System.out.println("copyRandomList2 test:");
        res2 = copyRandomList2(head);
        printRandomList(res2);

    }

    /**
     * 打印随机链表
     */
    private static void printRandomList(Node<Integer> head) {
        Node<Integer> cur = head;
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
