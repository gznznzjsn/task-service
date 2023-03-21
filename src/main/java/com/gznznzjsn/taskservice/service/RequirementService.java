package com.gznznzjsn.taskservice.service;

import com.gznznzjsn.taskservice.domain.Requirement;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RequirementService {

    Flux<Requirement> retrieveAllByTaskId(String taskId);

    Flux<Requirement> sendRequirements(String taskId);

    Mono<Requirement> create(Requirement requirement);

}
