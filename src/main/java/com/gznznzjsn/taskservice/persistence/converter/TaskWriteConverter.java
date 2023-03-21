package com.gznznzjsn.taskservice.persistence.converter;

import com.gznznzjsn.taskservice.domain.Task;
import org.bson.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class TaskWriteConverter implements Converter<Task, Document> {

    @Override
    public Document convert(Task task) {
        Document obj = new Document();
        obj.put("specialization", task.getSpecialization());
        obj.put("name", task.getName());
        obj.put("duration", task.getDuration());
        obj.put("cost_per_hour", task.getCostPerHour());
        obj.remove("_class");
        return obj;
    }

}
