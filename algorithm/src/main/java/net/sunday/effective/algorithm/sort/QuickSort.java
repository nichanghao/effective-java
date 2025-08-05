package net.sunday.effective.algorithm.sort;

import net.sunday.effective.algorithm.util.SortUtils;

import java.util.Random;

public class QuickSort {

    public static void quickSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        quickSort(arr, 0, arr.length - 1);
    }

    private static void quickSort(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }

        //随机挑选基准点
        SortUtils.swap(arr, right, new Random().nextInt(right - left + 1) + left);
        int[] p = partition(arr, left, right);

        quickSort(arr, left, p[0] - 1);
        quickSort(arr, p[1] + 1, right);

    }

    private static int[] partition(int[] arr, int left, int right) {
        int curr = left, less = left - 1, more = right;
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

    public static void main(String[] args) {
        SortUtils.testSort(QuickSort::quickSort);
    }
}
