package com.construcao.financiase.project.service;

import com.construcao.financiase.project.dto.ProjectDTO;
import com.construcao.financiase.project.entity.Project;
import com.construcao.financiase.project.mapper.ProjectMapper;
import com.construcao.financiase.project.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    private final static ProjectMapper projectMapper = ProjectMapper.INSTANCE;

    private ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public ProjectDTO create(ProjectDTO projectDTO){

        Project projectToCreate = projectMapper.toModel(projectDTO);
        Project createdProject = projectRepository.save(projectToCreate);

        return projectMapper.toDTO(createdProject);
    }

}
