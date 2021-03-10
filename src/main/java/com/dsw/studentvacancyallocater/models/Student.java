package com.dsw.studentvacancyallocater.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "student")
public class Student extends AbstractEntity {
    private String name;
    private String surname;
    private int units;
    private long birthCertificate;
    private long resultsStatement;

    @JsonIgnore
    @ManyToMany(targetEntity = School.class, fetch = FetchType.EAGER)
    @JoinTable(name = "student_school",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "school_id")
    )
    private List<School> schools;


}
