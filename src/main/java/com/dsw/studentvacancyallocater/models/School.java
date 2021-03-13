package com.dsw.studentvacancyallocater.models;

import lombok.Data;

import javax.persistence.*;

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

    @OneToOne(targetEntity = Deadline.class, mappedBy = "school", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Deadline deadline;

}
