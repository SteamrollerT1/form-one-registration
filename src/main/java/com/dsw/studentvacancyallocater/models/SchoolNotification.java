package com.dsw.studentvacancyallocater.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "school_notification")
public class SchoolNotification extends Notification {
    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;
}
