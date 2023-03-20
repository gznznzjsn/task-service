package com.gznznzjsn.taskservice.web.dto.mapper;


import com.gznznzjsn.taskservice.domain.Task;
import com.gznznzjsn.taskservice.web.dto.TaskDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskDto toDto(Task entity);

    Task toEntity(TaskDto dto);

}
