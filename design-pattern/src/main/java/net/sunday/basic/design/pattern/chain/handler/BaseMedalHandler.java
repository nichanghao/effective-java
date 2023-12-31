package net.sunday.basic.design.pattern.chain.handler;


import net.sunday.basic.design.pattern.chain.executor.MedalExecutor;

public interface BaseMedalHandler {

    void handle(MedalExecutor executor);
}
