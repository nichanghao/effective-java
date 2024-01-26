package net.sunday.basic.thread.future;

import lombok.SneakyThrows;

import java.util.concurrent.*;

public class FutureTaskTest {

    public static void main(String[] args) {
        // 通过线程池的方式
        byThreadPool();

        // future task
        byFutureTask();
    }

    @SneakyThrows
    private static void byFutureTask() {
        FutureTask<String> task = new FutureTask<>(() -> {
            Thread.sleep(2000);
            return Thread.currentThread().getName() + "执行完毕";
        });
        new Thread(task).start();
        //从Future中取得返回值
        System.out.println("FutureTask任务的返回值：" + task.get());
    }

    @SneakyThrows
    private static void byThreadPool() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Future<String> future = executorService.submit(() -> {
            Thread.sleep(2000);
            return Thread.currentThread().getName() + "执行完毕";
        });

        //从Future中取得返回值
        System.out.println("ThreadPool任务的返回值：" + future.get());

        executorService.shutdown();
    }
}
