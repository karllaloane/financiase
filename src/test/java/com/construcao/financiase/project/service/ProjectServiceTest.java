package com.construcao.financiase.project.service;

import com.construcao.financiase.project.builder.ProjectDTOBuilder;
import com.construcao.financiase.project.dto.ProjectDTO;
import com.construcao.financiase.project.entity.Project;
import com.construcao.financiase.project.exception.ProjectAlreadyExistsException;
import com.construcao.financiase.project.mapper.ProjectMapper;
import com.construcao.financiase.project.repository.ProjectRepository;
import com.construcao.financiase.user.dto.AuthenticatedUser;
import com.construcao.financiase.user.entity.User;
import com.construcao.financiase.user.mapper.UserMapper;
import com.construcao.financiase.user.service.UserService;
import com.construcao.financiase.users.builder.UserDTOBuilder;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
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

    private final UserMapper userMapper = UserMapper.INSTANCE;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private ProjectService projectService;

    private ProjectDTOBuilder projectDTOBuilder;

    private AuthenticatedUser authenticatedUser;

    @BeforeEach
    void setUp() {
        projectDTOBuilder = ProjectDTOBuilder.builder().build();
        UserDTOBuilder userDTOBuilder = UserDTOBuilder.builder().build();
        User user = userMapper.toModel(userDTOBuilder.buildUserDTO());
        authenticatedUser = new AuthenticatedUser(user);
    }

    @Test
    void whenNewProjectIsInformedThenItShouldBeCreated() {

        //given
        ProjectDTO projectToCreateDTO = projectDTOBuilder.buildProjectDTO();
        Project createdProject = projectMapper.toModel(projectToCreateDTO);

        //when
        Mockito.when(projectRepository.save(createdProject)).thenReturn(createdProject);
        Mockito.when(projectRepository.findByTitle(projectToCreateDTO.getTitle())).thenReturn(Optional.empty());

        ProjectDTO createdProjectDTO = projectService.create(authenticatedUser, projectToCreateDTO);

        //then
        MatcherAssert.assertThat(createdProjectDTO, Matchers.is(IsEqual.equalTo(projectToCreateDTO)));

    }

    @Test
    void whenExistingProjectIsInformedThenExceptionShouldBeThrown() {

        //given
        ProjectDTO projectToCreateDTO = projectDTOBuilder.buildProjectDTO();
        Project createdProject = projectMapper.toModel(projectToCreateDTO);

        //when
        Mockito.when(projectRepository.findByTitle(projectToCreateDTO.getTitle()))
                .thenReturn(Optional.of(createdProject));

        Assertions.assertThrows(ProjectAlreadyExistsException.class, () -> projectService.create(authenticatedUser, projectToCreateDTO));

    }
}
