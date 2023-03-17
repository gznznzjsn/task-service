package com.gznznzjsn.taskservice.kafka;


import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class TaskConsumerConfig {

    @Bean
    public ReceiverOptions<String, Long> receiverOptions() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "taskConsumer");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
        ReceiverOptions<String, Long> receiverOptions = ReceiverOptions.create(properties);
        return receiverOptions.subscription(Collections.singleton("tasks"));
    }

    @Bean
    public KafkaReceiver<String, Long> receiver(ReceiverOptions<String, Long> receiverOptions) {
        return KafkaReceiver.create(receiverOptions);
    }

}
