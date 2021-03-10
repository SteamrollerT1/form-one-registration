package com.dsw.studentvacancyallocater.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.dsw.studentvacancyallocater.annotations.Actor;
import com.dsw.studentvacancyallocater.configs.SpringProps;
import com.dsw.studentvacancyallocater.enums.ApplicationStatus;
import com.dsw.studentvacancyallocater.models.ApplicationResult;
import com.dsw.studentvacancyallocater.models.School;
import com.dsw.studentvacancyallocater.models.Student;
import com.dsw.studentvacancyallocater.repositories.ApplicationResultsRepository;
import com.dsw.studentvacancyallocater.repositories.SchoolRepository;
import com.dsw.studentvacancyallocater.repositories.StudentRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
@Actor
public class SchoolActor extends AbstractActor {
    @Autowired
    private SchoolRepository schoolRepository;
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ActorSystem actorSystem;

    @Autowired
    private ApplicationResultsRepository applicationResultsRepository;


    //actor behavior
    void registerSchool(RegisterSchool register) {
        schoolRepository.save(register.getSchool());
        //TODO Tell notification actor that school registered successfully
    }


    void getById(GetById getById) {
        School school = schoolRepository.findById(getById.getSchoolId()).get();
        getSender().tell(school, getSelf());
    }

    void getAll(GetAllSchools getAllSchools) {
        List<School> schools = schoolRepository.findAll();
        getSender().tell(schools, getSelf());
    }


    void updateSchool(UpdateSchool updateSchool) {
        schoolRepository.save(updateSchool.getSchool());
    }

    void rejectStudent(RejectedStudent rejectedStudent) {
        ActorRef applicationResultsActor = actorSystem.actorOf(SpringProps.create(actorSystem, ApplicationResultsActor.class));
        ApplicationResult applicationResult = new ApplicationResult();
        applicationResult.setStudent(rejectedStudent.getStudent());
        applicationResult.setSchool(rejectedStudent.getSchool());
        applicationResult.setNarrative(rejectedStudent.getNarrative());
        applicationResult.setStatus(ApplicationStatus.FAILED.name());
        ApplicationResultsActor.StoreApplicationResults storeApplicationResults = new ApplicationResultsActor.StoreApplicationResults(applicationResult);
        applicationResultsActor.tell(storeApplicationResults, self());
    }

    void acceptStudent(AcceptStudent acceptStudent) {
        ActorRef applicationResultsActor = actorSystem.actorOf(SpringProps.create(actorSystem, ApplicationResultsActor.class));
        ApplicationResult applicationResult = new ApplicationResult();
        applicationResult.setStudent(acceptStudent.getStudent());
        applicationResult.setSchool(acceptStudent.getSchool());
        applicationResult.setNarrative(acceptStudent.getNarrative());
        applicationResult.setStatus(ApplicationStatus.SUCCESSFUL.name());
        ApplicationResultsActor.StoreApplicationResults storeApplicationResults = new ApplicationResultsActor.StoreApplicationResults(applicationResult);
        applicationResultsActor.tell(storeApplicationResults, self());
    }


    //message types
    interface Command {
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static abstract class AbstractApplicationResult {
        protected School school;
        protected Student student;
        protected String narrative;
    }

    @Data
    public static class AcceptStudent extends AbstractApplicationResult implements Command {
        public AcceptStudent(School school, Student student, String narrative) {
            super(school, student, narrative);
        }
    }

    @Data
    public static class RejectedStudent extends AbstractApplicationResult implements Command {
        public RejectedStudent(School school, Student student, String narrative) {
            super(school, student, narrative);
        }
    }


    @Data
    public static class RegisterSchool implements Command {
        private final School school;
    }

    @Data
    public static class UpdateSchool implements Command {
        private final School school;
    }

    @Data
    public static class GetById implements Command {
        private final long schoolId;
    }


    public enum GetAllSchools implements Command {
        GET_ALL_SCHOOLS
    }


    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(RejectedStudent.class, this::rejectStudent)
                .match(GetById.class, this::getById)
                .match(GetAllSchools.class, this::getAll)
                .match(UpdateSchool.class, this::updateSchool)
                .match(RegisterSchool.class, this::registerSchool)
                .match(AcceptStudent.class, this::acceptStudent)
                .build();
    }
}
