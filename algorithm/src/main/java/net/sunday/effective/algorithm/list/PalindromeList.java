package net.sunday.effective.algorithm.list;

import net.sunday.effective.algorithm.list.entity.Node;
import net.sunday.effective.algorithm.util.ListUtils;

import java.util.Stack;

/**
 * 判断一个链表是否为回文结构
 * 【题目】 给定一个链表的头节点head，请判断该链表是否为回文结构。
 * 例如： 1->2->1，返回true。 1->2->2->1，返回true。15->6->15，返回true。 1->2->3，返回false。
 * 进阶： 如果链表长度为N，时间复杂度达到O(N)，额外空间复杂度达到O(1)。
 */
public class PalindromeList {

    /**
     * 不限制空间复杂度的实现
     */
    public static boolean isPalindromeList(Node<Integer> head) {
        if (head == null || head.next == null) {
            return true;
        }

        Stack<Node<Integer>> stack = new Stack<>();
        Node<Integer> curr = head;
        while (curr != null) {
            stack.push(curr);
            curr = curr.next;
        }

        while (!stack.isEmpty()) {
            if (!stack.pop().data.equals(head.data)) {
                return false;
            }
            head = head.next;
        }

        return true;

    }

    /**
     * 空间复杂度为O(1)的实现
     */
    public static boolean isPalindromeList2(Node<Integer> head) {
        if (head == null || head.next == null) {
            return true;
        }

        Node<Integer> slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        Node<Integer> temp = slow;
        slow = ReverseList.reverseList(slow);
        fast = head;

        boolean flag = true;
        while (fast != null && slow != null) {
            if (!fast.data.equals(slow.data)) {
                flag = false;
                break;
            }
            fast = fast.next;
            slow = slow.next;
        }

        ReverseList.reverseList(temp);
        return flag;
    }

    /**
     * for test
     */
    public static void main(String[] args) {

        Node<Integer> head;

        head = new Node<>(1);
        head.next = new Node<>(2);
        head.next.next = new Node<>(2);
        head.next.next.next = new Node<>(1);
        ListUtils.printList(head);
        System.out.print(isPalindromeList(head) + " | ");
        ListUtils.printList(head);
        System.out.println("=========================");

        head = new Node<>(1);
        head.next = new Node<>(2);
        head.next.next = new Node<>(3);
        head.next.next.next = new Node<>(2);
        head.next.next.next.next = new Node<>(1);
        ListUtils.printList(head);
        System.out.print(isPalindromeList2(head) + " | ");
        ListUtils.printList(head);
        System.out.println("=========================");

    }
}
