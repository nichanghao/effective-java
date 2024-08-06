package net.sunday.effective.design.pattern.strategy.event;

import org.springframework.stereotype.Component;

/**
 * 执行 点赞
 */
@Component
public class LikeEventHandler extends EventBaseHandler {
    @Override
    protected void doExecuteEvent() {
        System.out.println("执行点赞...");
    }

    @Override
    public boolean accept(Integer type) {
        return type == 2;
    }
}
