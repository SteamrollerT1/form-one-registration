package com.dsw.studentvacancyallocater.repositories;

import com.dsw.studentvacancyallocater.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("SELECT st FROM Student st JOIN st.schools sc WHERE sc.id = :schoolId")
    List<Student> getStudentsBySchoolId(@Param("schoolId") long schoolId);

    @Query("SELECT count(st) FROM Student st JOIN st.schools sc WHERE sc.id = :schoolId")
    long getNumOfAcceptedStudentsBySchoolId(@Param("schoolId") long schoolId);
}
