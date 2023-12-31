package net.sunday.basic.algorithm.bit;

import java.util.Arrays;

/**
 * 位运算
 */

public class BitAlgorithm {

    /**
     * 一个数组有一种数出现了奇数次，其他种数都出现了偶数次，找出这个数
     */
    public static Integer getOnceTimeOddNum(int[] arr) {
        if (arr == null || arr.length == 0)
            return null;

        int eor = 0;
        for (int i = 0; i < arr.length; i++) {
            eor ^= arr[i];
        }
        return eor;
    }

    /**
     * 一个数组有2种数出现了奇数次，其他种数都出现了偶数次，找出这2个数
     */
    public static Integer[] getTwoTimeOddNum(int[] arr) {
        if (arr == null || arr.length == 0)
            return null;

        Integer[] result = new Integer[2];

        int eor = 0;
        for (int i = 0; i < arr.length; i++) {
            eor ^= arr[i];
        }

        int rightOne = eor & (~eor + 1);
        result[0] = 0;
        for (int i = 0; i < arr.length; i++) {
            if ((rightOne & arr[i]) == 0) {
                result[0] ^= arr[i];
            }
        }

        result[1] = eor ^ result[0];

        return result;
    }

    public static void main(String[] args) {
        int[] arr1 = {1, 1, 2, 2, 3, 3, 4, 4, 4, 5, 5, 5, 5};
        System.out.println(getOnceTimeOddNum(arr1));

        int[] arr2 = {1, 1, 2, 2, 3, 3, 3, 4, 4, 5, 5, 5, 6, 6, 6, 6};
        System.out.println(Arrays.toString(getTwoTimeOddNum(arr2)));
    }

}
