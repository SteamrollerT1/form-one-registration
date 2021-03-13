package com.dsw.studentvacancyallocater.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "contact_details")
public class ContactDetails extends AbstractEntity {
    private String telephone;
    private String cellphone1;
    private String cellphone2;
    private String cellphone3;
    private String emailAddress;
}
