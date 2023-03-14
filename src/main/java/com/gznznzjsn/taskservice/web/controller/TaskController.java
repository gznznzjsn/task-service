package com.gznznzjsn.taskservice.web.controller;


import com.gznznzjsn.taskservice.domain.Task;
import com.gznznzjsn.taskservice.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task-api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public Flux<Task> getTasks(@RequestParam("taskId") List<Long> taskIds) {
        return taskService.getAllIn(taskIds);
    }

}
