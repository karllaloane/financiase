package com.construcao.financiase.project.controller;

import com.construcao.financiase.project.dto.ProjectDTO;
import com.construcao.financiase.project.service.ProjectService;
import com.construcao.financiase.reward.dto.RewardDTO;
import com.construcao.financiase.reward.entity.Reward;
import com.construcao.financiase.user.dto.AuthenticatedUser;
import com.construcao.financiase.user.entity.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController("ProjectController")
@RequestMapping("/api/v1/projects")
public class ProjectController implements ProjectControllerDocs{

    private ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectDTO create(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            @RequestBody @Valid ProjectDTO projectDTO) {

        return projectService.create(authenticatedUser, projectDTO);
    }

    @GetMapping("/{id}")
    public List<RewardDTO> getAllRewards(@PathVariable Long id) {
        return projectService.getAllRewards(id);
    }
}
