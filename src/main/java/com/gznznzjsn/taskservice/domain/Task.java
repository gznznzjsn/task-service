package com.gznznzjsn.taskservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("tasks")
public class Task {

    @Id
    @Column("task_id")
    private Long id;
    private String name;
    private int duration;
    private BigDecimal costPerHour;
    private Specialization specialization;

}
