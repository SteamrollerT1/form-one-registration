package com.dsw.studentvacancyallocater.repositories;

import com.dsw.studentvacancyallocater.models.StudentNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentNotificationRepository extends JpaRepository<StudentNotification, Long> {
    @Query("SELECT sn FROM StudentNotification sn JOIN sn.student st WHERE st.id = :studentId ORDER BY sn.dateCreated DESC")
    List<StudentNotification> getStudentNotificationsByStudentId(@Param("studentId") long studentId);
}
