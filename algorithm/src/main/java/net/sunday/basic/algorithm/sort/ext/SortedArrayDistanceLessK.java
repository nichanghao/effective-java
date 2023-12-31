package net.sunday.basic.algorithm.sort.ext;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * 已知一个几乎有序的数组，几乎有序是指，如果把数组排好顺序的话，每个元
 * 素移动的距离可以不超过k，并且k相对于数组来说比较小。请选择一个合适的
 * 排序算法针对这个数据进行排序。
 */
public class SortedArrayDistanceLessK {

    public static void sortedArrayDistanceLessK(int[] arr, int k) {
        if (arr == null || arr.length == 1)
            return;

        k = Math.min(k, arr.length);
        PriorityQueue<Integer> heap = new PriorityQueue<>(arr.length);

        int curr = 0;
        for (; curr < k; curr++) {
            heap.add(arr[curr]);
        }

        int i = 0;
        for (; curr < arr.length; i++, curr++) {
            heap.add(arr[curr]);
            arr[i] = heap.poll();
        }

        while (!heap.isEmpty()) {
            arr[i++] = heap.poll();
        }

    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 4, 3, 5, 7, 8, 11, 10, 9, 12};
        sortedArrayDistanceLessK(arr, 3);
        System.out.println(Arrays.toString(arr));
    }
}
