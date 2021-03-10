package com.dsw.studentvacancyallocater.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@Data
public abstract class AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Version
    @JsonIgnore
    protected Long version;

    @Temporal(TemporalType.TIMESTAMP)
    protected Date dateCreated;

    protected String status;

    @JsonIgnore
    @PrePersist
    public void prePersist() {
        dateCreated = new Date();
    }
}
