package com.gznznzjsn.taskservice.web.dto.mapper;


import com.gznznzjsn.taskservice.domain.Task;
import com.gznznzjsn.taskservice.web.dto.TaskDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    Task toEntity(TaskDto dto);

    TaskDto toDto(Task entity);

}
