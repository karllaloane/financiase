package com.construcao.financiase.project.service;

import com.construcao.financiase.project.dto.ProjectDTO;
import com.construcao.financiase.project.entity.Project;
import com.construcao.financiase.project.exception.ProjectAlreadyExistsException;
import com.construcao.financiase.project.exception.ProjectNotFoundException;
import com.construcao.financiase.project.mapper.ProjectMapper;
import com.construcao.financiase.project.repository.ProjectRepository;
import com.construcao.financiase.reward.dto.RewardDTO;
import com.construcao.financiase.reward.entity.Reward;
import com.construcao.financiase.reward.mapper.RewardMapper;
import com.construcao.financiase.user.dto.AuthenticatedUser;
import com.construcao.financiase.user.entity.User;
import com.construcao.financiase.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    private final static ProjectMapper projectMapper = ProjectMapper.INSTANCE;

    private final static RewardMapper rewardMapper = RewardMapper.INSTANCE;

    private ProjectRepository projectRepository;

    private UserService userService;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, UserService userService) {
        this.projectRepository = projectRepository;
        this.userService = userService;
    }

    public ProjectDTO create(AuthenticatedUser authenticatedUser, ProjectDTO projectDTO){

        User foundAuthenticatedUser = userService.verifyAndGetUserIfExists(authenticatedUser.getUsername());

        verifyIfExists(projectDTO.getTitle());

        Project projectToCreate = projectMapper.toModel(projectDTO);
        projectToCreate.setOwner(foundAuthenticatedUser);

        Project createdProject = projectRepository.save(projectToCreate);

        return projectMapper.toDTO(createdProject);
    }

    private void verifyIfExists(String titleProject) {
        projectRepository.findByTitle(titleProject)
                .ifPresent(project -> {throw new ProjectAlreadyExistsException(project.getTitle()); });
    }

    public Project verifyAndGetProjectIfExists(Long id) {
        return projectRepository.findById(id)
                    .orElseThrow(() -> new ProjectNotFoundException(id));
    }

    public List<RewardDTO> getAllRewards(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(id));

        List<Reward> rewardList = project.getRewards();

        return rewardList.stream()
                .map(rewardMapper::toDTO)
                .collect(Collectors.toList());
    }
}
