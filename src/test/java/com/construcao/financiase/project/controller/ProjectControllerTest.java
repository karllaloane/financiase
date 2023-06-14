package com.construcao.financiase.project.controller;

import com.construcao.financiase.project.builder.ProjectDTOBuilder;
import com.construcao.financiase.project.dto.ProjectDTO;
import com.construcao.financiase.project.entity.Project;
import com.construcao.financiase.project.mapper.ProjectMapper;
import com.construcao.financiase.project.service.ProjectService;
import com.construcao.financiase.user.dto.AuthenticatedUser;
import com.construcao.financiase.user.mapper.UserMapper;
import com.construcao.financiase.utils.JsonConversionUtils;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ProjectControllerTest {

    private static final String PROJECT_API_URL_PATH = "/api/v1/projects";

    private static final ProjectMapper projectMapper = ProjectMapper.INSTANCE;

    private final UserMapper userMapper = UserMapper.INSTANCE;

    @Mock
    private ProjectService projectService;

    @InjectMocks
    private ProjectController projectController;

    private MockMvc mockMvc;

    private ProjectDTOBuilder projectDTOBuilder;

    @BeforeEach
    void setUp() {
        projectDTOBuilder = ProjectDTOBuilder.builder().build();
        mockMvc = MockMvcBuilders.standaloneSetup(projectController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenPOSTIsCalledThenStatusCreatedShouldBeReturned() throws Exception {
        ProjectDTO createdProjectDTO = projectDTOBuilder.buildProjectDTO();
        Project createProject = projectMapper.toModel(createdProjectDTO);

        Mockito.when(projectService.create(ArgumentMatchers.any(AuthenticatedUser.class), ArgumentMatchers.eq(createdProjectDTO)))
                .thenReturn(createdProjectDTO);

        mockMvc.perform(MockMvcRequestBuilders.post(PROJECT_API_URL_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonConversionUtils.asJsonString(createdProjectDTO)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(createdProjectDTO.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Is.is(createdProjectDTO.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", Is.is(createdProjectDTO.getDescription())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.subtitle", Is.is(createdProjectDTO.getSubtitle())));
        //.andExpect(MockMvcResultMatchers.jsonPath("$.category", Is.is(createdProjectDTO.getCategory())));
        //.andExpect(MockMvcResultMatchers.jsonPath("$.status", Is.is(createdProjectDTO.getStatus())))
        //.andExpect(MockMvcResultMatchers.jsonPath("$.startDate", Is.is(createdProjectDTO.getStartDate())))
        //.andExpect(MockMvcResultMatchers.jsonPath("$.endDate", Is.is(createdProjectDTO.getEndDate())));
    }

    @Test
    void whenPOSTItsCalledWithoutRequiredFieldThenRequestStatusShoulBeInformed() throws Exception {
        ProjectDTO createdProjectDTO = projectDTOBuilder.buildProjectDTO();
        createdProjectDTO.setTitle(null);

        mockMvc.perform(MockMvcRequestBuilders.post(PROJECT_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonConversionUtils.asJsonString(createdProjectDTO)))
                .andExpect(status().isBadRequest());
    }

}
