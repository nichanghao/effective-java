package net.sunday.basic.algorithm.sort.ext;

/**
 * 小和问题（归并排序）
 * 在一个数组中，每一个数左边比当前数小的数累加起来，叫做这个数组的小和。求一个数组的小和。
 * 例子：
 * [1,3,4,2,5]
 * 1左边比1小的数，没有；
 * 3左边比3小的数，1；
 * 4左边比4小的数，1、3；
 * 2左边比2小的数，1；
 * 5左边比5小的数，1、3、4、2；
 * 所以小和为1+1+3+1+1+3+4+2=16
 */
public class SmallSum {

    public static int getSmallSum(int[] arr) {
        if (arr == null || arr.length <= 1) {
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
        int p1 = left, p2 = mid + 1, i = 0, res = 0;
        while (p1 <= mid && p2 <= right) {
            res += arr[p1] < arr[p2] ? arr[p1] * (right - p2 + 1) : 0;
            help[i++] = arr[p1] < arr[p2] ? arr[p1++] : arr[p2++];
        }

        while (p1 <= mid) {
            help[i++] = arr[p1++];
        }

        while (p2 <= right) {
            help[i++] = arr[p2++];
        }

        for (i = 0; i < help.length; i++) {
            arr[left + i] = help[i];
        }
        return res;
    }

    public static void main(String[] args) {
        int[] arr = {1, 3, 4, 2, 5};
        System.out.println(getSmallSum(arr));
    }
}
