package net.sunday.effective.algorithm.stack;

import java.util.LinkedList;
import java.util.Queue;

import static net.sunday.effective.algorithm.util.AssertUtils.assertTrue;

/**
 * 利用2个队列模拟栈
 */
public class TwoQueueStack<E> {

    private Queue<E> q1;
    private Queue<E> q2;

    public TwoQueueStack() {
        q1 = new LinkedList<>();
        q2 = new LinkedList<>();
    }

    public void push(E e) {
        q1.add(e);
    }

    public E pop() {
        if (q1.isEmpty()) {
            throw new NullPointerException();
        }

        while (q1.size() > 1) {
            q2.add(q1.poll());
        }

        E e = q1.poll();

        this.swap();
        return e;
    }

    public E peek() {
        if (q1.isEmpty()) {
            throw new NullPointerException();
        }

        while (q1.size() > 1) {
            q2.add(q1.poll());
        }

        E e = q1.poll();
        q2.add(e);

        this.swap();
        return e;
    }

    private void swap() {
        // swap q1 and q2
        Queue<E> temp = q1;
        q1 = q2;
        q2 = temp;
    }

    /**
     * for test
     */
    @SuppressWarnings("all")
    public static void main(String[] args) {

        TwoQueueStack<Integer> twoQueueStack = new TwoQueueStack<>();
        twoQueueStack.push(1);
        twoQueueStack.push(2);
        twoQueueStack.push(3);

        assertTrue(twoQueueStack.peek() == 3);
        assertTrue(twoQueueStack.pop() == 3);
        assertTrue(twoQueueStack.pop() == 2);
        assertTrue(twoQueueStack.pop() == 1);
    }
}
