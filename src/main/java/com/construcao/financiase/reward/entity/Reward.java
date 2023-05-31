package com.construcao.financiase.reward.entity;

import com.construcao.financiase.project.entity.Project;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Reward {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    private String description;

    @Column(nullable = false, columnDefinition = "double default 0")
    private double minValue;

    @Column(nullable = false, columnDefinition = "double default 0")
    private double maxValue;

    @ManyToOne(cascade = {CascadeType.MERGE})
    private Project project;

}
