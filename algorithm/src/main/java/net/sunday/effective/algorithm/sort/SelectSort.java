package net.sunday.effective.algorithm.sort;

import net.sunday.effective.algorithm.util.SortUtils;

public class SelectSort {

    public static void selectSort(int[] arr) {
        if (arr == null || arr.length < 2)
            return;

        for (int i = 0; i < arr.length - 1; i++) {
            // find the minimum element in the unsorted part of the array
            int minIndex = i + 1;
            for (int j = minIndex; j < arr.length; j++) {
                minIndex = arr[minIndex] < arr[j] ? minIndex : j;
            }

            if (arr[minIndex] < arr[i]) {
                SortUtils.swap(arr, minIndex, i);
            }
        }
    }

    public static void main(String[] args) {
        SortUtils.testSort(SelectSort::selectSort);
    }
}
