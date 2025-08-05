package net.sunday.effective.algorithm.basic;

/**
 * 二分查找算法
 */
public class BinarySearch {

    /**
     * 在有序数组中查找某个数是否存在
     */
    public static boolean exist(int[] arr, int target) {
        if (arr == null || arr.length == 0) {
            return false;
        }

        int left = 0, right = arr.length - 1;
        while (left <= right) {
            int mid = left + ((right - left) >> 1);
            if (target > arr[mid]) {
                left = mid + 1;
            } else if (target < arr[mid]) {
                right = mid - 1;
            } else {
                return true;
            }
        }

        return false;
    }

    /**
     * 在有序数组中找出 = num 最左侧位置
     */
    public static int nearestIndex(int[] arr, int num) {
        int index = -1;
        if (arr == null || arr.length == 0)
            return index;

        int left = 0, right = arr.length - 1, mid;
        while (left <= right) {
            mid = left + ((right - left) >> 1);
            if (arr[mid] <= num) {
                index = mid;
                left = mid + 1;
            } else if (arr[mid] > num) {
                right = mid - 1;
            }
        }

        return index;
    }

    /**
     * 局部最小值问题
     * <pre>
     * 在一个无序数组中，已知相邻的数一定不相等，找出一个局部最小值的位置。
     *  1.对于0位置上的数，如果0位置上的数比1位置上的数小，则0位置为局部最小的位置
     *  2.对于n = arr.length - 1; 如果 n 上的位置比n-1上的位置小，则n位置为局部最小位置
     *  3.对于中间的位置mid，如果 mid < mid-1 && mid < mid + 1 ,则mid为局部最小的位置
     * </pre>
     */
    public static int getLessIndex(int[] arr) {
        if (arr == null || arr.length == 0) {
            return -1;
        }

        if (arr.length == 1 || arr[0] < arr[1]) {
            return 0;
        }
        if (arr[arr.length - 1] < arr[arr.length - 2]) {
            return arr.length - 1;
        }

        int left = 1, right = arr.length - 2, mid;
        while (left <= right) {
            mid = left + ((right - left) >> 1);
            if (arr[mid] > arr[mid - 1]) {
                right = mid - 1;
            } else if (arr[mid] > arr[mid + 1]) {
                left = mid + 1;
            } else {
                return mid;
            }
        }
        return left;
    }


    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6};
        System.out.println(exist(arr, 4));

        int[] arr1 = {1, 1, 2, 2, 2, 3, 3, 4, 7};
        System.out.println(nearestIndex(arr1, 3) == 6);

        int[] arr2 = {6, 5, 3, 4, 6, 7, 8};
        int index = getLessIndex(arr2);
        System.out.println("index: " + index + ", value: " + arr2[index]);
    }

}
