package com.gznznzjsn.taskservice.service.impl;

import com.gznznzjsn.taskservice.domain.ConsumableType;
import com.gznznzjsn.taskservice.domain.Requirement;
import com.gznznzjsn.taskservice.domain.Task;
import com.gznznzjsn.taskservice.persistence.repository.RequirementRepository;
import com.gznznzjsn.taskservice.service.RequirementService;
import com.gznznzjsn.taskservice.web.kafka.RequirementSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RequirementServiceImpl implements RequirementService {

    private final RequirementRepository requirementRepository;
    private final RequirementSender requirementSender;

    @Override
    public Flux<Requirement> retrieveAllByTaskId(String taskId) {
        return requirementRepository.findAllB(taskId);
    }

    @Override
    public Flux<Requirement> sendRequirements(String taskId) {
        return retrieveAllByTaskId(taskId)
                .doOnNext(requirementSender::send);
    }

    @Override
    public Mono<Requirement> create(Requirement requirement) {
        return Mono.just(requirement)
                .map(r -> Requirement.builder()
                        .task(Task.builder()
                                .id(r.getTask().getId())
                                .build())
                        .consumableType(ConsumableType.builder()
                                .id(r.getConsumableType().getId())
                                .build())
                        .requiredQuantity(r.getRequiredQuantity())
                        .build()
                )
                .flatMap(requirementRepository::save);
    }

}
