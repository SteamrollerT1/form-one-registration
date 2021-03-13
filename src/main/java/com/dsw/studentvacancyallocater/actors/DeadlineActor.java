package com.dsw.studentvacancyallocater.actors;

import akka.actor.AbstractActor;
import com.dsw.studentvacancyallocater.annotations.Actor;
import com.dsw.studentvacancyallocater.models.Deadline;
import com.dsw.studentvacancyallocater.repositories.DeadlineRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Actor
@Slf4j
public class DeadlineActor extends AbstractActor {

    @Autowired
    private DeadlineRepository deadlineRepository;

    //actor behavior
    void updateDeadLinePeriod(UpdateDeadlinePeriod updateDeadlinePeriod) {
        sender().tell(deadlineRepository.save(updateDeadlinePeriod.getDeadline()), self());

        //TODO send notification to school, deadline updated sccessfully
    }

    void getDeadline(GetDeadline getDeadline) {
        sender().tell(deadlineRepository.getById(getDeadline.getId()), self());
    }

    //message types
    interface Command {
    }

    @Data
    public static class UpdateDeadlinePeriod implements Command {
        private final Deadline deadline;
    }


    @Data
    public static class GetDeadline implements Command {
        private final long id;
    }


    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(UpdateDeadlinePeriod.class, this::updateDeadLinePeriod)
                .match(GetDeadline.class, this::getDeadline)
                .build();
    }
}