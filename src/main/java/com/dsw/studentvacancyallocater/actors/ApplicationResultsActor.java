package com.dsw.studentvacancyallocater.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.dsw.studentvacancyallocater.annotations.Actor;
import com.dsw.studentvacancyallocater.configs.SpringProps;
import com.dsw.studentvacancyallocater.enums.NotificationStatus;
import com.dsw.studentvacancyallocater.models.ApplicationResult;
import com.dsw.studentvacancyallocater.models.SchoolNotification;
import com.dsw.studentvacancyallocater.models.StudentNotification;
import com.dsw.studentvacancyallocater.repositories.ApplicationResultsRepository;
import com.dsw.studentvacancyallocater.repositories.SchoolRepository;
import com.dsw.studentvacancyallocater.repositories.StudentRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@Actor
public class ApplicationResultsActor extends AbstractActor {
    @Autowired
    private SchoolRepository schoolRepository;
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ActorSystem actorSystem;

    @Autowired
    private ApplicationResultsRepository applicationResultsRepository;

    //actor behavior
    void storeApplicationResults(StoreApplicationResults applicationResults) {
        applicationResultsRepository.save(applicationResults.getApplicationResult());

        //send notification to student
        ActorRef studentNotificationActor = actorSystem.actorOf(SpringProps.create(actorSystem, StudentNotificationActor.class));
        StudentNotification studentNotification = new StudentNotification();
        studentNotification.setStudent(applicationResults.getApplicationResult().getStudent());
        studentNotification.setNarrative("You application results are now available.");
        studentNotification.setStatus(NotificationStatus.CREATED.name());
        StudentNotificationActor.Notify notifyStudent = new StudentNotificationActor.Notify(studentNotification);
        studentNotificationActor.tell(notifyStudent, self());

        //send notification to school
        ActorRef schoolNotificationActor = actorSystem.actorOf(SpringProps.create(actorSystem, SchoolNotificationActor.class));
        SchoolNotification schoolNotification = new SchoolNotification();
        schoolNotification.setSchool(applicationResults.getApplicationResult().getSchool());
        schoolNotification.setNarrative("Student results sent successfully");
        schoolNotification.setStatus(NotificationStatus.CREATED.name());
        SchoolNotificationActor.Notify notifySchool = new SchoolNotificationActor.Notify(schoolNotification);
        schoolNotificationActor.tell(notifySchool, self());
    }


    void getBySchoolId(GetBySchoolId getBySchoolId) {
        sender().tell(applicationResultsRepository.getBySchoolId(getBySchoolId.getSchoolId()), self());
    }


    void getByStudentId(GetByStudentId getByStudentId) {
        sender().tell(applicationResultsRepository.getByStudentId(getByStudentId.getStudentId()), self());
    }

    void getByStudentIdAndSchoolId(GetByStudentIdAndSchoolId getByStudentIdAndSchoolId) {
        sender().tell(applicationResultsRepository.getByStudentIdAndSchoolId(getByStudentIdAndSchoolId.getStudentId(), getByStudentIdAndSchoolId.getSchoolId()), self());
    }

    //message types
    interface Command {
    }

    @Data
    public static class StoreApplicationResults implements Command {
        private final ApplicationResult applicationResult;
    }

    @Data
    public static class GetBySchoolId implements Command {
        private final long schoolId;
    }

    @Data
    public static class GetByStudentId implements Command {
        private final long studentId;
    }

    @Data
    public static class GetByStudentIdAndSchoolId implements Command {
        private final long studentId;
        private final long schoolId;
    }


    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(StoreApplicationResults.class, this::storeApplicationResults)
                .match(GetBySchoolId.class, this::getBySchoolId)
                .match(GetByStudentId.class, this::getByStudentId)
                .match(GetByStudentIdAndSchoolId.class, this::getByStudentIdAndSchoolId)
                .build();
    }
}
