package com.construcao.financiase.project.controller;

import com.construcao.financiase.project.dto.ProjectDTO;
import com.construcao.financiase.project.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public ProjectDTO create(@RequestBody @Valid ProjectDTO projectDTO) {

        //anotacoes no parâmetro do método
        //@RequestBody: a operação virá de um corpo
        //@Valid: validações dos atributos bean validation

        return projectService.create(projectDTO);
    }
}
