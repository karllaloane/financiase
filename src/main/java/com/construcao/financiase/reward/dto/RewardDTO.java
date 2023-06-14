package com.construcao.financiase.reward.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RewardDTO {

    @NotNull
    @NotEmpty
    @Size(max = 255)
    private String description;

    @NotNull
    @Positive
    @Min(value = 1)
    private Integer minValue;

}
