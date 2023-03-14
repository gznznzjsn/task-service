package com.gznznzjsn.taskservice.service.impl;

import com.gznznzjsn.taskservice.domain.Task;
import com.gznznzjsn.taskservice.domain.exception.ResourceNotFoundException;
import com.gznznzjsn.taskservice.persistence.repository.TaskRepository;
import com.gznznzjsn.taskservice.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    @Transactional(readOnly = true)
    public Mono<Task> get(Long taskId) {
        return taskRepository
                .findById(taskId)
                .switchIfEmpty(Mono.error(
                        new ResourceNotFoundException("Task with id=" + taskId + " not found!")
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<Task> getAllIn(List<Long> taskIds) {
        return Flux.fromIterable(taskIds)
                .flatMap(this::get);
    }

}
