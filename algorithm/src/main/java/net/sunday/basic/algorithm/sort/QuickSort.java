package net.sunday.basic.algorithm.sort;

import net.sunday.basic.algorithm.sort.util.SortUtils;

import java.util.Random;

/**
 * 快排
 */
public class QuickSort {

    public static void quickSort(int[] arr) {
        if (arr == null || arr.length <= 1) return;

        quickSort(arr, 0, arr.length - 1);
    }

    private static void quickSort(int[] arr, int left, int right) {
        if (left >= right) return;

        SortUtils.swap(arr, right, left + new Random().nextInt(right - left + 1));
        int[] p = partition(arr, left, right);
        quickSort(arr, left, p[0] - 1);
        quickSort(arr, p[1] + 1, right);
    }

    private static int[] partition(int[] arr, int left, int right) {
        int less = left - 1, more = right, curr = left;
        while (curr < more) {
            if (arr[curr] < arr[right]) {
                SortUtils.swap(arr, curr++, ++less);
            } else if (arr[curr] > arr[right]) {
                SortUtils.swap(arr, curr, --more);
            } else {
                curr++;
            }
        }

        SortUtils.swap(arr, right, more);

        return new int[]{less + 1, more};
    }

    @SuppressWarnings("Duplicates")
    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = SortUtils.generateRandomArray(maxSize, maxValue);
            int[] arr2 = arr1.clone();
            quickSort(arr1);
            SortUtils.comparator(arr2);
            if (!SortUtils.isEqual(arr1, arr2)) {
                succeed = false;
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Error!");

        int[] arr = SortUtils.generateRandomArray(maxSize, maxValue);
        SortUtils.printArray(arr);
        quickSort(arr);
        SortUtils.printArray(arr);
    }
}
