package net.sunday.basic.algorithm.tree;

import net.sunday.basic.algorithm.tree.entity.Node;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

/**
 * 求一棵二叉树的宽度（深度优先遍历）
 */
public class TreeMaxWidth {

    /**
     * 借助于哈希表的方式
     */
    public static <E> int getMaxWidth(Node<E> head) {
        if (head == null)
            return 0;

        Map<Node<E>, Integer> nodeLevelMap = new HashMap<>();
        nodeLevelMap.put(head, 1);

        Queue<Node<E>> queue = new ArrayDeque<>();
        queue.add(head);

        int maxWidth = 0, currLevel = 1, currLevelWidth = 0;
        while (!queue.isEmpty()) {
            head = queue.poll();
            if (nodeLevelMap.get(head) == currLevel) {
                currLevelWidth++;
            } else {
                maxWidth = Math.max(maxWidth, currLevelWidth);
                currLevelWidth = 1;
                currLevel++;
            }

            if (head.left != null) {
                queue.add(head.left);
                nodeLevelMap.put(head.left, nodeLevelMap.get(head) + 1);
            }

            if (head.right != null) {
                queue.add(head.right);
                nodeLevelMap.put(head.right, nodeLevelMap.get(head) + 1);
            }

        }

        return maxWidth;
    }

    /**
     * 不借助哈希表的方式
     */
    public static <E> int getMaxWidth2(Node<E> head) {
        if (head == null)
            return 0;

        Queue<Node<E>> queue = new ArrayDeque<>();
        queue.add(head);

        Node<E> curr = head, currLevelEnd = head, nextLevelEnd = null;
        int maxWidth = -1, currLevelWidth = 1;
        while (!queue.isEmpty()) {
            curr = queue.poll();

            if (curr.left != null) {
                queue.add(curr.left);
                nextLevelEnd = curr.left;
            }

            if (curr.right != null) {
                queue.add(curr.right);
                nextLevelEnd = curr.right;
            }

            if (curr == currLevelEnd) {
                maxWidth = Math.max(maxWidth, currLevelWidth + 1);
                currLevelEnd = nextLevelEnd;
                currLevelWidth = 0;
            } else {
                currLevelWidth++;
            }
        }

        return maxWidth;
    }

    /**
     * for test
     */
    @SuppressWarnings("Duplicates")
    public static void main(String[] args) {
        Node<Integer> head = new Node<>(5);
        head.left = new Node<>(3);
        head.right = new Node<>(8);
        head.left.left = new Node<>(2);
        head.left.right = new Node<>(4);
        head.left.left.left = new Node<>(1);
        head.right.left = new Node<>(7);
        head.right.left.left = new Node<>(6);
        head.right.right = new Node<>(10);
        head.right.right.left = new Node<>(9);
        head.right.right.right = new Node<>(11);

        System.out.println(getMaxWidth(head));
        System.out.println(getMaxWidth2(head));
    }

}
