package com.gznznzjsn.taskservice.persistence.repository;

import com.gznznzjsn.taskservice.domain.Task;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends ReactiveMongoRepository<Task, String> {
}