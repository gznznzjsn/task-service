package com.gznznzjsn.taskservice.persistence.converter;

import com.gznznzjsn.taskservice.domain.Specialization;
import com.gznznzjsn.taskservice.domain.Task;
import org.bson.Document;
import org.bson.types.Decimal128;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class TaskReadConverter implements Converter<Document, Task> {

    @Override
    public Task convert(Document document) {
        return Task.builder()
                .id(document.getObjectId("_id").toString())
                .specialization(Specialization.valueOf(document.get("specialization", String.class)))
                .duration(document.getInteger("duration"))
                .costPerHour(document.get("cost_per_hour", Decimal128.class).bigDecimalValue())
                .build();
    }

}
