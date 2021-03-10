package com.dsw.studentvacancyallocater.repositories;

import com.dsw.studentvacancyallocater.models.School;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolRepository extends JpaRepository<School, Long> {
}
