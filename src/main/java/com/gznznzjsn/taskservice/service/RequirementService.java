package com.gznznzjsn.taskservice.service;

import com.gznznzjsn.taskservice.domain.Requirement;
import reactor.core.publisher.Flux;

public interface RequirementService {

    Flux<Requirement> retrieveAllByTaskId(Long taskId);

    Flux<Requirement> sendRequirements(Long taskId);
}
