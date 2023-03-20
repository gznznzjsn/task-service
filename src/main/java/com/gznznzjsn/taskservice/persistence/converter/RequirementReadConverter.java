package com.gznznzjsn.taskservice.persistence.converter;

import com.gznznzjsn.taskservice.domain.ConsumableType;
import com.gznznzjsn.taskservice.domain.Requirement;
import com.gznznzjsn.taskservice.domain.Specialization;
import com.gznznzjsn.taskservice.domain.Task;
import org.bson.Document;
import org.bson.types.Decimal128;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.util.ArrayList;

@ReadingConverter
public class RequirementReadConverter implements Converter<Document, Requirement> {

    @Override
    public Requirement convert(Document document) {
        Document taskDoc = (Document) ((ArrayList<?>) document.get("task")).get(0);
        return Requirement.builder()
                .id(document.getObjectId("_id").toString())
                .task(Task.builder()
                        .id(taskDoc.getObjectId("_id").toString())
                        .name(taskDoc.getString("name"))
                        .specialization(Specialization.valueOf(taskDoc.getString("specialization")))
                        .duration(taskDoc.getInteger("duration"))
                        .costPerHour(taskDoc.get("cost_per_hour", Decimal128.class).bigDecimalValue())
                        .build())
                .consumableType(ConsumableType.builder()
                        .id(document.get("consumable_type_id", Long.class))
                        .build())
                .requiredQuantity(document.get("required_quantity", Long.class))
                .build();
    }

}