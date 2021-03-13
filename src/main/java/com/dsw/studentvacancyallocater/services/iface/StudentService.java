package com.dsw.studentvacancyallocater.services.iface;

import com.dsw.studentvacancyallocater.dtos.ResponseDTO;
import com.dsw.studentvacancyallocater.dtos.StudentDTO;
import com.dsw.studentvacancyallocater.models.Student;
import com.dsw.studentvacancyallocater.models.StudentNotification;

import java.util.List;

public interface StudentService {
    ResponseDTO registerStudent(StudentDTO dto);

    Student getStudentByIdAsync(long studentId);

    Student getStudentByIdSync(long studentId);

    List<Student> getAllBySchoolId(long schoolId);

    List<StudentNotification> getStudentNotificationsByStudentId(long studentId);

    List<Student> getAllStudents();
    void readNotification(long id);
    Student suspend(long id);
}
