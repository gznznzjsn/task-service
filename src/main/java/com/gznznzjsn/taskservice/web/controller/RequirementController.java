package com.gznznzjsn.taskservice.web.controller;


import com.gznznzjsn.taskservice.domain.Requirement;
import com.gznznzjsn.taskservice.service.RequirementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task-api/v1/requirements")
public class RequirementController {

    private final RequirementService requirementService;

    @GetMapping
    public Flux<Requirement> findAllByTask(@RequestParam String taskId) {
        return requirementService.retrieveAllByTaskId(taskId) ; //todo dto
    }

    @PostMapping
    public Mono<Requirement> create(@RequestBody Requirement requirement) {
        return Mono.just(requirement)
                .flatMap(requirementService::create); // todo dto
    }

}
