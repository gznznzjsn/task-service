package com.gznznzjsn.taskservice.service.impl;

import com.gznznzjsn.taskservice.domain.Task;
import com.gznznzjsn.taskservice.domain.exception.ResourceNotFoundException;
import com.gznznzjsn.taskservice.persistence.repository.TaskRepository;
import com.gznznzjsn.taskservice.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    @Transactional(readOnly = true)
    public Task get(Long taskId) {
        return null;
//        return taskRepository.findById(taskId)
//                .orElseThrow(() -> new ResourceNotFoundException("Task with id=" + taskId + " not found!"));

    }

    @Transactional(readOnly = true)
    public List<Task> getByAssignment(Long assignmentId) {
        return null;
//        return taskRepository.findAllByAssignmentId(assignmentId);
    }

}
