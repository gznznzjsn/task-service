package com.gznznzjsn.taskservice.persistence;

import com.gznznzjsn.taskservice.persistence.converter.RequirementReadConverter;
import com.gznznzjsn.taskservice.persistence.converter.RequirementWriteConverter;
import com.gznznzjsn.taskservice.persistence.converter.TaskReadConverter;
import com.gznznzjsn.taskservice.persistence.converter.TaskWriteConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.List;

@Configuration
public class RepositoryConfig {

    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(List.of(
                new TaskWriteConverter(),
                new RequirementWriteConverter(),
                new TaskReadConverter(),
                new RequirementReadConverter()
        ));
    }

}
