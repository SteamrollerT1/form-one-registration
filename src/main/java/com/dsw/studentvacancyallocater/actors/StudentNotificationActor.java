package com.dsw.studentvacancyallocater.actors;

import akka.actor.AbstractActor;
import com.dsw.studentvacancyallocater.annotations.Actor;
import com.dsw.studentvacancyallocater.models.StudentNotification;
import com.dsw.studentvacancyallocater.repositories.StudentNotificationRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Actor
@Slf4j
public class StudentNotificationActor extends AbstractActor {

    @Autowired
    private StudentNotificationRepository studentNotificationRepository;

    //actor behavior
    void notify(Notify notify) {
        studentNotificationRepository.save(notify.getNotification());
    }

    void getNotification(GetNotification getNotification) {
        sender().tell(studentNotificationRepository.findById(getNotification.getId()).get(), self());
    }

    //message types
    interface Command {
    }

    @Data
    public static class Notify implements Command {
        private final StudentNotification notification;
    }


    @Data
    public static class GetNotification implements Command {
        private final long id;
    }


    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Notify.class, this::notify)
                .match(GetNotification.class, this::getNotification)
                .build();
    }
}