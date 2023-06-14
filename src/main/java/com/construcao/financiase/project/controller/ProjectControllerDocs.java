package com.construcao.financiase.project.controller;

import com.construcao.financiase.project.dto.ProjectDTO;
import com.construcao.financiase.reward.dto.RewardDTO;
import com.construcao.financiase.user.dto.AuthenticatedUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Tag(name = "Projects Management")
public interface ProjectControllerDocs {

    @Operation(summary = "Project creation operation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success project creating"),
            @ApiResponse(responseCode = "400", description = "Missing required fields, wrong field range value" +
                    "project name already registered")
    })
    ProjectDTO create(AuthenticatedUser authenticatedUser, ProjectDTO projectDTO);

    @Operation(summary = "List all rewards of the project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returned all rewards")
    })
    List<RewardDTO> getAllRewards(Long id);
}
