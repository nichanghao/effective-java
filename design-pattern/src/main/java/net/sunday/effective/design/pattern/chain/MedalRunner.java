package net.sunday.effective.design.pattern.chain;


import net.sunday.effective.design.pattern.chain.executor.MedalExecutor;
import net.sunday.effective.design.pattern.chain.handler.BaseMedalHandler;
import net.sunday.effective.design.pattern.chain.handler.OrderCountMedalHandler;
import net.sunday.effective.design.pattern.chain.handler.OrderMoneyMedalHandler;

import java.util.ArrayList;
import java.util.List;

public class MedalRunner {

    public static void main(String[] args) {
        List<BaseMedalHandler> handlerChainList = new ArrayList<>();
        handlerChainList.add(new OrderCountMedalHandler());
        handlerChainList.add(new OrderMoneyMedalHandler());

        new MedalExecutor(handlerChainList).proceed();
    }
}
