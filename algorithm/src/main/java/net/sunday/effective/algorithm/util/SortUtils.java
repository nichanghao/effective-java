package net.sunday.effective.algorithm.util;

import java.util.Arrays;
import java.util.function.Consumer;

/**
 * 排序工具类
 */
public class SortUtils {

    /**
     * swap data
     */
    public static void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    /**
     * 测试排序算法
     */
    public static void testSort(Consumer<int[]> consumer) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = SortUtils.generateRandomArray(maxSize, maxValue);
            int[] arr2 = arr1.clone();
            consumer.accept(arr1);
            SortUtils.comparator(arr2);
            if (!SortUtils.isEqual(arr1, arr2)) {
                succeed = false;
                break;
            }
        }
        if (!succeed) {
            throw new ArithmeticException("sort failed!");
        }

        int[] arr = SortUtils.generateRandomArray(maxSize, maxValue);
        SortUtils.printArray(arr);
        consumer.accept(arr);
        SortUtils.printArray(arr);
    }

    /**
     * system sort
     */
    public static void comparator(int[] arr) {
        Arrays.sort(arr);
    }

    /**
     * generate random array
     */
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        return arr;
    }


    /**
     * equals arrays
     */
    public static boolean isEqual(int[] arr1, int[] arr2) {
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
            return false;
        }
        if (arr1 == null) {
            return true;
        }
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * print array
     */
    public static void printArray(int[] arr) {

        System.out.println(Arrays.toString(arr));
    }

}
