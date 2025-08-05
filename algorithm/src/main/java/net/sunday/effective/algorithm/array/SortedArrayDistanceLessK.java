package net.sunday.effective.algorithm.array;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * 已知一个几乎有序的数组，几乎有序是指，如果把数组排好顺序的话，每个元
 * 素移动的距离可以不超过k，并且k相对于数组来说比较小。请选择一个合适的
 * 排序算法针对这个数据进行排序。
 */
public class SortedArrayDistanceLessK {

    public static void sortedArrayDistanceLessK(int[] arr, int k) {
        if (arr == null || arr.length < 2) {
            return;
        }

        if (k > arr.length || k < 0) {
            k = arr.length;
        }

        // 默认为小根堆
        PriorityQueue<Integer> queue = new PriorityQueue<>(k + 1);
        int index = 0;
        for (; index < k; index++) {
            queue.add(arr[index]);
        }

        int i = 0;
        for (; index < arr.length; index++, i++) {
            queue.add(arr[index]);
            arr[i] = queue.poll();
        }

        while (!queue.isEmpty()) {
            arr[i++] = queue.poll();
        }
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 4, 3, 5, 7, 8, 11, 10, 9, 12};
        sortedArrayDistanceLessK(arr, 3);
        System.out.println(Arrays.toString(arr));
    }

}
