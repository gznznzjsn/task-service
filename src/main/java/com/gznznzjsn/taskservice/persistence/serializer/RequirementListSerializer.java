package com.gznznzjsn.taskservice.persistence.serializer;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gznznzjsn.taskservice.domain.Requirement;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class RequirementListSerializer implements RedisSerializer<List<Requirement>> {

    private final Gson gson;

    public RequirementListSerializer() {
        this.gson = new Gson();
    }

    @Override
    public byte[] serialize(List<Requirement> t) throws SerializationException {
        return gson.toJson(t).getBytes();
    }

    @Override
    public List<Requirement> deserialize(byte[] bytes) throws SerializationException {
        Type type = new TypeToken<ArrayList<Requirement>>() {
        }.getType();
        String json = new String(bytes, StandardCharsets.UTF_8);
        return gson.fromJson(json, type);
    }

}
