package com.construcao.financiase.project.dto;

import com.construcao.financiase.project.enums.Category;
import com.construcao.financiase.project.enums.Status;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDTO {

    private Long id;

    @NotNull
    @NotEmpty
    @Size(max = 255)
    private String title;

    @NotNull
    @NotEmpty
    @Size(max = 255)
    private String subtitle;

    @NotNull
    @NotEmpty
    @Size(max = 255)
    private String description;

    @NotNull
    @NotEmpty
    private Category category;

    @NotNull
    @NotEmpty
    private Status status;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

}
