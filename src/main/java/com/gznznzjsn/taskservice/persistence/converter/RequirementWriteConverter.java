package com.gznznzjsn.taskservice.persistence.converter;

import com.gznznzjsn.taskservice.domain.Requirement;
import org.bson.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class RequirementWriteConverter implements Converter<Requirement, Document> {

    @Override
    public Document convert(Requirement requirement) {
        Document obj = new Document();
        obj.put("task_id", requirement.getTask().getId());
        obj.put("consumable_type_id", requirement.getConsumableType().getId());
        obj.put("required_quantity", requirement.getRequiredQuantity());
        obj.remove("_class");
        return obj;
    }

}
