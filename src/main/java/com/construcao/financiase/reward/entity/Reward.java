package com.construcao.financiase.reward.entity;

import com.construcao.financiase.entity.Auditable;
import com.construcao.financiase.project.entity.Project;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Reward  extends Auditable {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    private String description;

    @Column(nullable = false, columnDefinition = "DOUBLE PRECISION")
    private double minValue = 0.0;

    @Column(nullable = false, columnDefinition = "DOUBLE PRECISION")
    private double maxValue = 0.0;

    @ManyToOne(cascade = {CascadeType.MERGE})
    private Project project;

}
