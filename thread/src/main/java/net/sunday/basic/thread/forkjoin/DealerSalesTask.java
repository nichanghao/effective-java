package net.sunday.basic.thread.forkjoin;

import java.util.concurrent.RecursiveTask;

/**
 * 经销商销量
 */
public class DealerSalesTask extends RecursiveTask<Long> {

    private static final long dealerSales = 100;

    private final long currSale;

    DealerSalesTask(long currSale) {
        this.currSale = currSale;
    }

    @Override
    protected Long compute() {
        TerminalSalesTask task = new TerminalSalesTask(currSale);
        task.fork();
        return dealerSales + task.join();
    }
}
