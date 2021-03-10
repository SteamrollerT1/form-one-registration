package com.dsw.studentvacancyallocater.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "student_notification")
public class StudentNotification extends Notification {
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

}
