package com.dsw.studentvacancyallocater.controllers;

import com.dsw.studentvacancyallocater.models.ApplicationResult;
import com.dsw.studentvacancyallocater.services.iface.ApplicationResultsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "/application")
public class ApplicationResultsController {
    @Autowired
    private ApplicationResultsService applicationResultsService;


    @GetMapping("/test")
    @ResponseBody
    String test() {
        return "Application Results endpoints up!!!";
    }


    @GetMapping("/resultsByStudentId/{studentId}")
    @ResponseBody
    List<ApplicationResult> resultsByStudentId(@PathVariable String studentId) {
        return applicationResultsService.getByStudentId(Long.parseLong(studentId));
    }

    @GetMapping("/resultsBySchoolId/{schoolId}")
    @ResponseBody
    List<ApplicationResult> resultsBySchoolId(@PathVariable String schoolId) {
        return applicationResultsService.getBySchoolId(Long.parseLong(schoolId));
    }

    @GetMapping("/results/{studentId}/{schoolId}")
    @ResponseBody
    ApplicationResult resultsByStudentIdAndSchoolId(@PathVariable String studentId, @PathVariable String schoolId) {
        return applicationResultsService.getByStudentIdAndSchoolId(Long.parseLong(studentId), Long.parseLong(schoolId));
    }

}
