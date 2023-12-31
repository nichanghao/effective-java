package net.sunday.basic.algorithm.sort;

import net.sunday.basic.algorithm.sort.util.SortUtils;

/**
 * 基数排序
 */
public class RadixSort {
    public static void radixSort(int[] arr) {
        if (arr == null || arr.length < 2) return;

        radixSort(arr, 0, arr.length - 1, maxBits(arr));
    }

    private static void radixSort(int[] arr, int left, int right, int digits) {
        int[] bucket = new int[right - left + 1];

        for (int i = 1; i <= digits; i++) {
            int[] counts = new int[10];

            for (int j = left; j <= right; j++) {
                counts[getDigit(arr[j], i)]++;
            }
            // 前缀和数组
            for (int j = 1; j < counts.length; j++) {
                counts[j] += counts[j - 1];
            }

            for (int j = right; j >= left; j--) {
                bucket[--counts[getDigit(arr[j], i)]] = arr[j];
            }

            for (int j = left, k = 0; j <= right; j++, k++) {
                arr[j] = bucket[k];
            }

        }

    }

    /**
     * 获取指定位数上的数字
     */
    private static int getDigit(int num, int d) {
        return num / (int) Math.pow(10, d - 1) % 10;
    }

    /**
     * 获取数组中最大值的十进制位数
     */
    private static int maxBits(int[] arr) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            max = Math.max(arr[i], max);
        }

        int res = 0;
        while (max != 0) {
            max /= 10;
            res++;
        }

        return res;
    }


    /**
     * for test
     */
    private static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random());
        }
        return arr;
    }

    /**
     * for test
     */
    private static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i];
        }
        return res;
    }

    /**
     * for test
     */
    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100000;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            radixSort(arr1);
            SortUtils.comparator(arr2);
            if (!SortUtils.isEqual(arr1, arr2)) {
                succeed = false;
                SortUtils.printArray(arr1);
                SortUtils.printArray(arr2);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");

        int[] arr = generateRandomArray(maxSize, maxValue);
        SortUtils.printArray(arr);
        radixSort(arr);
        SortUtils.printArray(arr);

    }
}
