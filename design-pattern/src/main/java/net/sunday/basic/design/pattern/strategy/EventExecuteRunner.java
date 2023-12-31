package net.sunday.basic.design.pattern.strategy;


import net.sunday.basic.design.pattern.strategy.factory.EventHandlerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class EventExecuteRunner {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("net.sunday.basic.design.pattern.strategy");
        final EventHandlerFactory factory = context.getBean(EventHandlerFactory.class);

        factory.getEventHandler(1).executeEvent();
        factory.getEventHandler(2).executeEvent();
    }
}
