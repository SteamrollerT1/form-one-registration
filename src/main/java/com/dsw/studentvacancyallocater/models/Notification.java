package com.dsw.studentvacancyallocater.models;

import lombok.Data;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@Data
@MappedSuperclass
public abstract class Notification extends AbstractEntity implements Serializable {
    protected String narrative;
}
