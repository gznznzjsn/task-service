package com.gznznzjsn.taskservice.persistence.repository;

import com.gznznzjsn.taskservice.domain.Requirement;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface RequirementRepository extends R2dbcRepository<Requirement, Long> {

    @Query("""
            SELECT
            requirement_id,
            task_id,
            tasks.specialization as task_specialization,
            tasks.name as task_name,
            tasks.duration as task_duration,
            tasks.cost_per_hour as task_cost_per_hour,
            consumable_type_id,
            required_quantity
            FROM requirements
            JOIN tasks USING(task_id)
            WHERE task_id = $1;
            """)
    Flux<Requirement> findAllByTaskId(Long taskId);

}
