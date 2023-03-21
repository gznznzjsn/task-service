package com.gznznzjsn.taskservice.web.kafka;

import com.gznznzjsn.taskservice.domain.Requirement;
import com.gznznzjsn.taskservice.web.kafka.parser.XMLParser;
import com.jcabi.xml.XML;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

@Component
@RequiredArgsConstructor
public class RequirementSender {

    private final XML requirementProducerSettings;
    private final KafkaSender<String, Requirement> sender;

    public void send(Requirement requirement) {
        this.sender.send(
                Mono.just(
                        SenderRecord.create(
                                "requirement",
                                0,
                                System.currentTimeMillis(),
                                new XMLParser(requirementProducerSettings).parse("requirementKey"),
                                requirement,
                                null
                        )
                )
        ).subscribe();
    }

}