package net.sunday.effective.algorithm.queue;

import java.util.Stack;

import static net.sunday.effective.algorithm.util.AssertUtils.assertThrows;
import static net.sunday.effective.algorithm.util.AssertUtils.assertTrue;

/**
 * 利用2个栈模拟队列
 */
public class TwoStackQueue<E> {

    private final Stack<E> pushStack;

    private final Stack<E> popStack;

    public TwoStackQueue() {
        pushStack = new Stack<>();
        popStack = new Stack<>();
    }

    public void push(E e) {
        pushStack.push(e);
    }

    public E pop() {
        if (popStack.isEmpty()) {
            while (!pushStack.isEmpty()) {
                popStack.push(pushStack.pop());
            }
        }

        if (popStack.isEmpty()) {
            throw new NullPointerException();
        }

        return popStack.pop();
    }

    public E peek() {
        if (popStack.isEmpty()) {
            while (!pushStack.isEmpty()) {
                popStack.push(pushStack.pop());
            }
        }

        if (popStack.isEmpty()) {
            throw new NullPointerException();
        }

        return popStack.peek();
    }

    /**
     * for test
     */
    @SuppressWarnings("all")
    public static void main(String[] args) {
        TwoStackQueue<Integer> twoStackQueue = new TwoStackQueue<>();
        twoStackQueue.push(1);
        twoStackQueue.push(2);
        twoStackQueue.push(3);

        assertTrue(twoStackQueue.peek() == 1);
        assertTrue(twoStackQueue.pop() == 1);
        assertTrue(twoStackQueue.pop() == 2);
        assertTrue(twoStackQueue.pop() == 3);

        assertThrows(NullPointerException.class, () -> twoStackQueue.pop());
    }


}
