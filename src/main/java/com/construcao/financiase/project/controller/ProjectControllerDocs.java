package com.construcao.financiase.project.controller;

import com.construcao.financiase.project.dto.ProjectDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Projects Management")
public interface ProjectControllerDocs {

    @Operation(summary = "Project creation operation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success project creating"),
            @ApiResponse(responseCode = "400", description = "Missing required fields, wrong field range value" +
                    "project name already registered")
    })
    ProjectDTO create(ProjectDTO projectDTO);
}
