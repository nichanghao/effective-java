package net.sunday.effective.algorithm.tree;

import net.sunday.effective.algorithm.tree.entity.Node;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * 给定一颗二叉树和两个的节点node1和node2，找到他们的最低公共祖先节点
 */
public class LowestCommonAncestor {

    /**
     * use hashmap
     */
    public static <E> Node<E> getLowestCommonAncestor(Node<E> head, Node<E> o1, Node<E> o2) {
        if (head == null || o1 == head || o2 == head) {
            return head;
        }

        if (o1 == null) {
            return o2;
        }
        if (o2 == null) {
            return o1;
        }

        Map<Node<E>, Node<E>> parentMap = new HashMap<>();
        parentMap.put(head, null);
        scan(head, parentMap);

        Set<Node<E>> o1ParentSet = new HashSet<>();
        o1ParentSet.add(null);
        while (parentMap.containsKey(o1)) {
            o1ParentSet.add(o1);
            o1 = parentMap.get(o1);
        }

        while (!o1ParentSet.contains(o2)) {
            o2 = parentMap.get(o2);
        }

        return o2;
    }

    private static <E> void scan(Node<E> head, Map<Node<E>, Node<E>> parentMap) {
        if (head == null)
            return;

        if (head.left != null) {
            parentMap.put(head.left, head);
        }
        if (head.right != null) {
            parentMap.put(head.right, head);
        }
        scan(head.left, parentMap);
        scan(head.right, parentMap);
    }

    /**
     * 递归实现，返回值表示当前节点和o1、o2的最近公共祖先
     */
    public static <E> Node<E> getLowestCommonAncestor2(Node<E> head, Node<E> o1, Node<E> o2) {
        if (head == null || o1 == head || o2 == head) {
            return head;
        }

        Node<E> left = getLowestCommonAncestor2(head.left, o1, o2);
        Node<E> right = getLowestCommonAncestor2(head.right, o1, o2);

        if (left != null && right != null) {
            return head;
        }

        return left != null ? left : right;
    }

    @SuppressWarnings("Duplicates")
    public static void main(String[] args) {
        Node<Integer> head = new Node<>(1);
        head.left = new Node<>(2);
        head.right = new Node<>(3);
        head.left.left = new Node<>(4);
        head.left.right = new Node<>(5);
        head.right.left = new Node<>(6);
        head.right.right = new Node<>(7);
        head.right.right.left = new Node<>(8);

        Node<Integer> o1 = head.left.right;
        Node<Integer> o2 = head.right.left;

        System.out.println("o1 : " + o1.value);
        System.out.println("o2 : " + o2.value);
        System.out.println("ancestor : " + getLowestCommonAncestor(head, o1, o2).value);
        System.out.println("ancestor : " + getLowestCommonAncestor2(head, o1, o2).value);
    }

}
