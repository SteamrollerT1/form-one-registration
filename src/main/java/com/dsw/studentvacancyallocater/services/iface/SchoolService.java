package com.dsw.studentvacancyallocater.services.iface;

import com.dsw.studentvacancyallocater.dtos.*;
import com.dsw.studentvacancyallocater.models.School;

import java.util.List;

public interface SchoolService {
    ResponseDTO registerSchool(SchoolDTO dto);

    ResponseDTO rejectStudent(RejectStudentDTO dto);

    ResponseDTO acceptStudent(AcceptStudentDTO dto);

    ResponseDTO openRegistration(DeadlineDTO dto);

    ResponseDTO closeRegistration(DeadlineDTO dto);

    School getByIdAsync(String id);

    long getNumOfAcceptedStudentsBySchoolId(long schoolId);

    School getByIdSync(long id);

    School suspend(long id);

    List<School> getAll();

}
