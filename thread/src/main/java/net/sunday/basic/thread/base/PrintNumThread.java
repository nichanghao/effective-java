package net.sunday.basic.thread.base;

/**
 * 多线程交替打印数字
 */
public class PrintNumThread {

    private int num = 0;

    public void print(int threadNo) {
        while (num < 99) {
            synchronized (PrintNumThread.class) {
                while (num % 3 != threadNo) {
                    try {
                        PrintNumThread.class.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                System.out.println("[thread name]: " + Thread.currentThread().getName() + ", print num: " + num++);
                PrintNumThread.class.notifyAll();
            }
        }
    }

    public static void main(String[] args) {
        PrintNumThread pr = new PrintNumThread();
        new Thread(() -> pr.print(0)).start();
        new Thread(() -> pr.print(1)).start();
        new Thread(() -> pr.print(2)).start();

    }


}
