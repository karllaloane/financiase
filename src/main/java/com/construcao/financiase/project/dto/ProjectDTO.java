package com.construcao.financiase.project.dto;

import com.construcao.financiase.project.enums.Category;
import com.construcao.financiase.project.enums.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    @Enumerated(EnumType.STRING)
    private Category category;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull
    @FutureOrPresent
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/m/aaaa")
    private LocalDate startDate;

    @NotNull
    @Future
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/m/aa")
    private LocalDate endDate;

}
