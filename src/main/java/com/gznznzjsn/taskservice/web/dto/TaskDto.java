package com.gznznzjsn.taskservice.web.dto;

import com.gznznzjsn.taskservice.domain.Specialization;
import com.gznznzjsn.taskservice.web.dto.group.OnCreateTask;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

public record TaskDto(

        String id,

        @NotBlank(message = "Task name can't be blank!",groups = {OnCreateTask.class})
        @Length(max = 40, message = "Too long name!",groups = {OnCreateTask.class})
        String name,

        @NotBlank(message = "Task name can't be blank!",groups = {OnCreateTask.class})
        @Length(max = 40, message = "Too long specialization!",groups = {OnCreateTask.class})
        Specialization specialization,

        @NotBlank(message = "Task duration can't be blank!",groups = {OnCreateTask.class})
        @Positive(message = "Task duration must be positive!",groups = {OnCreateTask.class})
        Integer duration,

        @NotNull(message = "Cost per hour must be set!",groups = {OnCreateTask.class})
        @Positive(message = "Cost per hour must be positive!",groups = {OnCreateTask.class})
        BigDecimal costPerHour

) {
}