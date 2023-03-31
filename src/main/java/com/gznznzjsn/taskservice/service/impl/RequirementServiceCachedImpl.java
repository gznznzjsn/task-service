package com.gznznzjsn.taskservice.service.impl;

import com.gznznzjsn.taskservice.domain.Requirement;
import com.gznznzjsn.taskservice.service.RequirementService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.List;

@Primary
@Service
@RequiredArgsConstructor
public class RequirementServiceCachedImpl implements RequirementService {

    private static final String KEY = "requirementsByTask";

    private final ReactiveHashOperations<String, String, List<Requirement>> hashOps;
    private final RequirementServiceImpl requirementService;

    @Override
    public Flux<Requirement> retrieveAllByTaskId(String taskId) {
        return hashOps.get(KEY, taskId)
                .flatMapMany(Flux::fromIterable)
                .switchIfEmpty(getFromRepositoryAndCache(taskId));
    }

    @Override
    public Flux<Requirement> sendRequirements(String taskId) {
        return requirementService.sendRequirements(taskId);
    }

    @Override
    public Mono<Requirement> create(Requirement requirement) {
        return requirementService.create(requirement)
                .flatMap(r -> Mono.zip(
                        Mono.just(r),
                        hashOps.remove(KEY, r.getTask().getId())
                ))
                .map(Tuple2::getT1);
    }

    private Flux<Requirement> getFromRepositoryAndCache(String taskId) {
        return requirementService.retrieveAllByTaskId(taskId)
                .collectList()
                .flatMap(l -> hashOps.put(KEY, taskId, l)
                        .thenReturn(l))
                .flatMapMany(Flux::fromIterable);
    }

}
