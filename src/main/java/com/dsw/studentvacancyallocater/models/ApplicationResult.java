package com.dsw.studentvacancyallocater.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "application_result")
public class ApplicationResult extends AbstractEntity {
    private String narrative;

    @ManyToOne(targetEntity = Student.class)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(targetEntity = School.class)
    @JoinColumn(name = "school_id")
    private School school;


}
