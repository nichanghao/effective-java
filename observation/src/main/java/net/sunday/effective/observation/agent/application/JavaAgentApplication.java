package net.sunday.effective.observation.agent.application;

import net.sunday.effective.observation.agent.entity.Person;

public class JavaAgentApplication {

    public static void main(String[] args) {

        System.out.println("[JavaAgentApplication] is running");
        Person person = new Person();
        System.out.println("[JavaAgentApplication] execute sayOK() method result: " + person.sayOK());
    }

}
