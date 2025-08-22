package net.sunday.effective.algorithm.tree;

import net.sunday.effective.algorithm.tree.entity.Node;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

/**
 * 二叉树的序列化和反序列化
 * 1. `#`作为空节点的表示(null)
 * 2. `_`分割作为节点之间的分隔符
 */

public class SerializableBinaryTree {

    private static final String SEPARATOR = "_";

    private static final String NULL_NODE = "#";

    /**
     * 先序的方式进行序列化
     */
    public static String serializeByPre(Node<Integer> node) {
        if (node == null) {
            return NULL_NODE + SEPARATOR;
        }

        String str = node.value + SEPARATOR;
        return str + serializeByPre(node.left) + serializeByPre(node.right);
    }

    /**
     * 先序的方式进行反序列化
     */
    public static Node<Integer> deserializeByPre(String str) {
        if (str == null || str.isBlank()) {
            return null;
        }

        String[] arr = str.split(SEPARATOR);
        if (arr.length == 0) {
            return null;
        }

        Queue<String> queue = new ArrayDeque<>(Arrays.asList(arr));

        return deserializeByPre(queue);
    }

    private static Node<Integer> deserializeByPre(Queue<String> queue) {
        if (queue.isEmpty()) {
            return null;
        }

        String str = queue.poll();
        if (NULL_NODE.equals(str)) {
            return null;
        }

        Node<Integer> head = new Node<>(Integer.parseInt(str));
        head.left = deserializeByPre(queue);
        head.right = deserializeByPre(queue);

        return head;
    }

    /**
     * 按层序列化
     */
    public static String serializeByLevel(Node<Integer> node) {
        if (node == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        Queue<Node<Integer>> queue = new ArrayDeque<>();
        queue.add(node);
        sb.append(node.value).append(SEPARATOR);

        while (!queue.isEmpty()) {
            node = queue.poll();
            if (node.left != null) {
                queue.add(node.left);
                sb.append(node.left.value).append(SEPARATOR);
            } else {
                sb.append(NULL_NODE).append(SEPARATOR);
            }

            if (node.right != null) {
                queue.add(node.right);
                sb.append(node.right.value).append(SEPARATOR);
            } else {
                sb.append(NULL_NODE).append(SEPARATOR);
            }

        }

        return sb.toString();
    }

    /**
     * 按层反序列化
     */
    public static Node<Integer> deserializeByLevel(String str) {
        if (str == null || str.isBlank()) {
            return null;
        }

        String[] arr = str.split(SEPARATOR);
        int index = 0;

        Queue<Node<Integer>> queue = new ArrayDeque<>();
        Node<Integer> head = getNodeByString(arr[index++]);
        queue.add(head);

        while (!queue.isEmpty()) {
            Node<Integer> node = queue.poll();
            node.left = getNodeByString(arr[index++]);
            if (node.left != null) {
                queue.add(node.left);
            }

            node.right = getNodeByString(arr[index++]);
            if (node.right != null) {
                queue.add(node.right);
            }
        }

        return head;
    }

    private static Node<Integer> getNodeByString(String str) {
        if (NULL_NODE.equals(str))
            return null;

        return new Node<>(Integer.parseInt(str));
    }

    /**
     * <pre>
     *     1
     *    / \
     *   2   3
     *  /     \
     * 4       5
     * <pre/>
     */
    @SuppressWarnings("Duplicates")
    public static void main(String[] args) {
        Node<Integer> head = new Node<>(1);
        head.left = new Node<>(2);
        head.right = new Node<>(3);
        head.left.left = new Node<>(4);
        head.right.right = new Node<>(5);

        System.out.println("source tree by pre-order:");
        PrintBinaryTree.preOrderNoRecur(head);
        String str = serializeByPre(head);
        System.out.println("serialize tree by pre-order:\n" + str);
        head = deserializeByPre(str);
        System.out.println("deserialize tree by pre-order:");
        PrintBinaryTree.preOrderNoRecur(head);

        System.out.println("===========================================");
        str = serializeByLevel(head);
        System.out.println("serialize tree by level:\n" + str);
        head = deserializeByLevel(str);
        System.out.println("deserialize tree by level:");
        PrintBinaryTree.printByLevel(head);
    }


}
