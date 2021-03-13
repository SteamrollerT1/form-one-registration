package com.dsw.studentvacancyallocater.repositories;

import com.dsw.studentvacancyallocater.models.Deadline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface DeadlineRepository extends JpaRepository<Deadline, Long> {
    @Query("SELECT d FROM Deadline d ORDER BY d.dateCreated ASC")
    List<Deadline> getAll();


    @Query("SELECT d FROM Deadline d WHERE d.id = :id ORDER BY d.dateCreated ASC")
    Deadline getById(@RequestParam("id") long id);


    @Query("SELECT d FROM Deadline d JOIN d.school s WHERE s.id = :schoolId ORDER BY d.dateCreated ASC")
    Deadline getBySchoolId(@RequestParam("schoolId") long schoolId);
}
