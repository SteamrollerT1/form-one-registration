package com.dsw.studentvacancyallocater.controllers;

import com.dsw.studentvacancyallocater.dtos.*;
import com.dsw.studentvacancyallocater.models.School;
import com.dsw.studentvacancyallocater.services.iface.SchoolService;
import com.dsw.studentvacancyallocater.services.iface.StudentService;
import com.dsw.studentvacancyallocater.utilities.Codes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/school")
public class SchoolController {
    @Autowired
    private SchoolService schoolService;

    @Autowired
    private StudentService studentService;

    @GetMapping("/test")
    @ResponseBody
    String test() {
        return "School endpoints up!!!";
    }

    @PostMapping("/register")
    @ResponseBody
    ResponseDTO register(@RequestBody SchoolDTO dto) {
        return schoolService.registerSchool(dto);
    }

    @PostMapping("/accept")
    @ResponseBody
    ResponseDTO accept(@RequestBody AcceptStudentDTO dto) {
        return schoolService.acceptStudent(dto);
    }

    @PostMapping("/reject")
    @ResponseBody
    ResponseDTO register(@RequestBody RejectStudentDTO dto) {
        return schoolService.rejectStudent(dto);
    }


    @GetMapping("/getById/{id}")
    @ResponseBody
    School getById(@PathVariable String id) {
        return schoolService.getByIdAsync(id);
    }

    @GetMapping("/getAll")
    @ResponseBody
    List<School> getByAll() {
        return schoolService.getAll();
    }

    @PostMapping("/suspend/{id}")
    @ResponseBody
    ResponseDTO suspend(@PathVariable String id) {
        schoolService.suspend(Long.parseLong(id));
        return new ResponseDTO("School with id : " + id + " has been suspended", Codes.generalSuccess);
    }


    @PostMapping("/openRegistrationPeriod")
    @ResponseBody
    ResponseDTO openRegistrationPeriod(@RequestBody DeadlineDTO dto) {
        return schoolService.openRegistration(dto);
    }

    @PostMapping("/closeRegistrationPeriod")
    @ResponseBody
    ResponseDTO closeRegistrationPeriod(@RequestBody DeadlineDTO dto) {
        return schoolService.closeRegistration(dto);
    }

}
