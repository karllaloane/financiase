package com.construcao.financiase.project.dto;

import com.construcao.financiase.project.enums.Category;
import com.construcao.financiase.project.enums.Status;
import jakarta.validation.constraints.*;
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
    @Size(max = 100)
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
    private Category category;

    @NotNull
    private Status status;

    @NotNull
    @FutureOrPresent
    private LocalDate startDate;

    @NotNull
    @Future
    private LocalDate endDate;

}
