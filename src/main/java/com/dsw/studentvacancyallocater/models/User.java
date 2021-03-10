package com.dsw.studentvacancyallocater.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "system_user")
public class User extends AbstractEntity {
    private String username;
    private String passwordHash;
    private String salt;
}
