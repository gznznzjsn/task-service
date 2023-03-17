package com.gznznzjsn.taskservice.service.impl;

import com.gznznzjsn.taskservice.domain.Requirement;
import com.gznznzjsn.taskservice.persistence.repository.RequirementRepository;
import com.gznznzjsn.taskservice.service.RequirementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RequirementServiceImpl implements RequirementService {

    private final RequirementRepository requirementRepository;

    @Override
    public Flux<Requirement> retrieveAllByTaskId(Long taskId) {
        return requirementRepository.findAllByTaskId(taskId);
    }

    @Override
    public Mono<Void> sendRequirements(Long taskId) {
        return retrieveAllByTaskId(taskId)
                .thenEmpty(requirement -> System.out.println("Sent " + requirement));
    }

}
