package com.gznznzjsn.taskservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("requirements")
public class Requirement {

    @Id
    @Column("requirement_id")
    private Long id;
    private Task task;
    private ConsumableType consumableType;
    private Long requiredQuantity;

}
