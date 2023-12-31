package net.sunday.basic.design.pattern.strategy.event;

import org.springframework.stereotype.Component;

/**
 * 执行事件的handler
 */
@Component
public abstract class EventBaseHandler {

    public void executeEvent() {
        System.out.println("查询事件规则...");

        this.doExecuteEvent();

        System.out.println("保存事件执行结果...");
    }

    protected abstract void doExecuteEvent();


    public boolean accept(Integer type) {
        return false;
    }
}
