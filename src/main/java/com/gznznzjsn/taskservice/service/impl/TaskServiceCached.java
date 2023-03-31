package com.gznznzjsn.taskservice.service.impl;

import com.gznznzjsn.taskservice.domain.Task;
import com.gznznzjsn.taskservice.domain.exception.ResourceNotFoundException;
import com.gznznzjsn.taskservice.persistence.repository.TaskRepository;
import com.gznznzjsn.taskservice.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceCached implements TaskService {

    private static final String KEY = "task";

    private final ReactiveHashOperations<String, String, Task> hashOps;
    private final TaskRepository taskRepository;

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
        return Mono.just(task)
                .map(t -> Task.builder()
                        .name(t.getName())
                        .duration(t.getDuration())
                        .costPerHour(t.getCostPerHour())
                        .specialization(t.getSpecialization())
                        .build()
                )
                .flatMap(taskRepository::save)
                .flatMap(t -> Mono.zip(
                        Mono.just(t),
                        hashOps.remove(KEY, t.getId())
                ))
                .map(Tuple2::getT1);
    }

    private Mono<Task> getFromRepositoryAndCache(String taskId) {
        return taskRepository
                .findById(taskId)
                .switchIfEmpty(Mono.error(
                        new ResourceNotFoundException(
                                "Task with id=" + taskId + " not found!"
                        )
                ))
                .flatMap(task -> hashOps.put(KEY, taskId, task)
                        .thenReturn(task));
    }

}
