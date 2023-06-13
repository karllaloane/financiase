package com.construcao.financiase.project.service;

import com.construcao.financiase.project.builder.ProjectDTOBuilder;
import com.construcao.financiase.project.dto.ProjectDTO;
import com.construcao.financiase.project.entity.Project;
import com.construcao.financiase.project.exception.ProjectAlreadyExistsException;
import com.construcao.financiase.project.mapper.ProjectMapper;
import com.construcao.financiase.project.repository.ProjectRepository;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {

    private final ProjectMapper projectMapper = ProjectMapper.INSTANCE;

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectService projectService;

    private ProjectDTOBuilder projectDTOBuilder;

    @BeforeEach
    void setUp() {
        projectDTOBuilder = ProjectDTOBuilder.builder().build();
        ProjectDTO projectDTO = projectDTOBuilder.buildProjectDTO();
    }

    @Test
    void whenNewProjectIsInformedThenItShouldBeCreated() {

        //given
        ProjectDTO projectToCreateDTO = projectDTOBuilder.buildProjectDTO();
        Project createdProject = projectMapper.toModel(projectToCreateDTO);

        //when
        Mockito.when(projectRepository.save(createdProject)).thenReturn(createdProject);
        Mockito.when(projectRepository.findByTitle(projectToCreateDTO.getTitle())).thenReturn(Optional.empty());

        ProjectDTO createdProjectDTO = projectService.create(projectToCreateDTO);

        //then
        MatcherAssert.assertThat(createdProjectDTO, Is.is(IsEqual.equalTo(projectToCreateDTO)));

    }

    @Test
    void whenExistingProjectIsInformedThenExceptionShouldBeThrown() {

        //given
        ProjectDTO projectToCreateDTO = projectDTOBuilder.buildProjectDTO();
        Project createdProject = projectMapper.toModel(projectToCreateDTO);

        //when
        Mockito.when(projectRepository.findByTitle(projectToCreateDTO.getTitle()))
                .thenReturn(Optional.of(createdProject));

        Assertions.assertThrows(ProjectAlreadyExistsException.class, () -> projectService.create(projectToCreateDTO));

    }
}
