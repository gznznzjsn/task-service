package com.gznznzjsn.taskservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("requirements")
public class Requirement {

    @Id
    private String id;
    private Task task;
    private ConsumableType consumableType;
    private Long requiredQuantity;

}
