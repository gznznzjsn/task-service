package com.gznznzjsn.taskservice.service;

import com.gznznzjsn.taskservice.domain.Task;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface TaskService {

    Mono<Task> get(Long id);

    Flux<Task> getAllIn(List<Long> taskIds);

}
