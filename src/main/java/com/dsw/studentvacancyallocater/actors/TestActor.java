package com.dsw.studentvacancyallocater.actors;

import akka.actor.AbstractActor;
import com.dsw.studentvacancyallocater.annotations.Actor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Actor
@Slf4j
public class TestActor extends AbstractActor {

    //actor state
    String var1, var2;

    //actor behavior
    void printMessage(Display display) {
        log.info(display.getMessage());
    }

    //message types
    interface Command {
    }

    @Data
    public static class Display implements Command {
        private final String message;

        public Display(String message) {
            this.message = message;
        }
    }


    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Display.class, this::printMessage)
                .build();
    }
}