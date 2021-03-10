package com.dsw.studentvacancyallocater.services.iface;

import com.dsw.studentvacancyallocater.dtos.AcceptStudentDTO;
import com.dsw.studentvacancyallocater.dtos.RejectStudentDTO;
import com.dsw.studentvacancyallocater.dtos.ResponseDTO;
import com.dsw.studentvacancyallocater.dtos.SchoolDTO;
import com.dsw.studentvacancyallocater.models.School;

import java.util.List;

public interface SchoolService {
    ResponseDTO registerSchool(SchoolDTO dto);

    ResponseDTO rejectStudent(RejectStudentDTO dto);

    ResponseDTO acceptStudent(AcceptStudentDTO dto);

    School getByIdAsync(String id);

    long getNumOfAcceptedStudentsBySchoolId(long schoolId);

    School getByIdSync(long id);

    List<School> getAll();

}
