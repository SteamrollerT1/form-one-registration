package com.dsw.studentvacancyallocater.repositories;

import com.dsw.studentvacancyallocater.models.ApplicationResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationResultsRepository extends JpaRepository<ApplicationResult, Long> {
    @Query("SELECT ar FROM ApplicationResult ar JOIN ar.school sc WHERE sc.id = :schoolId")
    List<ApplicationResult> getBySchoolId(@Param("schoolId") long schoolId);

    @Query("SELECT ar FROM ApplicationResult ar JOIN ar.student st WHERE st.id = :studentId")
    List<ApplicationResult> getByStudentId(@Param("studentId") long studentId);

    @Query("SELECT ar FROM ApplicationResult ar JOIN ar.school sc JOIN ar.student st WHERE sc.id = :schoolId AND st.id = :studentId")
    ApplicationResult getByStudentIdAndSchoolId(@Param("studentId") long studentId, @Param("schoolId") long schoolId);
}
