package net.sunday.basic.algorithm.sort;

import net.sunday.basic.algorithm.sort.util.SortUtils;

/**
 * 插入排序
 */
public class InsertSort {

    public static void insertSort(int[] arr) {
        if (arr == null || arr.length < 2) return;


        for (int i = 1; i < arr.length; i++) {
            for (int j = i - 1; j >= 0 && arr[j] > arr[j + 1]; j--) {
                SortUtils.swap(arr, j, j + 1);
            }
        }

    }

    /**
     * for test
     */
    @SuppressWarnings("Duplicates")
    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = SortUtils.generateRandomArray(maxSize, maxValue);
            int[] arr2 = arr1.clone();
            insertSort(arr1);
            SortUtils.comparator(arr2);
            if (!SortUtils.isEqual(arr1, arr2)) {
                succeed = false;
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Error!");

        int[] arr = SortUtils.generateRandomArray(maxSize, maxValue);
        SortUtils.printArray(arr);
        insertSort(arr);
        SortUtils.printArray(arr);
    }

}
