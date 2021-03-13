package com.dsw.studentvacancyallocater.repositories;

import com.dsw.studentvacancyallocater.models.SchoolNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolNotificationRepository extends JpaRepository<SchoolNotification, Long> {
    @Query("SELECT sn FROM SchoolNotification sn JOIN sn.school sc WHERE sc.id = :schoolId  ORDER BY sn.dateCreated ASC")
    List<SchoolNotification> getBySchoolId(@Param("schoolId") long schoolId);

}
