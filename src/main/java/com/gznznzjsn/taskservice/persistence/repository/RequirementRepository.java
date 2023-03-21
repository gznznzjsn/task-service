package com.gznznzjsn.taskservice.persistence.repository;

import com.gznznzjsn.taskservice.domain.Requirement;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface RequirementRepository extends ReactiveMongoRepository<Requirement, String> {

    @Aggregation(pipeline = {
            "{ $match : { task_id : ?0 } }",
            """
                    {
                        $lookup: {
                            from: tasks,
                            let: { searchId: $task_id},
                            pipeline: [
                                {
                                    $match: {
                                        $expr: {
                                            $eq: [
                                                {
                                                    $toString: $_id
                                                },
                                                $$searchId
                                            ]
                                        }
                                    }
                                }
                            ],
                            as: task
                        }
                    }
                                                     """
    })
    Flux<Requirement> findAllByTask(String taskId);

}
