package com.dsw.studentvacancyallocater.dtos;

import lombok.Data;

@Data
public class DeadlineDTO {
    private String schoolId;
    private String startDate;
    private String endDate;
}
