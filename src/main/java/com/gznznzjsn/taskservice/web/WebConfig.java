package com.gznznzjsn.taskservice.web;

import com.jcabi.xml.XML;
import com.jcabi.xml.XMLDocument;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

@Configuration
public class WebConfig {

    @Value("${settings.requirementProducer}")
    private String requirementProducerPath;

    @Value("${settings.taskConsumer}")
    private String taskConsumerPath;

    @Bean
    @SneakyThrows
    public XML requirementProducerSettings() {
        return new XMLDocument(ResourceUtils.getFile(requirementProducerPath));
    }

    @Bean
    @SneakyThrows
    public XML taskConsumerSettings() {
        return new XMLDocument(ResourceUtils.getFile(taskConsumerPath));
    }

}