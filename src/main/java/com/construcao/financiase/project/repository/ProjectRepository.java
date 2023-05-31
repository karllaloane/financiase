package com.construcao.financiase.project.repository;

import com.construcao.financiase.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long>{
    Optional<Project> findByTitle(String title);
}
