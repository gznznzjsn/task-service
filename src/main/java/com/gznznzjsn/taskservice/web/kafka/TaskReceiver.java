package com.gznznzjsn.taskservice.web.kafka;

import com.gznznzjsn.taskservice.service.RequirementService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.kafka.receiver.KafkaReceiver;

@Component
@RequiredArgsConstructor
public class TaskReceiver {

    private final KafkaReceiver<String, String> receiver;
    private final RequirementService requirementService;

    @PostConstruct
    public void fetch() {
        this.receiver.receive()
                .subscribe(record -> {
                    record.receiverOffset().acknowledge();
                    requirementService.sendRequirements(record.value())
                            .subscribe();
                });
    }

}
