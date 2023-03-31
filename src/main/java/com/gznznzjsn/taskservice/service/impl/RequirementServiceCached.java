package com.gznznzjsn.taskservice.service.impl;

import com.gznznzjsn.taskservice.domain.ConsumableType;
import com.gznznzjsn.taskservice.domain.Requirement;
import com.gznznzjsn.taskservice.domain.Task;
import com.gznznzjsn.taskservice.persistence.repository.RequirementRepository;
import com.gznznzjsn.taskservice.service.RequirementService;
import com.gznznzjsn.taskservice.web.kafka.RequirementSender;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RequirementServiceCached implements RequirementService {

    private static final String KEY = "requirementsByTask";

    private final ReactiveHashOperations<String, String, List<Requirement>> hashOps;
    private final RequirementRepository requirementRepository;
    private final RequirementSender requirementSender;

    @Override
    public Flux<Requirement> retrieveAllByTaskId(String taskId) {
        return hashOps.get(KEY, taskId)
                .flatMapMany(Flux::fromIterable)
                .switchIfEmpty(getFromRepositoryAndCache(taskId));
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
                .flatMap(requirementRepository::save)
                .flatMap(r -> Mono.zip(
                        Mono.just(r),
                        hashOps.remove(KEY, r.getTask().getId())
                ))
                .map(Tuple2::getT1);
    }

    private Flux<Requirement> getFromRepositoryAndCache(String taskId) {
        return requirementRepository.findAllByTask(taskId)
                .collectList()
                .flatMap(l -> hashOps.put(KEY, taskId, l)
                        .thenReturn(l))
                .flatMapMany(Flux::fromIterable);
    }

}
