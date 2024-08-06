package net.sunday.effective.design.pattern.chain.handler;


import net.sunday.effective.design.pattern.chain.executor.MedalExecutor;

/**
 * 订单数量处理
 */
public class OrderCountMedalHandler implements OrderMedalHandler {


    @Override
    public void handle(MedalExecutor executor) {
        System.out.println("OrderCountMedalHandler handle ...");

        executor.proceed();
    }
}
