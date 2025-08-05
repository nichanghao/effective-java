package net.sunday.effective.algorithm.basic;

import java.util.Arrays;

/**
 * 异或算法
 */
public class EORAlgorithm {

    /**
     * 一个数组有一种数出现了奇数次，其他种数都出现了偶数次，找出这个数
     */
    public static Integer getOnceTimeOddNum(int[] arr) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException();
        }

        int res = 0;
        for (int i = 0; i < arr.length; i++) {
            res ^= arr[i];
        }

        return res;
    }

    /**
     * 一个数组有2种数出现了奇数次，其他种数都出现了偶数次，找出这2个数
     */
    public static int[] getTwoTimeOddNum(int[] arr) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException();
        }

        int[] res = new int[2];

        int xor = 0;
        for (int i = 0; i < arr.length; i++) {
            xor ^= arr[i];
        }

        // xor = a ^ b, mask = 最右侧二进制为1的数
        int mask = xor & (~xor + 1);
        for (int i = 0; i < arr.length; i++) {
            if ((arr[i] & mask) == 0) {
                res[0] ^= arr[i];
            }
        }

        res[1] = xor ^ res[0];

        return res;
    }

    public static void main(String[] args) {
        int[] arr1 = {1, 1, 2, 2, 3, 3, 4, 4, 4, 5, 5, 5, 5};
        System.out.println(getOnceTimeOddNum(arr1));

        int[] arr2 = {1, 1, 2, 2, 3, 3, 3, 4, 4, 5, 5, 5, 6, 6, 6, 6};
        System.out.println(Arrays.toString(getTwoTimeOddNum(arr2)));
    }

}
