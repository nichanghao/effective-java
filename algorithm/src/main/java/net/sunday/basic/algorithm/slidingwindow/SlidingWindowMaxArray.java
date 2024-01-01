package net.sunday.basic.algorithm.slidingwindow;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Queue;

/**
 * 滑动窗口极值算法
 * 有一个整数数组arr和一个大小为w的窗口从数组的最左边滑到最右边，窗口每次向右边移动一个位置。
 * 例如，数组为[4,3,5,4,3,3,6,7],窗口大小为3时：
 * [4 3 5] 4 3 3 6 7    窗口中最大值为5
 * 4 [3 5 4] 3 3 6 7    窗口中最大值为5
 * 4 3 [5 4 3] 3 6 7    窗口中最大值为5
 * 4 3 5 [4 3 3] 6 7    窗口中最大值为4
 * 4 3 5 4 [3 3 6] 7    窗口中最大值为6
 * 4 3 5 4 3 [3 6 7]    窗口中最大值为7
 * 如果数组长度为n,窗口大小为w,则一共产生n-w+1个窗口最大值。
 * 请实现一个函数。
 * 输入：整型数组arr,窗口大小w.
 * 输出：一个长度为n-w+1的数组res,res[i]表示每一中窗口状态下的窗口最大值。
 * 如上例中，返回[5,5,5,4,6,7]
 */
public class SlidingWindowMaxArray {

    public static int[] getSlidingWindowMaxArray(int[] arr, int w) {
        if (arr == null || arr.length == 0)
            return null;

        if (w > arr.length) {
            w = arr.length;
        }

        int[] res = new int[arr.length - w + 1];
        int index = 0;

        Deque<Integer> deque = new ArrayDeque<>();
        for (int i = 0; i < arr.length; i++) {
            while (!deque.isEmpty() && arr[deque.peekLast()] <= arr[i]) {
                deque.pollLast();
            }

            deque.addLast(i);

            if (deque.peekFirst() == i - w) {
                deque.pollFirst();
            }

            if (i >= w - 1) {
                res[index++] = arr[deque.peekFirst()];
            }
        }

        return res;
    }

    //for test
    public static void main(String[] args) {
        int[] arr = {4, 3, 5, 4, 3, 3, 6, 7};
        arr = getSlidingWindowMaxArray(arr, 3);
        System.out.println(Arrays.toString(arr));
    }
}
