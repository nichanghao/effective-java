package net.sunday.basic.thread.forkjoin;

import java.util.concurrent.RecursiveTask;

/**
 * 终端销量
 */

public class TerminalSalesTask extends RecursiveTask<Long> {

    private static final long terminalSales = 10;

    private final long currSale;

    TerminalSalesTask(long currSale) {
        this.currSale = currSale;
    }

    @Override
    protected Long compute() {
        return terminalSales + currSale;
    }
}
