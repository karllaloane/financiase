package com.construcao.financiase.user.entity;

import com.construcao.financiase.project.entity.Project;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private List<Project> projects;
}
