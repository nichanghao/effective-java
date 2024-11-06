package net.sunday.effective.thread.exercise.printnum;

/**
 * 多线程交替打印0-100数字，使用 wait/notify 方式实现
 */
public class PrintNumByWaitNotify {

    private volatile int num = 0;

    public void printNum(int threadNo) {
        while (num <= 100) {
            synchronized (PrintNumByWaitNotify.class) {
                // 需要使用 while 多次进行判断，防止被 notifyAll 后，直接向下执行了
                while (num % 3 != threadNo) {
                    try {
                        PrintNumByWaitNotify.class.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                // 这里需要再次判断
                if (num <= 100) {
                    System.out.println("[thread name]: " + Thread.currentThread().getName() + ", print num: " + num);
                }

                num++;
                PrintNumByWaitNotify.class.notifyAll();
            }
        }
    }

    public static void main(String[] args) {
        PrintNumByWaitNotify pr = new PrintNumByWaitNotify();
        new Thread(() -> pr.printNum(0)).start();
        new Thread(() -> pr.printNum(1)).start();
        new Thread(() -> pr.printNum(2)).start();
    }
}
