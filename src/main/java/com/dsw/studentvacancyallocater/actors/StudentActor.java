package com.dsw.studentvacancyallocater.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.dsw.studentvacancyallocater.annotations.Actor;
import com.dsw.studentvacancyallocater.configs.SpringProps;
import com.dsw.studentvacancyallocater.enums.NotificationStatus;
import com.dsw.studentvacancyallocater.models.School;
import com.dsw.studentvacancyallocater.models.Student;
import com.dsw.studentvacancyallocater.models.StudentNotification;
import com.dsw.studentvacancyallocater.repositories.ApplicationResultsRepository;
import com.dsw.studentvacancyallocater.repositories.SchoolRepository;
import com.dsw.studentvacancyallocater.repositories.StudentNotificationRepository;
import com.dsw.studentvacancyallocater.repositories.StudentRepository;
import com.dsw.studentvacancyallocater.utilities.StringManipulator;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

@Slf4j
@Actor
public class StudentActor extends AbstractActor {
    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ActorSystem actorSystem;

    @Autowired
    private ApplicationResultsRepository applicationResultsRepository;

    @Autowired
    private StudentNotificationRepository studentNotificationRepository;

    //actor state
    private Student student;

    //actor behavior

    void getById(GetById getById) {
        getSender().tell(studentRepository.findById(getById.getStudentId()), self());
    }

    void getAllStudents(GetAllStudents getAllStudents) {
        getSender().tell(studentRepository.findAll(), self());
    }

    void getStudentsBySchoolId(GetStudentsBySchoolId getStudentsBySchoolId) {
        getSender().tell(studentRepository.getStudentsBySchoolId(getStudentsBySchoolId.getSchoolId()), self());
    }

    @Transactional
    void updateStudent(UpdateStudent updateStudent) {
        studentRepository.save(updateStudent.getStudent());
    }

    void registerStudent(RegisterStudent registerStudent) {
        Student student = registerStudent.getStudent();

        log.info("--------------------------  Registered schools --------------------------------");
        log.info(ToStringBuilder.reflectionToString(student));

        registerStudent.getStudent().getSchools()
                .forEach(school -> {
                    log.info(ToStringBuilder.reflectionToString(school));
                    String narrative;
                    if (student.getUnits() <= school.getMaxUnitsAllowed()) {
                        narrative = "Grade seven Units of applicant are within selection criteria";

                        /** Check if there are still any places left*/
                        if (studentRepository.getNumOfAcceptedStudentsBySchoolId(school.getId()) < school.getApplicantCapacity()) {
                            StringManipulator.narrativeAppender(narrative, "Application successfully sent for processing. You will be notified when its complete.");
                        } else {
                            StringManipulator.narrativeAppender(narrative, "No places left, application unsuccessful.");
                        }

                    } else {
                        narrative = "Grade seven Units of applicant are outside selection criteria. ";
                        StringManipulator.narrativeAppender(narrative, "Application unsuccessful.");
                    }
                });


        //persist student
        Student persistedStudent = studentRepository.save(student);

        //send notification, registration successfully completed.
        ActorRef notificationActor = actorSystem.actorOf(SpringProps.create(actorSystem, StudentNotificationActor.class));
        StudentNotification notification = new StudentNotification();
        notification.setStudent(persistedStudent);
        notification.setNarrative("Student registered successfully. Registration awaiting processing.");
        notification.setStatus(NotificationStatus.CREATED.name());
        StudentNotificationActor.Notify notify = new StudentNotificationActor.Notify(notification);
        notificationActor.tell(notify, self());
    }

    //message types
    interface Command {
    }

    @Data
    public static class Register implements Command {
        private final Student student;
    }

    @Data
    public static class UpdateStudent implements Command {
        private final Student student;
        private final School school;
    }

    @Data
    public static class GetAllStudents implements Command {
    }

    @Data
    public static class GetById implements Command {
        private final long studentId;
    }

    @Data
    public static class GetStudentsBySchoolId implements Command {
        private final long schoolId;
    }

    @Data
    public static class AccessStudent implements Command {
        private final Student student;
    }


    @Data
    public static class RegisterStudent implements Command {
        private final Student student;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(UpdateStudent.class, this::updateStudent)
                .match(GetAllStudents.class, this::getAllStudents)
                .match(GetStudentsBySchoolId.class, this::getStudentsBySchoolId)
                .match(RegisterStudent.class, this::registerStudent)
                .match(GetById.class, this::getById)
                .build();
    }
}
