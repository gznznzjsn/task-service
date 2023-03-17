package com.gznznzjsn.taskservice.service.impl;

import com.gznznzjsn.taskservice.domain.Requirement;
import com.gznznzjsn.taskservice.persistence.repository.RequirementRepository;
import com.gznznzjsn.taskservice.service.RequirementService;
import com.gznznzjsn.taskservice.web.kafka.RequirementSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class RequirementServiceImpl implements RequirementService {

    private final RequirementRepository requirementRepository;
    private final RequirementSender requirementSender;

    @Override
    public Flux<Requirement> retrieveAllByTaskId(Long taskId) {
        return requirementRepository.findAllByTaskId(taskId);
    }

    @Override
    public Flux<Requirement> sendRequirements(Long taskId) {
        return retrieveAllByTaskId(taskId)
                .doOnNext(requirementSender::send);
    }

}
