package net.sunday.design.pattern.chain.handler;


import net.sunday.design.pattern.chain.executor.MedalExecutor;

public interface BaseMedalHandler {

    void handle(MedalExecutor executor);
}
