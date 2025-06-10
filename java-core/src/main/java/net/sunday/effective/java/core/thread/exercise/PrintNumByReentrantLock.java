package net.sunday.effective.java.core.thread.exercise;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 多线程交替打印0-100数字，使用 ReentrantLock 方式实现
 */
public class PrintNumByReentrantLock {

    private volatile int num = 0;

    private static final ReentrantLock lock = new ReentrantLock();

    /**
     * 声明三个 Condition 条件变量，分别对应三个线程的打印条件
     */
    private static final Condition[] conditions = {lock.newCondition(), lock.newCondition(), lock.newCondition()};

    public void printNum(int threadNo) {
        if (threadNo == 0) {
            Thread.yield();
        }

        while (num <= 100) {
            lock.lock();

            try {
                if (num % 3 != threadNo) {
                    try {
                        conditions[threadNo].await();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                if (num <= 100) {
                    System.out.println("[thread name]: " + Thread.currentThread().getName() + ", print num: " + num);
                }
                num++;

                // 通知下一个线程打印
                conditions[(threadNo + 1) % 3].signal();

            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        PrintNumByReentrantLock pr = new PrintNumByReentrantLock();
        new Thread(() -> pr.printNum(0)).start();
        new Thread(() -> pr.printNum(1)).start();
        new Thread(() -> pr.printNum(2)).start();
    }

}
