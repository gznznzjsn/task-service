package com.gznznzjsn.taskservice.web.kafka;

import com.gznznzjsn.taskservice.domain.Requirement;
import com.gznznzjsn.taskservice.web.kafka.parser.XMLParser;
import com.jcabi.xml.XML;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RequirementProducerConfig {

    private final XML requirementProducerSettings;

    public RequirementProducerConfig(@Qualifier("requirementProducerSettings") XML requirementProducerSettings) {
        this.requirementProducerSettings = requirementProducerSettings;
    }

    @Bean
    public NewTopic tasksTopic() {
        return TopicBuilder.name("requirements")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public SenderOptions<String, Requirement> senderOptions() {
        XMLParser parser = new XMLParser(requirementProducerSettings);
        Map<String, Object> properties = new HashMap<>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                parser.parse("bootstrapServers"));
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                parser.parse("keySerializerClass"));
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                parser.parse("valueSerializerClass"));
        return SenderOptions.create(properties);
    }

    @Bean
    public KafkaSender<String, Requirement> sender(SenderOptions<String, Requirement> senderOptions) {
        return KafkaSender.create(senderOptions);
    }

}
