package com.gznznzjsn.taskservice.web.kafka;

import com.gznznzjsn.taskservice.service.RequirementService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.kafka.receiver.KafkaReceiver;

@RequiredArgsConstructor
@Component
public class TaskReceiver {

    private final KafkaReceiver<String, Long> receiver;
    private final RequirementService requirementService;

    @PostConstruct
    public void fetch() {
        this.receiver.receive()
                .subscribe(record -> {
                    System.out.println(record);
                    record.receiverOffset().acknowledge();
                    requirementService.sendRequirements(record.value())
                            .subscribe();
                });
    }

}
