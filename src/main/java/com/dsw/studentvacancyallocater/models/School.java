package com.dsw.studentvacancyallocater.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "school")
public class School extends AbstractEntity {
    private String name;
    private String city;
    private String suburb;
    private String street;
    private String number;
    private int maxUnitsAllowed;
    private int applicantCapacity;

}
