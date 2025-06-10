package net.sunday.effective.java.core.thread.exercise;

import java.util.concurrent.locks.LockSupport;

/**
 * 多线程交替打印0-100数字，使用 LockSupport 方式实现
 */
public class PrintNumByLockSupport {

    private static final int MAX_NUM = 100;

    private static int num = 0;

    private static final Thread[] threads = new Thread[3];

    public void printNum(int threadNo) {

        if (threadNo == 0) {
            Thread.yield();
        }

        while (num <= MAX_NUM) {

            if (num % threads.length != threadNo) {
                LockSupport.park();
            }

            // 注意 LockSupport.park() 时不会释放锁资源，不需要在同步代码块中调用
            if (num <= MAX_NUM) {
                System.out.println("[thread name]: " + Thread.currentThread().getName() + ", print num: " + num);
            }
            num++;

            // 唤醒下一个线程
            LockSupport.unpark(threads[num % threads.length]);

        }
    }

    public static void main(String[] args) throws InterruptedException {
        PrintNumByLockSupport pr = new PrintNumByLockSupport();

        threads[0] = new Thread(() -> pr.printNum(0));
        threads[1] = new Thread(() -> pr.printNum(1));
        threads[2] = new Thread(() -> pr.printNum(2));

        // 启动线程
        for (Thread thread : threads) {
            thread.start();
        }

        // 等待所有线程结束
        for (Thread thread : threads) {
            thread.join();
        }
    }

}
