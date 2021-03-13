package com.dsw.studentvacancyallocater.services.impl;

import akka.actor.ActorSystem;
import com.dsw.studentvacancyallocater.dtos.DeadlineDTO;
import com.dsw.studentvacancyallocater.models.Deadline;
import com.dsw.studentvacancyallocater.repositories.DeadlineRepository;
import com.dsw.studentvacancyallocater.services.iface.DeadlineService;
import com.dsw.studentvacancyallocater.utilities.DTOToModelConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeadlineServiceImpl implements DeadlineService {

    @Autowired
    private ActorSystem actorSystem;

    @Autowired
    private DTOToModelConvertor dtoToModelConvertor;

    @Autowired
    private DeadlineRepository deadlineRepository;

    @Override
    public Deadline openDeadlinePeriod(DeadlineDTO dto) {
        return null;
    }

    @Override
    public Deadline closeDeadlinePeriod(DeadlineDTO dto) {
        return null;
    }
}
