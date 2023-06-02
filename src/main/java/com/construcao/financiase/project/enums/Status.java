package com.construcao.financiase.project.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {

    PENDING("Pendente"),
    ACTIVE("Ativo"),
    PAUSED("Pausado"),
    CLOSED("Encerrado");

    private String description;
}
