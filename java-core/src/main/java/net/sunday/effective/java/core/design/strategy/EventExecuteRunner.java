package net.sunday.effective.java.core.design.strategy;


import net.sunday.effective.java.core.design.strategy.factory.EventHandlerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class EventExecuteRunner {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(EventExecuteRunner.class.getPackageName());
        final EventHandlerFactory factory = context.getBean(EventHandlerFactory.class);

        factory.getEventHandler(1).executeEvent();
        factory.getEventHandler(2).executeEvent();
    }
}
