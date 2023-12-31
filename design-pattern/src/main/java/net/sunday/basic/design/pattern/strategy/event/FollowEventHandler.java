package net.sunday.basic.design.pattern.strategy.event;

import org.springframework.stereotype.Component;

/**
 * 执行 关注
 */
@Component
public class FollowEventHandler extends EventBaseHandler {

    @Override
    protected void doExecuteEvent() {
        System.out.println("执行关注...");
    }

    @Override
    public boolean accept(Integer type) {
        return type == 1;
    }
}
