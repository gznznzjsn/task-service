package com.gznznzjsn.taskservice.web.controller;


import com.gznznzjsn.taskservice.service.TaskService;
import com.gznznzjsn.taskservice.web.dto.TaskDto;
import com.gznznzjsn.taskservice.web.dto.mapper.TaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task-api/v1/tasks")
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @GetMapping
    public Flux<TaskDto> getAllIn(@RequestParam("taskId") List<String> taskIds) {
        return taskService.getAllIn(taskIds)
                .map(taskMapper::toDto);
    }

    @PostMapping
    public Mono<TaskDto> create(@RequestBody TaskDto taskDto) {
        return Mono.just(taskDto)
                .map(taskMapper::toEntity)
                .flatMap(taskService::create)
                .map(taskMapper::toDto);
    }

}
