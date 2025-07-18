package net.sunday.effective.algorithm.sort;

import net.sunday.effective.algorithm.util.SortUtils;

public class InsertSort {

    public static void insertSort(int[] arr) {
        if (arr == null || arr.length < 2)
            return;

        for (int i = 1; i < arr.length; i++) {
            for (int j = i - 1; j >= 0 && arr[j] > arr[j + 1]; j--) {
                SortUtils.swap(arr, j, j + 1);
            }
        }
    }

    public static void main(String[] args) {
        SortUtils.testSort(InsertSort::insertSort);
    }
}
