package net.sunday.basic.design.pattern.chain.executor;


import net.sunday.basic.design.pattern.chain.handler.BaseMedalHandler;

import java.util.List;

public class MedalExecutor {

    private final List<BaseMedalHandler> handlerChainList;

    private int i = 0;


    public MedalExecutor(List<BaseMedalHandler> handlerChainList) {
        this.handlerChainList = handlerChainList;
    }


    public void proceed() {
        if (i == handlerChainList.size()) {
            return;
        }
        BaseMedalHandler handler = handlerChainList.get(i++);
        handler.handle(this);
    }

}
