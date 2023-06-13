package com.construcao.financiase.project.exception;

import jakarta.persistence.EntityNotFoundException;

public class ProjectNotFoundException extends EntityNotFoundException {

    public ProjectNotFoundException(Long id) {
        super(String.format("Project with id %s not exists!", id));
    }
}
