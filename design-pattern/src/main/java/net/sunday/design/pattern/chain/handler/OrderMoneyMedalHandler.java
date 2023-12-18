package net.sunday.design.pattern.chain.handler;


import net.sunday.design.pattern.chain.executor.MedalExecutor;

/**
 * 订单金额处理
 */
public class OrderMoneyMedalHandler implements OrderMedalHandler {
    @Override
    public void handle(MedalExecutor executor) {
        System.out.println("OrderMoneyMedalHandler handle ...");

        executor.proceed();
    }
}
