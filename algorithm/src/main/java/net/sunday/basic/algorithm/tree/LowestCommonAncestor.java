package net.sunday.basic.algorithm.tree;

import net.sunday.basic.algorithm.tree.entity.Node;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 给定两个二叉树的节点node1和node2，找到他们的最低公共祖先节点
 */
public class LowestCommonAncestor {

    /**
     * 借助于哈希表的方式
     */
    public static <E> Node<E> getLowestCommonAncestor(Node<E> head, Node<E> o1, Node<E> o2) {
        if (head == null || o1 == head || o2 == head) return head;

        if (o1 == null) return o2;

        if (o2 == null) return o1;

        Map<Node<E>, Node<E>> parentMap = new HashMap<>();
        parentMap.put(head, null);
        // 构建每个节点和其头节点的对应关系
        preOrderRecur(head, parentMap);


        Set<Node<E>> o1Set = new HashSet<>();
        while (parentMap.containsKey(o1)) {
            o1Set.add(o1);
            o1 = parentMap.get(o1);
        }

        while (!o1Set.contains(o2)) {
            o2 = parentMap.get(o2);
        }

        return o2;
    }

    private static <E> void preOrderRecur(Node<E> head, Map<Node<E>, Node<E>> parentMap) {
        if (head == null) return;

        if (head.left != null) {
            parentMap.put(head.left, head);
        }
        if (head.right != null) {
            parentMap.put(head.right, head);
        }

        preOrderRecur(head.left, parentMap);
        preOrderRecur(head.right, parentMap);
    }

    /**
     * 递归的方式
     */
    public static <E> Node<E> getLowestCommonAncestor2(Node<E> head, Node<E> o1, Node<E> o2) {
        if (head == null || o1 == head || o2 == head)
            return head;

        Node<E> left = getLowestCommonAncestor2(head.left, o1, o2);
        Node<E> right = getLowestCommonAncestor2(head.right, o1, o2);

        if (left != null && right != null)
            return head;

        return left == null ? right : left;
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
        System.out.println("===============");

        Node<Integer> o1 = head.left.right;
        Node<Integer> o2 = head.right.left;

        System.out.println("o1 : " + o1.value);
        System.out.println("o2 : " + o2.value);
        System.out.println("ancestor : " + getLowestCommonAncestor(head, o1, o2).value);
        System.out.println("ancestor : " + getLowestCommonAncestor2(head, o1, o2).value);
        System.out.println("===============");

    }
}
