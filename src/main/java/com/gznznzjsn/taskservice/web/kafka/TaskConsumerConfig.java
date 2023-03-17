package com.gznznzjsn.taskservice.web.kafka;


import com.gznznzjsn.taskservice.web.kafka.parser.XMLParser;
import com.jcabi.xml.XML;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class TaskConsumerConfig {

    private final XML taskConsumerSettings;

    public TaskConsumerConfig(@Qualifier("taskConsumerSettings") XML taskConsumerSettings) {
        this.taskConsumerSettings = taskConsumerSettings;
    }

    @Bean
    public ReceiverOptions<String, Long> receiverOptions() {
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
        ReceiverOptions<String, Long> receiverOptions = ReceiverOptions.create(properties);
        return receiverOptions.subscription(Collections.singleton("tasks"));
    }

    @Bean
    public KafkaReceiver<String, Long> receiver(ReceiverOptions<String, Long> receiverOptions) {
        return KafkaReceiver.create(receiverOptions);
    }

}
