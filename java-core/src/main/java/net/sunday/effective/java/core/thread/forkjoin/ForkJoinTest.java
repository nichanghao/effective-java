package net.sunday.effective.java.core.thread.forkjoin;

import lombok.AllArgsConstructor;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ForkJoinTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool(10);

        // 准备数据
        int[] arrays = new int[10000];
        for (int i = 0; i < arrays.length; i++) {
            arrays[i] = i + 1;
        }

        // 提交任务
        ForkJoinTask<Long> submit = forkJoinPool.submit(new SumTask(arrays, 0, arrays.length));
        System.out.println(submit.get());

        forkJoinPool.close();
    }


    /**
     * ForkJoinTask：表示一个可以在 ForkJoinPool 中执行的任务: RecursiveTask（有返回值）、 RecursiveAction（无返回值）
     */
    @AllArgsConstructor
    static class SumTask extends RecursiveTask<Long> {

        // 子任务的拆分大小
        private static final int THRESHOLD = 1000;

        // 任务执行的具体数据
        private final int[] arrays;
        private final int start;
        private final int end;

        @Override
        protected Long compute() {
            if (end - start <= THRESHOLD) {
                // 拆分到任务足够小时，直接计算
                long sum = 0;
                for (int i = start; i < end; i++) {
                    sum += arrays[i];
                }

                System.out.println("sum from " + start + " to " + end + " is " + sum);
                return sum;
            } else {
                // 拆分任务
                int mid = (start + end) >> 1;

                SumTask leftTask = new SumTask(arrays, start, mid);
                SumTask rightTask = new SumTask(arrays, mid + 1, end);

                // 并行执行子任务
                leftTask.fork();
                rightTask.fork();

                // 等待子任务执行完毕，并返回结果
                return leftTask.join() + rightTask.join();
            }

        }
    }
}
