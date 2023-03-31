package com.gznznzjsn.taskservice.service.impl;

import com.gznznzjsn.taskservice.domain.Task;
import com.gznznzjsn.taskservice.service.TaskService;
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
public class TaskServiceCachedImpl implements TaskService {

    private static final String KEY = "task";

    private final TaskServiceImpl taskService;
    private final ReactiveHashOperations<String, String, Task> hashOps;

    @Override
    public Mono<Task> get(String taskId) {
        return hashOps.get(KEY, taskId)
                .switchIfEmpty(getFromRepositoryAndCache(taskId));
    }

    @Override
    public Flux<Task> getAllIn(List<String> taskIds) {
        return Flux.fromIterable(taskIds)
                .flatMap(this::get);
    }

    @Override
    public Mono<Task> create(Task task) {
        return taskService.create(task)
                .flatMap(t -> Mono.zip(
                        Mono.just(t),
                        hashOps.remove(KEY, t.getId())
                ))
                .map(Tuple2::getT1);
    }

    private Mono<Task> getFromRepositoryAndCache(String taskId) {
        return taskService.get(taskId)
                .flatMap(task -> hashOps.put(KEY, taskId, task)
                        .thenReturn(task));
    }

}
