package net.sunday.effective.algorithm.array;

import java.util.HashMap;
import java.util.Map;

/**
 * 最长子数组的问题:
 * 给定一个数组arr，值可正，可负，可0；一个整数aim，求累加和等于aim的最长子数组，要求时间复杂度O(N)
 */
public class LongestSubArray {

    /**
     * 最长子数组的问题
     *
     * @param arr 数组
     * @param aim 累加的目标值
     * @return 累加和等于目标值的最长子数组
     */
    public static int getLongestSubArrayNum(int[] arr, int aim) {
        if (arr == null)
            return 0;

        // sum -> index
        Map<Integer, Integer> map = new HashMap<>();
        // 表示累计值为0的索引位置为 `-1` 达到 +1 的效果
        map.put(0, -1);

        int sum = 0, length = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];

            if (!map.containsKey(sum)) {
                map.put(sum, i);
            }
            if (map.containsKey(sum - aim)) {
                length = Math.max(length, i - map.get(sum - aim));
            }
        }

        return length;
    }

    public static void main(String[] args) {
        int[] arr = {-1, 0, 0, 1, 0, -6, 0, 7, 6};
        System.out.println(getLongestSubArrayNum(arr, 7));
    }


}
