package com.dsw.studentvacancyallocater.services.impl;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.pattern.Patterns;
import akka.util.Timeout;
import com.dsw.studentvacancyallocater.actors.SchoolActor;
import com.dsw.studentvacancyallocater.configs.SpringProps;
import com.dsw.studentvacancyallocater.dtos.AcceptStudentDTO;
import com.dsw.studentvacancyallocater.dtos.RejectStudentDTO;
import com.dsw.studentvacancyallocater.dtos.ResponseDTO;
import com.dsw.studentvacancyallocater.dtos.SchoolDTO;
import com.dsw.studentvacancyallocater.enums.EntityStatus;
import com.dsw.studentvacancyallocater.models.School;
import com.dsw.studentvacancyallocater.models.Student;
import com.dsw.studentvacancyallocater.repositories.SchoolRepository;
import com.dsw.studentvacancyallocater.repositories.StudentRepository;
import com.dsw.studentvacancyallocater.services.iface.SchoolService;
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
public class SchoolServiceImpl implements SchoolService {

    @Autowired
    private ActorSystem actorSystem;

    @Autowired
    private DTOToModelConvertor dtoToModelConvertor;

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentService studentService;


    @Override
    public ResponseDTO registerSchool(SchoolDTO dto) {
        dto.setStatus(EntityStatus.ACTIVE.name());
        ActorRef schoolActor = actorSystem.actorOf(SpringProps.create(actorSystem, SchoolActor.class));
        SchoolActor.RegisterSchool registerSchool = new SchoolActor.RegisterSchool(dtoToModelConvertor.dtoToSchool(dto));
        schoolActor.tell(registerSchool, ActorRef.noSender());
        return new ResponseDTO("School registration initiated. You will be notified when its complete.", "00");
    }


    @Override
    public ResponseDTO acceptStudent(AcceptStudentDTO dto) {
        ActorRef schoolActor = actorSystem.actorOf(SpringProps.create(actorSystem, SchoolActor.class));

        School school = schoolRepository.findById(Long.parseLong(dto.getSchoolId())).get();
        Student student = studentService.getStudentByIdSync(Long.parseLong(dto.getStudentId()));

        SchoolActor.AcceptStudent acceptStudent = new SchoolActor.AcceptStudent(school, student, dto.getNarrative());
        schoolActor.tell(acceptStudent, ActorRef.noSender());
        return new ResponseDTO("Student application processing initiated. You will be notified when its complete.", "00");
    }

    @Override
    public ResponseDTO rejectStudent(RejectStudentDTO dto) {
        ActorRef schoolActor = actorSystem.actorOf(SpringProps.create(actorSystem, SchoolActor.class));

        School school = schoolRepository.findById(Long.parseLong(dto.getSchoolId())).get();
        Student student = studentService.getStudentByIdSync(Long.parseLong(dto.getStudentId()));

        SchoolActor.RejectedStudent rejectedStudent = new SchoolActor.RejectedStudent(school, student, dto.getNarrative());

        schoolActor.tell(rejectedStudent, ActorRef.noSender());
        return new ResponseDTO("Student application processing initiated. You will be notified when its complete.", "00");
    }


    @Override
    public School getByIdAsync(String id) {
        ActorRef schoolActor = actorSystem.actorOf(SpringProps.create(actorSystem, SchoolActor.class));
        SchoolActor.GetById getById = new SchoolActor.GetById(Long.parseLong(id));
        Timeout timeout = new Timeout(Duration.create(5, "seconds"));
        Future<Object> future = Patterns.ask(schoolActor, getById, timeout);
        try {
            return (School) Await.result(future, timeout.duration());
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public long getNumOfAcceptedStudentsBySchoolId(long schoolId) {
        return studentRepository.getNumOfAcceptedStudentsBySchoolId(schoolId);
    }

    @Override
    public School getByIdSync(long id) {
        return schoolRepository.findById(id).get();
    }

    @Override
    public List<School> getAll() {
        ActorRef schoolActor = actorSystem.actorOf(SpringProps.create(actorSystem, SchoolActor.class));
        Timeout timeout = new Timeout(Duration.create(5, "seconds"));
        Future<Object> future = Patterns.ask(schoolActor, SchoolActor.GetAllSchools.GET_ALL_SCHOOLS, timeout);
        try {
            return (List<School>) Await.result(future, timeout.duration());
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

}
