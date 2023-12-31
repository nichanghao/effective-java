package net.sunday.basic.algorithm.sort.ext;

/**
 * 逆序对问题(归并排序)
 * 在一个数组中，左边的数如果比右边的数大，则这两个数构成一个逆序对，请打印所有逆序对。
 */
public class ReversePair {

    public static int reversePair(int[] arr) {
        if (null == arr || arr.length < 2) {
            return 0;
        }

        return mergeSort(arr, 0, arr.length - 1);
    }

    private static int mergeSort(int[] arr, int left, int right) {
        if (left >= right)
            return 0;

        int mid = left + ((right - left) >> 1);

        return mergeSort(arr, left, mid) + mergeSort(arr, mid + 1, right) + merge(arr, left, right, mid);
    }

    private static int merge(int[] arr, int left, int right, int mid) {
        int[] help = new int[right - left + 1];

        int p1 = left, p2 = mid + 1, p = 0, res = 0;
        while (p1 <= mid && p2 <= right) {
            if (arr[p1] > arr[p2]) {
                res += mid - p1 + 1;
                for (int i = p1; i <= mid; i++) {
                    System.out.println("逆序对：[" + arr[i] + "," + arr[p2] + "]");
                }
            }

            help[p++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
        }

        while (p1 <= mid) {
            help[p++] = arr[p1++];
        }

        while (p2 <= right) {
            help[p++] = arr[p2++];
        }

        System.arraycopy(help, 0, arr, left, help.length);

        return res;
    }

    public static void main(String[] args) {
        int[] arr = {3, 1, 5, 0, 2, 3};
        System.out.println("逆序对的个数：" + reversePair(arr));
    }
}
