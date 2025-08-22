package net.sunday.effective.algorithm.stack;

import static net.sunday.effective.algorithm.util.AssertUtils.assertTrue;

/**
 * 数组模拟栈
 */
public class ArrayStack {

    /**
     * 存放元素的数组
     */
    private final int[] data;

    /**
     * 指向栈顶的指针
     */
    private int curr;

    public ArrayStack(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("The capacity is less than zero");
        }
        data = new int[capacity];
        curr = 0;
    }

    /**
     * 向栈中添加元素
     */
    public void push(int num) {
        if (data.length == curr) {
            throw new IndexOutOfBoundsException("The stack is full");
        }

        data[curr++] = num;
    }


    /**
     * 从栈顶中弹出元素
     */
    public int pop() {
        if (curr == 0) {
            throw new NullPointerException("The stack is empty");
        }

        return data[--curr];
    }

    /**
     * 查看栈顶元素
     */
    public int peek() {
        if (curr == 0) {
            throw new NullPointerException("The stack is empty");
        }
        return data[curr - 1];
    }

    /**
     * for test
     */
    @SuppressWarnings("all")
    public static void main(String[] args) {

        ArrayStack arrayStack = new ArrayStack(3);
        arrayStack.push(1);
        arrayStack.push(2);
        arrayStack.push(3);

        assertTrue(arrayStack.peek() == 3);
        assertTrue(arrayStack.pop() == 3);
        assertTrue(arrayStack.pop() == 2);
        assertTrue(arrayStack.pop() == 1);

    }

}
