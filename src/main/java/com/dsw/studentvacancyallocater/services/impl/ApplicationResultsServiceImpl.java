package com.dsw.studentvacancyallocater.services.impl;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.pattern.Patterns;
import akka.util.Timeout;
import com.dsw.studentvacancyallocater.actors.ApplicationResultsActor;
import com.dsw.studentvacancyallocater.configs.SpringProps;
import com.dsw.studentvacancyallocater.models.ApplicationResult;
import com.dsw.studentvacancyallocater.repositories.ApplicationResultsRepository;
import com.dsw.studentvacancyallocater.services.iface.ApplicationResultsService;
import com.dsw.studentvacancyallocater.utilities.DTOToModelConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.util.List;
import java.util.concurrent.TimeoutException;

@Service
public class ApplicationResultsServiceImpl implements ApplicationResultsService {

    @Autowired
    private ActorSystem actorSystem;

    @Autowired
    private DTOToModelConvertor dtoToModelConvertor;

    @Autowired
    private ApplicationResultsRepository applicationResultsRepository;


    @Override
    public List<ApplicationResult> getBySchoolId(long schoolId) {
        ActorRef applicationsResultsActor = actorSystem.actorOf(SpringProps.create(actorSystem, ApplicationResultsActor.class));
        ApplicationResultsActor.GetBySchoolId getBySchoolId = new ApplicationResultsActor.GetBySchoolId(schoolId);
        Timeout timeout = new Timeout(Duration.create(5, "seconds"));
        Future<Object> future = Patterns.ask(applicationsResultsActor, getBySchoolId, timeout);
        try {
            return (List<ApplicationResult>) Await.result(future, timeout.duration());
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ApplicationResult> getByStudentId(long studentId) {
        ActorRef applicationsResultsActor = actorSystem.actorOf(SpringProps.create(actorSystem, ApplicationResultsActor.class));
        ApplicationResultsActor.GetByStudentId getByStudentId = new ApplicationResultsActor.GetByStudentId(studentId);
        Timeout timeout = new Timeout(Duration.create(5, "seconds"));
        Future<Object> future = Patterns.ask(applicationsResultsActor, getByStudentId, timeout);
        try {
            return (List<ApplicationResult>) Await.result(future, timeout.duration());
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ApplicationResult getByStudentIdAndSchoolId(long studentId, long schoolId) {
        ActorRef applicationsResultsActor = actorSystem.actorOf(SpringProps.create(actorSystem, ApplicationResultsActor.class));
        ApplicationResultsActor.GetByStudentIdAndSchoolId getByStudentIdAndSchoolId = new ApplicationResultsActor.GetByStudentIdAndSchoolId(studentId, schoolId);
        Timeout timeout = new Timeout(Duration.create(5, "seconds"));
        Future<Object> future = Patterns.ask(applicationsResultsActor, getByStudentIdAndSchoolId, timeout);
        try {
            return (ApplicationResult) Await.result(future, timeout.duration());
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
