package com.dsw.studentvacancyallocater.dtos;

import lombok.Data;

import java.util.List;

@Data
public class StudentDTO {
    private String name;
    private String surname;
    private int units;
    private List<String> preferredSchoolIds;
}
