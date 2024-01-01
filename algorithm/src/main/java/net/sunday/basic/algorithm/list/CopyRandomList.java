package net.sunday.basic.algorithm.list;

import net.sunday.basic.algorithm.list.entity.Node;
import net.sunday.basic.algorithm.list.util.ListUtils;

import java.util.Date;
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
    public static <E> Node<E> copyRandomList(Node<E> head) {
        if (head == null) {
            return null;
        }

        Map<Node<E>, Node<E>> hashMap = new HashMap<>();
        Node<E> curr = head;
        while (curr != null) {
            hashMap.put(curr, new Node<>(curr.data));
            curr = curr.next;
        }

        curr = head;
        while (curr != null) {
            if (curr.next != null) {
                hashMap.get(curr).next = hashMap.get(curr.next);
            }
            if (curr.rand != null) {
                hashMap.get(curr).rand = hashMap.get(curr.rand);
            }

            curr = curr.next;
        }

        return hashMap.get(head);
    }

    /**
     * 将拷贝节点放在原链表的后面进行拷贝，空间复杂度O(1)
     */
    public static <E> Node<E> copyRandomList2(Node<E> head) {
        if (head == null) {
            return null;
        }

        Node<E> curr = head, next = null;
        while (curr != null) {
            next = curr.next;
            curr.next = new Node<>(curr.data);
            curr.next.next = next;
            curr = next;
        }

        // 构建 random 指针
        curr = head;
        while (curr != null) {
            next = curr.next;
            next.rand = curr.rand == null ? null : curr.rand.next;
            curr = next.next;
        }

        // 恢复原链表
        curr = head;
        Node<E> res = head.next;
        while (curr.next != null) {
            next = curr.next;
            curr.next = next.next;
            curr = next;
        }

        return res;
    }

    //for test
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

        ListUtils.printRandomList(head);
        res1 = copyRandomList(head);
        ListUtils.printRandomList(res1);
        System.out.println("copyRandomList2 test:");
        res2 = copyRandomList2(head);
        ListUtils.printRandomList(res2);

    }
}
