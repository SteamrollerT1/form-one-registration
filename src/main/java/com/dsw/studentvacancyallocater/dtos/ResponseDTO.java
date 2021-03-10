package com.dsw.studentvacancyallocater.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseDTO {
    private String narrative;
    private String responseCode;
}
