package com.dsw.studentvacancyallocater.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "deadline")
public class Deadline extends AbstractEntity {

}
