package com.dsw.studentvacancyallocater.services.iface;

import com.dsw.studentvacancyallocater.models.ApplicationResult;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApplicationResultsService {

    List<ApplicationResult> getBySchoolId(long schoolId);


    List<ApplicationResult> getByStudentId(long studentId);


    ApplicationResult getByStudentIdAndSchoolId(long studentId, long schoolId);
}
