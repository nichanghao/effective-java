package net.sunday.basic.algorithm.sort;

import net.sunday.basic.algorithm.sort.util.SortUtils;

/**
 * 堆排序
 */
public class HeapSort {

    public static void heapSort(int[] arr) {
        if (arr == null || arr.length < 2) return;

        for (int i = 1; i < arr.length; i++) {
            heapInsert(arr, i);
        }

        int heapSize = arr.length;
        SortUtils.swap(arr, 0, --heapSize);
        while (heapSize > 0) {
            heapify(arr, 0, heapSize);
            SortUtils.swap(arr, 0, --heapSize);
        }

    }

    /**
     * 向下调整为大顶堆
     */
    private static void heapify(int[] arr, int index, int heapSize) {
        int left = index * 2 + 1;
        while (left < heapSize) {
            int largest = left + 1 < heapSize && arr[left + 1] > arr[left] ? left + 1 : left;
            largest = arr[index] >= arr[largest] ? index : largest;

            if (largest == index) break;

            SortUtils.swap(arr, index, largest);
            index = largest;
            left = index * 2 + 1;
        }
    }


    /**
     * 向上调整为大顶堆
     */
    private static void heapInsert(int[] arr, int index) {
        while (arr[index] > arr[(index - 1) / 2]) {
            SortUtils.swap(arr, index, (index - 1) / 2);
            index = (index - 1) / 2;
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
            heapSort(arr1);
            SortUtils.comparator(arr2);
            if (!SortUtils.isEqual(arr1, arr2)) {
                succeed = false;
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Error!");

        int[] arr = SortUtils.generateRandomArray(maxSize, maxValue);
        SortUtils.printArray(arr);
        heapSort(arr);
        SortUtils.printArray(arr);

    }

}
