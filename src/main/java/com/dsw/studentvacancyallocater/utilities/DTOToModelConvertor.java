package com.dsw.studentvacancyallocater.utilities;

import com.dsw.studentvacancyallocater.dtos.SchoolDTO;
import com.dsw.studentvacancyallocater.dtos.StudentDTO;
import com.dsw.studentvacancyallocater.enums.EntityStatus;
import com.dsw.studentvacancyallocater.models.School;
import com.dsw.studentvacancyallocater.models.Student;
import com.dsw.studentvacancyallocater.services.iface.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DTOToModelConvertor {

    @Autowired
    private SchoolService schoolService;

    public School dtoToSchool(SchoolDTO dto) {
        School school = new School();
        school.setName(dto.getName());
        school.setCity(dto.getCity());
        school.setSuburb(dto.getSuburb());
        school.setStreet(dto.getStreet());
        school.setStatus(dto.getStatus());
        school.setNumber(dto.getNumber());
        school.setMaxUnitsAllowed(dto.getMaxUnitsAllowed());
        school.setApplicantCapacity(dto.getApplicantCapacity());
        return school;
    }

    public Student dtoToStudent(StudentDTO dto) {
        Student student = new Student();
        student.setName(dto.getName());
        student.setSurname(dto.getSurname());
        student.setUnits(dto.getUnits());
        student.setStatus(EntityStatus.PENDING_APPROVAL.name());

        List<School> schools = dto.getPreferredSchoolIds()
                .stream()
                .map(Long::parseLong)
                .map(schoolService::getByIdSync)
                .collect(Collectors.toList());

        student.setSchools(schools);
        return student;
    }
}
