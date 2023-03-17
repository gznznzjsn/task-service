package com.gznznzjsn.taskservice.persistence.converter;

import com.gznznzjsn.taskservice.domain.ConsumableType;
import com.gznznzjsn.taskservice.domain.Requirement;
import com.gznznzjsn.taskservice.domain.Specialization;
import com.gznznzjsn.taskservice.domain.Task;
import io.r2dbc.spi.Row;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.math.BigDecimal;

@ReadingConverter
public class RequirementReadConverter implements Converter<Row, Requirement> {

    @Override
    public Requirement convert(Row source) {
        return Requirement.builder()
                .id(source.get("requirement_id", Long.class))
                .task(Task.builder()
                        .id(source.get("task_id", Long.class))
                        .specialization(Specialization.valueOf(source.get("task_specialization", String.class)))
                        .name(source.get("task_name", String.class))
                        .duration(source.get("task_duration", Integer.class))
                        .costPerHour(source.get("task_cost_per_hour", BigDecimal.class))
                        .build())
                .consumableType(ConsumableType.builder()
                        .id(source.get("consumable_type_id", Long.class))
                        .build())
                .requiredQuantity(source.get("required_quantity", Long.class))
                .build();
    }
}