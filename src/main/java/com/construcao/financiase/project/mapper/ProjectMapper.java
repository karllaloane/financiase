package com.construcao.financiase.project.mapper;

import com.construcao.financiase.project.dto.ProjectDTO;
import com.construcao.financiase.project.entity.Project;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProjectMapper {

    ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);

    Project toModel(ProjectDTO projectDTO);

    ProjectDTO toDTO(Project project);

}
