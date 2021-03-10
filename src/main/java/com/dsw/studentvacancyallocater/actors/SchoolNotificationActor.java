package com.dsw.studentvacancyallocater.actors;

import akka.actor.AbstractActor;
import com.dsw.studentvacancyallocater.annotations.Actor;
import com.dsw.studentvacancyallocater.models.SchoolNotification;
import com.dsw.studentvacancyallocater.repositories.SchoolNotificationRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Actor
@Slf4j
public class SchoolNotificationActor extends AbstractActor {

    @Autowired
    private SchoolNotificationRepository schoolNotificationRepository;

    //actor behavior
    void notify(Notify notify) {
        schoolNotificationRepository.save(notify.getNotification());
    }

    //message types
    interface Command {
    }

    @Data
    public static class Notify implements Command {
        private final SchoolNotification notification;
    }


    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Notify.class, this::notify)
                .build();
    }
}