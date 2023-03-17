package com.gznznzjsn.taskservice.kafka;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.kafka.receiver.KafkaReceiver;

@RequiredArgsConstructor
@Component
public class CustomReceiver {

    private final KafkaReceiver<String, Long> receiver;

    @PostConstruct
    public void fetch() {
        this.receiver.receive()
                .subscribe(r -> r.receiverOffset().acknowledge());
    }

}
