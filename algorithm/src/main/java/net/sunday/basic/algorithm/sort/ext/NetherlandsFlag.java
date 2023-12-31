package net.sunday.basic.algorithm.sort.ext;

import net.sunday.basic.algorithm.sort.util.SortUtils;

/**
 * 荷兰国旗问题（快排）
 * 给定一个数组arr，和一个数num，请把小于num的数放在数组的左边，
 * 等于num的数放在数组的中间，大于num的数放在数组的右边。
 * 要求额外空间复杂度O(1)，时间复杂度O(N)
 */
public class NetherlandsFlag {

    public static int[] partition(int[] arr, int left, int right, int num) {
        int less = left - 1, more = right + 1, curr = left;
        while (curr < more) {
            if (arr[curr] < num) {
                SortUtils.swap(arr, curr++, ++less);
            } else if (arr[curr] > num) {
                SortUtils.swap(arr, --more, curr);
            } else {
                curr++;
            }
        }

        return new int[]{less + 1, more - 1};
    }

    public static void main(String[] args) {
        int[] test = generateArray();

        SortUtils.printArray(test);
        int[] res = partition(test, 0, test.length - 1, 1);
        SortUtils.printArray(test);
        System.out.println(res[0]);
        System.out.println(res[1]);

    }

    private static int[] generateArray() {
        int[] arr = new int[10];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * 3);
        }
        return arr;
    }
}