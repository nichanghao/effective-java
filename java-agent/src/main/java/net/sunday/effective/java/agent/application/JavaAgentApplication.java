package net.sunday.effective.java.agent.application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import net.sunday.effective.java.agent.entity.Person;

public class JavaAgentApplication {

    public static void main(String[] args) {

        final ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
        executorService.execute(() -> {
            System.out.println("Hello, World!");
        });


        System.out.println("[JavaAgentApplication] is running");
        Person person = new Person();
        System.out.println("[JavaAgentApplication] execute sayOK() method result: " + person.sayOK());
    }

}
