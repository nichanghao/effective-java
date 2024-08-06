package net.sunday.effective.design.pattern.chain.handler;


import net.sunday.effective.design.pattern.chain.executor.MedalExecutor;

public interface BaseMedalHandler {

    void handle(MedalExecutor executor);
}
