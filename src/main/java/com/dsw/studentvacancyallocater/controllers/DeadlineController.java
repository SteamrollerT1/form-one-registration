package com.dsw.studentvacancyallocater.controllers;

import com.dsw.studentvacancyallocater.models.Deadline;
import com.dsw.studentvacancyallocater.services.iface.DeadlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/deadline")
public class DeadlineController {
    @Autowired
    private DeadlineService deadlineService;


    @GetMapping("/test")
    @ResponseBody
    String test() {
        return "Deadline endpoints up!!!";
    }


    @GetMapping("/getBySchoolId/{schoolId}")
    @ResponseBody
    Deadline getBySchoolId(@PathVariable String schoolId) {
        return deadlineService.getBySchoolIdAsync(Long.parseLong(schoolId));
    }

    @GetMapping("/getById/{id}}")
    @ResponseBody
    Deadline resultsByStudentIdAndSchoolId(@PathVariable String id) {
        return deadlineService.getByIdAsync(Long.parseLong(id));
    }

}
