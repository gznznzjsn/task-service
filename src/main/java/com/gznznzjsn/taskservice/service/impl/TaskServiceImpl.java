package com.gznznzjsn.taskservice.service.impl;

import com.gznznzjsn.taskservice.domain.Task;
import com.gznznzjsn.taskservice.domain.exception.ResourceNotFoundException;
import com.gznznzjsn.taskservice.persistence.repository.TaskRepository;
import com.gznznzjsn.taskservice.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    @Transactional(readOnly = true)
    public Mono<Task> get(String taskId) {
        return taskRepository
                .findById(taskId)
                .switchIfEmpty(Mono.error(
                        new ResourceNotFoundException(
                                "Task with id=" + taskId + " not found!"
                        )
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<Task> getAllIn(List<String> taskIds) {
        return Flux.fromIterable(taskIds)
                .flatMap(this::get);
    }

    @Override
    @Transactional
    public Mono<Task> create(Task task) {
        return Mono.just(task)
                .map(t -> Task.builder()
                        .name(t.getName())
                        .duration(t.getDuration())
                        .costPerHour(t.getCostPerHour())
                        .specialization(t.getSpecialization())
                        .build()
                )
                .flatMap(taskRepository::save);
    }

}
