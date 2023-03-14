package com.gznznzjsn.taskservice.web.controller;


import com.gznznzjsn.taskservice.domain.Task;
import jakarta.ws.rs.QueryParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task-api/v1/tasks")
public class TaskController {

    @GetMapping
    public Flux<Task> getTasks(@RequestParam("tasksId") List<Long> taskIds) {
        System.out.println(taskIds);
        return Flux.empty();
    }

}
