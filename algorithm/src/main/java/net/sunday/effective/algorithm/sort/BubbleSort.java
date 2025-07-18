package net.sunday.effective.algorithm.sort;

import net.sunday.effective.algorithm.util.SortUtils;

public class BubbleSort {

    public static void bubbleSort(int[] arr) {
        if (arr == null || arr.length < 2)
            return;

        for (int i = arr.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                // 将最大的数放到最后面
                if (arr[j] > arr[j + 1]) {
                    SortUtils.swap(arr, j, j + 1);
                }
            }
        }
    }

    public static void main(String[] args) {
        SortUtils.testSort(BubbleSort::bubbleSort);
    }
}
