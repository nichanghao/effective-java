package net.sunday.effective.algorithm.queue;


import static net.sunday.effective.algorithm.util.AssertUtils.assertThrows;
import static net.sunday.effective.algorithm.util.AssertUtils.assertTrue;

/**
 * 环形数组实现的队列
 */
public class ArrayQueue {

    private final int[] data;

    /**
     * 始终指向可以取出元素的位置
     */
    private int start;

    /**
     * 始终指可以放入元素的位置
     */
    private int end;

    /**
     * 元素的个数
     */
    private int size;

    public ArrayQueue(int capacity) {
        data = new int[capacity];
        size = 0;
        end = start = -1;
    }

    /**
     * 从队尾入队
     */
    public void push(int num) {
        if (size == data.length) {
            throw new RuntimeException("The queue is full");
        }

        data[(end = nextIndex(end))] = num;
        size++;
    }

    /**
     * 从队头出队
     */
    public int pop() {
        if (size == 0) {
            throw new RuntimeException("The queue is empty");
        }
        size--;
        return data[(start = nextIndex(start))];
    }

    /**
     * 查看队头元素
     */
    public int peek() {
        if (size == 0) {
            throw new RuntimeException("The queue is empty");
        }

        return data[nextIndex(start)];
    }

    /**
     * 实现环形数组
     */
    private int nextIndex(int curr) {
        if (++curr == data.length) {
            return 0;
        }
        return curr;
    }

    //for test
    @SuppressWarnings("all")
    public static void main(String[] args) {
        ArrayQueue arrayQueue = new ArrayQueue(3);
        arrayQueue.push(1);
        arrayQueue.push(2);
        arrayQueue.push(3);
        assertThrows("The queue is full", () -> arrayQueue.push(4));

        assertTrue(arrayQueue.peek() == 1);
        assertTrue(arrayQueue.pop() == 1);
        assertTrue(arrayQueue.pop() == 2);
        assertTrue(arrayQueue.pop() == 3);
        assertThrows("The queue is empty", () -> arrayQueue.pop());

    }

}
