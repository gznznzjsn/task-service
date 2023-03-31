package com.gznznzjsn.taskservice.service.impl;

import com.gznznzjsn.taskservice.domain.Requirement;
import com.gznznzjsn.taskservice.persistence.repository.RequirementRepository;
import com.gznznzjsn.taskservice.web.kafka.RequirementSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.List;

@Service
public class RequirementServiceCachedImpl extends RequirementServiceImpl {

    private static final String KEY = "requirementsByTask";

    private final ReactiveHashOperations<String, String, List<Requirement>> hashOps;

    @Autowired
    public RequirementServiceCachedImpl(
            RequirementRepository requirementRepository,
            RequirementSender requirementSender,
            ReactiveHashOperations<String, String, List<Requirement>> hashOps
    ) {
        super(requirementRepository, requirementSender);
        this.hashOps = hashOps;
    }

    @Override
    public Flux<Requirement> retrieveAllByTaskId(String taskId) {
        return hashOps.get(KEY, taskId)
                .switchIfEmpty(getFromRepositoryAndCache(taskId))
                .flatMapMany(Flux::fromIterable);
    }

    @Override
    public Mono<Requirement> create(Requirement requirement) {
        return super.create(requirement)
                .flatMap(r -> Mono.zip(
                        Mono.just(r),
                        hashOps.remove(KEY, r.getTask().getId())
                ))
                .map(Tuple2::getT1);
    }

    private Mono<List<Requirement>> getFromRepositoryAndCache(String taskId) {
        return super.retrieveAllByTaskId(taskId)
                .collectList()
                .flatMap(l -> hashOps.put(KEY, taskId, l)
                        .thenReturn(l));
    }

}
