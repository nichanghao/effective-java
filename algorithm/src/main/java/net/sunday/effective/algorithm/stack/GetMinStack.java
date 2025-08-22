package net.sunday.effective.algorithm.stack;

import java.util.Stack;

import static net.sunday.effective.algorithm.util.AssertUtils.assertTrue;

/**
 * 实现一个特殊的栈，在实现栈的基本功能的基础上，再实现返回栈中最小元素的操作。
 * 【要求】
 * 1．pop、push、getMin操作的时间复杂度都是O(1)。
 * 2．设计的栈类型可以使用现成的栈结构。
 */
public class GetMinStack {

    private final Stack<Integer> dataStack;
    private final Stack<Integer> minStack;

    public GetMinStack() {
        dataStack = new Stack<>();
        minStack = new Stack<>();
    }

    /**
     * 向栈中添加元素
     */
    public void push(Integer num) {
        dataStack.push(num);
        if (minStack.isEmpty()) {
            minStack.push(num);
        } else {
            minStack.push(num > minStack.peek() ? minStack.peek() : num);
        }
    }

    /**
     * 从栈中取出元素
     */
    public Integer pop() {
        if (dataStack.isEmpty()) {
            return null;
        }

        minStack.pop();
        return dataStack.pop();
    }

    /**
     * 获取最小值
     */
    public Integer getMin() {
        return minStack.peek();
    }

    public static void main(String[] args) {

        GetMinStack getMinStack = new GetMinStack();
        getMinStack.push(3);
        assertTrue(getMinStack.getMin() == 3);

        getMinStack.push(4);
        assertTrue(getMinStack.getMin() == 3);

        getMinStack.push(1);
        assertTrue(getMinStack.getMin() == 1);

        System.out.println(getMinStack.pop());
        assertTrue(getMinStack.getMin() == 3);
    }


}
