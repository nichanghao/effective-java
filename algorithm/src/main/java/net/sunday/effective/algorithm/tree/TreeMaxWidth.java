package net.sunday.effective.algorithm.tree;

import net.sunday.effective.algorithm.tree.entity.Node;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

/**
 * 求一棵二叉树的宽度
 */

public class TreeMaxWidth {

    /**
     * 借助于哈希表的方式
     */
    public static <E> int getMaxWidth(Node<E> head) {
        if (head == null) {
            return 0;
        }

        Map<Node<E>, Integer> levelMap = new HashMap<>();

        Queue<Node<E>> queue = new ArrayDeque<>();
        queue.add(head);
        levelMap.put(head, 1);

        int maxWidth = 0, currLevel = 1, currLevelWidth = 0;
        while (!queue.isEmpty()) {
            head = queue.poll();

            if (head.left != null) {
                queue.add(head.left);
                levelMap.put(head.left, levelMap.get(head) + 1);
            }

            if (head.right != null) {
                queue.add(head.right);
                levelMap.put(head.right, levelMap.get(head) + 1);
            }

            if (currLevel == levelMap.get(head)) {
                currLevelWidth++;
            } else {
                // 说明遍历到了下一层，开始计算上一层的数据并汇总
                maxWidth = Math.max(maxWidth, currLevelWidth);
                currLevelWidth = 1;
                currLevel++;
            }
        }

        return maxWidth;
    }

    /**
     * 不借助于哈希表的方式
     */
    public static <E> int getMaxWidth2(Node<E> head) {
        if (head == null) {
            return 0;
        }

        Queue<Node<E>> queue = new ArrayDeque<>();
        queue.add(head);

        Node<E> currEnd = head, nextEnd = null;
        int maxWidth = 0, currLevelWidth = 0;
        while (!queue.isEmpty()) {
            head = queue.poll();
            currLevelWidth++;

            if (head.left != null) {
                queue.add(head.left);
                nextEnd = head.left;
            }
            if (head.right != null) {
                queue.add(head.right);
                nextEnd = head.right;
            }

            if (head == currEnd) {
                maxWidth = Math.max(maxWidth, currLevelWidth);
                currEnd = nextEnd;
                currLevelWidth = 0;
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
