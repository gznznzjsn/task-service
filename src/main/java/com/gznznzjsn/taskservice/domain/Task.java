package com.gznznzjsn.taskservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("tasks")
public class Task {

    @Id
    private String id;
    private String name;
    private Integer duration;
    private BigDecimal costPerHour;
    private Specialization specialization;

}
