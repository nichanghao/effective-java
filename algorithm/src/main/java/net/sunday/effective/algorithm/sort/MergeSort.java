package net.sunday.effective.algorithm.sort;

import net.sunday.effective.algorithm.util.SortUtils;

public class MergeSort {

    public static void mergeSort(int[] arr) {
        if (arr == null || arr.length < 2)
            return;

        mergeSort(arr, 0, arr.length - 1);
    }

    private static void mergeSort(int[] arr, int left, int right) {
        if (left >= right)
            return;

        int mid = ((right - left) >> 1) + left;
        mergeSort(arr, left, mid);
        mergeSort(arr, mid + 1, right);
        merge(arr, left, mid, right);
    }

    private static void merge(int[] arr, int left, int mid, int right) {
        int[] helper = new int[right - left + 1];
        int less = left, more = mid + 1, curr = 0;

        while (less <= mid && more <= right) {
            helper[curr++] = arr[less] < arr[more] ? arr[less++] : arr[more++];
        }
        while (less <= mid) {
            helper[curr++] = arr[less++];
        }
        while (more <= right) {
            helper[curr++] = arr[more++];
        }

        for (int i = 0; i < helper.length; i++) {
            arr[i + left] = helper[i];
        }
    }

    public static void main(String[] args) {
        SortUtils.testSort(MergeSort::mergeSort);
    }

}
