package net.sunday.basic.thread.forkjoin;

import java.util.concurrent.RecursiveTask;

public class DealerSalesTask extends RecursiveTask<Long> {

    private static final long sale = 100;

    private long currSale;

    DealerSalesTask(long currSale) {
        this.currSale = currSale;
    }

    @Override
    protected Long compute() {
        TerminalSalesTask task = new TerminalSalesTask(currSale);
        task.fork();
        return sale + task.join();
    }
}
