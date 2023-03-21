package com.gznznzjsn.taskservice.web.kafka;


import com.gznznzjsn.taskservice.web.kafka.parser.XMLParser;
import com.jcabi.xml.XML;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class TaskConsumerConfig {

    private final XML taskConsumerSettings;

    @Bean
    public ReceiverOptions<String, String> receiverOptions() {
        XMLParser parser = new XMLParser(taskConsumerSettings);
        Map<String, Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                parser.parse("bootstrapServers"));
        properties.put(ConsumerConfig.GROUP_ID_CONFIG,
                parser.parse("groupId"));
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                parser.parse("keyDeserializerClass"));
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                parser.parse("valueDeserializerClass"));
        ReceiverOptions<String, String> receiverOptions = ReceiverOptions.create(properties);
        return receiverOptions.subscription(Collections.singleton("tasks"));
    }

    @Bean
    public KafkaReceiver<String, String> receiver(ReceiverOptions<String, String> receiverOptions) {
        return KafkaReceiver.create(receiverOptions);
    }

}
