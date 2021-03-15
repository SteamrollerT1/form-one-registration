package com.dsw.studentvacancyallocater.dtos;

import lombok.Data;

@Data
public class SchoolDTO {
    private String name;
    private String city;
    private String suburb;
    private String street;
    private String number;
    private String schoolId;
    private String status;
    private int maxUnitsAllowed;
    private int applicantCapacity;

    private String username;
    private String password;
}
