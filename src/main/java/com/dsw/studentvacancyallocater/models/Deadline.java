package com.dsw.studentvacancyallocater.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "deadline")
public class Deadline extends AbstractEntity {
    @OneToOne
    @MapsId
    @JoinColumn(name = "school_id")
    private School school;

    @Temporal(TemporalType.TIMESTAMP)
    protected Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    protected Date endDate;
}
