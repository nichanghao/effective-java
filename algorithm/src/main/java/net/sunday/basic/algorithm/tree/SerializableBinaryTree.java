package net.sunday.basic.algorithm.tree;

import net.sunday.basic.algorithm.tree.entity.Node;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

/**
 * 二叉树的序列化和反序列化
 */
public class SerializableBinaryTree {

    /**
     * 先序遍历的方式进行序列化 null-->#
     */
    public static <E> String serializeByPre(Node<E> node) {
        if (node == null) {
            return "#_";
        }
        String str = node.value + "_";
        return str + serializeByPre(node.left) + serializeByPre(node.right);
    }

    /**
     * 先序遍历的方式进行反序列化
     */
    public static Node<Integer> deSerializeByPre(String str) {
        Queue<String> queue = new ArrayDeque<>(Arrays.asList(str.split("_")));
        return deSerializeByPre(queue);
    }

    private static Node<Integer> deSerializeByPre(Queue<String> queue) {
        String str = queue.poll();
        if ("#".equals(str) || str == null) {
            return null;
        }
        Node<Integer> node = new Node<>(Integer.parseInt(str));
        node.left = deSerializeByPre(queue);
        node.right = deSerializeByPre(queue);

        return node;
    }

    /**
     * 按层序列化
     */
    public static <E> String serializeByLevel(Node<E> node) {
        if (node == null)
            return "#_";

        Queue<Node<E>> queue = new ArrayDeque<>();
        queue.add(node);

        StringBuilder sb = new StringBuilder();
        sb.append(node.value).append("_");
        while (!queue.isEmpty()) {
            node = queue.poll();

            if (node.left != null) {
                queue.add(node.left);
                sb.append(node.left.value).append("_");
            } else {
                sb.append("#_");
            }

            if (node.right != null) {
                queue.add(node.right);
                sb.append(node.right.value).append("_");
            } else {
                sb.append("#_");
            }
        }

        return sb.toString();
    }

    /**
     * 按层反序列化
     */
    public static Node<Integer> deSerializeByLevel(String str) {
        if (str == null)
            return null;

        String[] arr = str.split("_");
        Queue<Node<Integer>> queue = new ArrayDeque<>(arr.length);

        int index = 0;
        // 建立头节点
        Node<Integer> head = getNodeByStr(arr[index++]);
        queue.add(head);

        Node<Integer> curr;
        while (!queue.isEmpty()) {
            curr = queue.poll();
            curr.left = getNodeByStr(arr[index++]);
            curr.right = getNodeByStr(arr[index++]);

            if (curr.left != null) {
                queue.add(curr.left);
            }
            if (curr.right != null) {
                queue.add(curr.right);
            }
        }

        return head;
    }

    private static Node<Integer> getNodeByStr(String str) {
        if ("#".equals(str))
            return null;

        return new Node<>(Integer.parseInt(str));
    }


    /**
     * for test
     */
    @SuppressWarnings("Duplicates")
    public static void main(String[] args) {
        Node<Integer> head = new Node<>(1);
        head.left = new Node<>(2);
        head.right = new Node<>(3);
        head.left.left = new Node<>(4);
        head.right.right = new Node<>(5);

        System.out.println("source tree by in-order:");
        PrintBinaryTree.inOrderNoRecur(head);
        String str = serializeByPre(head);
        System.out.println("serialize tree by in-order:\n" + str);
        head = deSerializeByPre(str);
        System.out.println("deserialize tree by in-order:");
        PrintBinaryTree.inOrderNoRecur(head);

        str = serializeByLevel(head);
        System.out.println("serialize tree by level:\n" + str);
        head = deSerializeByLevel(str);
        System.out.println("deserialize tree by level:");
        PrintBinaryTree.preOrderNoRecur(head);
    }
}
