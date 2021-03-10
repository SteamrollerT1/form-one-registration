package com.dsw.studentvacancyallocater.controllers;

import com.dsw.studentvacancyallocater.dtos.ResponseDTO;
import com.dsw.studentvacancyallocater.dtos.StudentDTO;
import com.dsw.studentvacancyallocater.models.Student;
import com.dsw.studentvacancyallocater.models.StudentNotification;
import com.dsw.studentvacancyallocater.services.iface.SchoolService;
import com.dsw.studentvacancyallocater.services.iface.StudentService;
import com.dsw.studentvacancyallocater.utilities.DTOToModelConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private DTOToModelConvertor dtoToModelConvertor;

    @GetMapping("/test")
    @ResponseBody
    String test() {
        return "Student endpoints up!!!";
    }

    @PostMapping("/register")
    @ResponseBody
    ResponseDTO register(@RequestBody StudentDTO dto) {
        return studentService.registerStudent(dto);
    }

    @GetMapping("/getAllBySchoolId/{schoolId}")
    @ResponseBody
    List<Student> getAllBySchoolId(@PathVariable String schoolId) {
        return studentService.getAllBySchoolId(Long.parseLong(schoolId));
    }

    @GetMapping("/getStudentNotificationsByStudentId/{studentId}")
    @ResponseBody
    List<StudentNotification> getStudentNotificationsByStudentId(@PathVariable String studentId) {
        return studentService.getStudentNotificationsByStudentId(Long.parseLong(studentId));
    }

    @GetMapping("/getAll")
    @ResponseBody
    List<Student> getAllBySchoolId() {
        return studentService.getAllStudents();
    }

}
