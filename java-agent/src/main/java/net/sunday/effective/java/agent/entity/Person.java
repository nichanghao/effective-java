package net.sunday.effective.java.agent.entity;

public class Person {
    public String sayOK() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("executing person's sayOK()......");

        return "I'm ok";
    }

}
