package com.dsw.studentvacancyallocater.services.impl;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.pattern.Patterns;
import akka.util.Timeout;
import com.dsw.studentvacancyallocater.actors.DeadlineActor;
import com.dsw.studentvacancyallocater.actors.StudentActor;
import com.dsw.studentvacancyallocater.configs.SpringProps;
import com.dsw.studentvacancyallocater.dtos.DeadlineDTO;
import com.dsw.studentvacancyallocater.models.Deadline;
import com.dsw.studentvacancyallocater.models.Student;
import com.dsw.studentvacancyallocater.repositories.DeadlineRepository;
import com.dsw.studentvacancyallocater.services.iface.DeadlineService;
import com.dsw.studentvacancyallocater.utilities.DTOToModelConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.util.List;
import java.util.concurrent.TimeoutException;

@Service
public class DeadlineServiceImpl implements DeadlineService {

    @Autowired
    private ActorSystem actorSystem;

    @Autowired
    private DTOToModelConvertor dtoToModelConvertor;

    @Autowired
    private DeadlineRepository deadlineRepository;

    @Override
    public Deadline openDeadlinePeriod(DeadlineDTO dto) {
        return null;
    }

    @Override
    public Deadline closeDeadlinePeriod(DeadlineDTO dto) {
        return null;
    }

    @Override
    public Deadline getBySchoolIdSync(Long schoolId) {
        return deadlineRepository.getBySchoolId(schoolId);
    }

    @Override
    public Deadline getByIdSync(Long id) {
        return deadlineRepository.getById(id);
    }

    @Override
    public Deadline getBySchoolIdAsync(Long schoolId) {
        ActorRef deadlineActor = actorSystem.actorOf(SpringProps.create(actorSystem, DeadlineActor.class));
        DeadlineActor.GetDeadlineBySchoolId getDeadlineBySchoolId = new DeadlineActor.GetDeadlineBySchoolId(schoolId);
        Timeout timeout = new Timeout(Duration.create(5, "seconds"));
        Future<Object> future = Patterns.ask(deadlineActor, getDeadlineBySchoolId, timeout);
        try {
            return (Deadline) Await.result(future, timeout.duration());
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Deadline getByIdAsync(Long id) {
        ActorRef deadlineActor = actorSystem.actorOf(SpringProps.create(actorSystem, DeadlineActor.class));
        DeadlineActor.GetDeadline getDeadline = new DeadlineActor.GetDeadline(id);
        Timeout timeout = new Timeout(Duration.create(5, "seconds"));
        Future<Object> future = Patterns.ask(deadlineActor, getDeadline, timeout);
        try {
            return (Deadline) Await.result(future, timeout.duration());
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
