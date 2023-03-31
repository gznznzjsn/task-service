package com.gznznzjsn.taskservice.persistence;

import com.gznznzjsn.taskservice.domain.Requirement;
import com.gznznzjsn.taskservice.domain.Task;
import com.gznznzjsn.taskservice.persistence.converter.RequirementReadConverter;
import com.gznznzjsn.taskservice.persistence.converter.RequirementWriteConverter;
import com.gznznzjsn.taskservice.persistence.converter.TaskReadConverter;
import com.gznznzjsn.taskservice.persistence.converter.TaskWriteConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

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

    @Bean
    public ReactiveHashOperations<String, String, Task> taskHashOps(ReactiveRedisConnectionFactory redisConnectionFactory) {
        ReactiveRedisTemplate<String, Task> template = new ReactiveRedisTemplate<>(
                redisConnectionFactory,
                RedisSerializationContext.<String, Task>newSerializationContext(new StringRedisSerializer())
                        .hashKey(new GenericToStringSerializer<>(String.class))
                        .hashValue(new Jackson2JsonRedisSerializer<>(Task.class))
                        .build()
        );
        return template.opsForHash();
    }

    @Bean
    public ReactiveHashOperations<String, String, List<Requirement>> requirementsByTaskHashOps(ReactiveRedisConnectionFactory redisConnectionFactory) {
        ReactiveRedisTemplate<String, List<Requirement>> template = new ReactiveRedisTemplate<>(
                redisConnectionFactory,
                RedisSerializationContext.<String, List<Requirement>>newSerializationContext(new StringRedisSerializer())
                        .hashKey(new GenericToStringSerializer<>(String.class))
                        .hashValue(new Jackson2JsonRedisSerializer<>(List.class))
                        .build()
        );
        return template.opsForHash();
    }

}
