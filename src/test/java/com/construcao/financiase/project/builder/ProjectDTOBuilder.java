package com.construcao.financiase.project.builder;

import com.construcao.financiase.project.dto.ProjectDTO;
import com.construcao.financiase.project.enums.Category;
import com.construcao.financiase.project.enums.Status;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public class ProjectDTOBuilder {

    @Builder.Default
    private final Long id = 4L;

    @Builder.Default
    private final String title = "Meu projeto";

    @Builder.Default
    private final String subtitle = "Meu projeto";

    @Builder.Default
    private final String description = "Meu projeto";

    @Builder.Default
    private final Category category = Category.ARTS;

    @Builder.Default
    private final Status status = Status.ACTIVE;

    @Builder.Default
    private final LocalDate startDate = LocalDate.of(2023, 9, 1);

    @Builder.Default
    private final LocalDate endDate = LocalDate.of(2023, 10, 1);

    public ProjectDTO buildProjectDTO(){
        return new ProjectDTO(id, title, subtitle, description, category, status, startDate, endDate);
    }
}
