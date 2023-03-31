package com.gznznzjsn.taskservice.service.impl;

import com.gznznzjsn.taskservice.domain.Task;
import com.gznznzjsn.taskservice.persistence.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@Service
public class TaskServiceCachedImpl extends TaskServiceImpl {

    private static final String KEY = "task";

    private final ReactiveHashOperations<String, String, Task> hashOps;

    @Autowired
    public TaskServiceCachedImpl(
            TaskRepository repository,
            ReactiveHashOperations<String, String, Task> hashOps
    ) {
        super(repository);
        this.hashOps = hashOps;
    }

    @Override
    public Mono<Task> get(String taskId) {
        return hashOps.get(KEY, taskId)
                .switchIfEmpty(getFromRepositoryAndCache(taskId));
    }

    @Override
    public Mono<Task> create(Task task) {
        return super.create(task)
                .flatMap(t -> Mono.zip(
                        Mono.just(t),
                        hashOps.remove(KEY, t.getId())
                ))
                .map(Tuple2::getT1);
    }

    private Mono<Task> getFromRepositoryAndCache(String taskId) {
        return super.get(taskId)
                .flatMap(task -> hashOps.put(KEY, taskId, task)
                        .thenReturn(task));
    }

}
