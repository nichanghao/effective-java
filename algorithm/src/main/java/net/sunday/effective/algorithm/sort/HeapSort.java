package net.sunday.effective.algorithm.sort;


import net.sunday.effective.algorithm.util.SortUtils;

/**
 * 堆排序
 * <p>
 * 使用数组模拟堆结构：当前下标为i，则其左子节点为 2*i+1, 右子节点为 2*i+2, 父节点为 (i-1)/2
 */

public class HeapSort {

    public static void heapSort(int[] arr) {
        if (arr == null || arr.length <= 1)
            return;

        for (int i = 1; i < arr.length; i++) {
            heapInsert(arr, i);
        }

        int limit = arr.length - 1;
        SortUtils.swap(arr, 0, limit);

        //每次弹出根元素，并再次调整堆
        while (limit > 1) {
            heapify(arr, 0, limit--);
            SortUtils.swap(arr, 0, limit);
        }

    }

    /**
     * 向下调整大根堆
     *
     * @param curr  当前下标
     * @param limit 数组长度
     */
    @SuppressWarnings("SameParameterValue")
    private static void heapify(int[] arr, int curr, int limit) {
        int left;
        while ((left = curr * 2 + 1) < limit) {
            int largest = left + 1 < limit && arr[left + 1] > arr[left] ? left + 1 : left;
            largest = arr[curr] < arr[largest] ? largest : curr;

            //最大值为自己时，不需要再向下调整
            if (largest == curr)
                break;

            SortUtils.swap(arr, curr, largest);
            curr = largest;
        }
    }

    /**
     * 新加入一个索引为i的数，以此调整为大根堆
     */
    private static void heapInsert(int[] arr, int i) {
        int parent;
        while ((parent = (i - 1) / 2) >= 0 && arr[parent] < arr[i]) {
            SortUtils.swap(arr, i, parent);
            i = parent;
        }
    }

    public static void main(String[] args) {
        SortUtils.testSort(HeapSort::heapSort);
    }
}
