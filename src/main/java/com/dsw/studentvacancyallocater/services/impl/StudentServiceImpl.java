package com.dsw.studentvacancyallocater.services.impl;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.pattern.Patterns;
import akka.util.Timeout;
import com.dsw.studentvacancyallocater.actors.StudentActor;
import com.dsw.studentvacancyallocater.configs.SpringProps;
import com.dsw.studentvacancyallocater.dtos.ResponseDTO;
import com.dsw.studentvacancyallocater.dtos.StudentDTO;
import com.dsw.studentvacancyallocater.models.Student;
import com.dsw.studentvacancyallocater.models.StudentNotification;
import com.dsw.studentvacancyallocater.repositories.StudentNotificationRepository;
import com.dsw.studentvacancyallocater.repositories.StudentRepository;
import com.dsw.studentvacancyallocater.services.iface.StudentService;
import com.dsw.studentvacancyallocater.utilities.DTOToModelConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.util.List;
import java.util.concurrent.TimeoutException;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private ActorSystem actorSystem;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentNotificationRepository studentNotificationRepository;

    @Autowired
    private DTOToModelConvertor dtoToModelConvertor;

    @Override
    public ResponseDTO registerStudent(StudentDTO dto) {
        ActorRef studentActor = actorSystem.actorOf(SpringProps.create(actorSystem, StudentActor.class));
        StudentActor.RegisterStudent registerStudent = new StudentActor.RegisterStudent(dtoToModelConvertor.dtoToStudent(dto));
        studentActor.tell(registerStudent, ActorRef.noSender());
        return new ResponseDTO("Student registration initiated. You will be notified when its complete.", "00");
    }


    @Override
    public Student getStudentByIdAsync(long studentId) {
        ActorRef schoolActor = actorSystem.actorOf(SpringProps.create(actorSystem, StudentActor.class));
        StudentActor.GetById getById = new StudentActor.GetById(studentId);
        Timeout timeout = new Timeout(Duration.create(5, "seconds"));
        Future<Object> future = Patterns.ask(schoolActor, getById, timeout);
        try {
            return (Student) Await.result(future, timeout.duration());
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Student getStudentByIdSync(long studentId) {
        return studentRepository.findById(studentId).get();
    }

    @Override
    public List<Student> getAllBySchoolId(long schoolId) {
        ActorRef studentActor = actorSystem.actorOf(SpringProps.create(actorSystem, StudentActor.class));
        StudentActor.GetStudentsBySchoolId getStudentsBySchoolId = new StudentActor.GetStudentsBySchoolId(schoolId);
        Timeout timeout = new Timeout(Duration.create(5, "seconds"));
        Future<Object> future = Patterns.ask(studentActor, getStudentsBySchoolId, timeout);
        try {
            return (List<Student>) Await.result(future, timeout.duration());
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<StudentNotification> getStudentNotificationsByStudentId(long studentId) {
        return studentNotificationRepository.getStudentNotificationsByStudentId(studentId);
    }

    @Override
    public List<Student> getAllStudents() {
        ActorRef studentActor = actorSystem.actorOf(SpringProps.create(actorSystem, StudentActor.class));
        StudentActor.GetAllStudents getAllStudents = new StudentActor.GetAllStudents();
        Timeout timeout = new Timeout(Duration.create(5, "seconds"));
        Future<Object> future = Patterns.ask(studentActor, getAllStudents, timeout);
        try {
            return (List<Student>) Await.result(future, timeout.duration());
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

}
