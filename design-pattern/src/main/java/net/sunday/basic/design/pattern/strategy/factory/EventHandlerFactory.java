package net.sunday.basic.design.pattern.strategy.factory;

import net.sunday.basic.design.pattern.strategy.event.EventBaseHandler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 事件管理工厂
 */
@Service
public class EventHandlerFactory implements InitializingBean, ApplicationContextAware {

    private final List<EventBaseHandler> eventBaseHandlerList = new ArrayList<>();

    private ApplicationContext applicationContext;


    public EventBaseHandler getEventHandler(Integer type) {
        for (EventBaseHandler eventBaseHandler : eventBaseHandlerList) {
            if (eventBaseHandler.accept(type)) {
                return eventBaseHandler;
            }
        }

        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        eventBaseHandlerList.addAll(applicationContext.getBeansOfType(EventBaseHandler.class).values());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
