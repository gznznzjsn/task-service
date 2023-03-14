package com.gznznzjsn.taskservice.service;


import com.gznznzjsn.taskservice.domain.Task;

import java.util.List;

public interface TaskService {

    Task get(Long id);

    List<Task> getByAssignment(Long assignmentId);

}
