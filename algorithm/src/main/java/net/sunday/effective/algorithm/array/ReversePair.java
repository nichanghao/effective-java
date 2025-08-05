package net.sunday.effective.algorithm.array;

/**
 * 逆序对问题(归并排序)
 * 在一个数组中，左边的数如果比右边的数大，则这两个数构成一个逆序对，请打印所有逆序对。
 */
public class ReversePair {

    public static int getReversePairs(int[] arr) {
        if (arr == null || arr.length < 2)
            return 0;

        return getReversePairs(arr, 0, arr.length - 1);
    }

    private static int getReversePairs(int[] arr, int left, int right) {
        if (left >= right)
            return 0;

        int mid = left + ((right - left) >> 1);
        return getReversePairs(arr, left, mid)
                + getReversePairs(arr, mid + 1, right)
                + merge(arr, left, mid, right);
    }

    private static int merge(int[] arr, int left, int mid, int right) {
        int[] temp = new int[right - left + 1];
        int p1 = left, p2 = mid + 1, p = 0, res = 0;

        while (p1 <= mid && p2 <= right) {
            if (arr[p1] > arr[p2]) {
                for (int k = p1; k <= mid; k++) {
                    System.out.println("reverse pair: [" + arr[k] + ", " + arr[p2] + "]");
                }
                res += mid - p1 + 1;

                temp[p++] = arr[p2++];
            } else {
                temp[p++] = arr[p1++];
            }
        }

        while (p1 <= mid) {
            temp[p++] = arr[p1++];
        }
        while (p2 <= right) {
            temp[p++] = arr[p2++];
        }

        System.arraycopy(temp, 0, arr, left, temp.length);

        return res;
    }

    public static void main(String[] args) {
        int[] arr = {3, 1, 5, 0, 2, 3};
        // 7个
        System.out.println("reverse pairs: " + getReversePairs(arr));
    }


}
