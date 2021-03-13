package com.dsw.studentvacancyallocater.services.iface;

import com.dsw.studentvacancyallocater.dtos.DeadlineDTO;
import com.dsw.studentvacancyallocater.models.Deadline;

public interface DeadlineService {
    Deadline openDeadlinePeriod(DeadlineDTO dto);

    Deadline closeDeadlinePeriod(DeadlineDTO dto);

    public Deadline getBySchoolIdSync(Long schoolId);


    public Deadline getByIdSync(Long id);


    public Deadline getBySchoolIdAsync(Long schoolId);


    public Deadline getByIdAsync(Long id);
}
