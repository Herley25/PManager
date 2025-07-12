package com.java360.pmanager.infrastructure.dto;

import com.java360.pmanager.domain.entity.Project;
import com.java360.pmanager.domain.model.ProjectStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class ProjectDTO { // DTO (Data Transfer Object) para transferir dados de um projeto
    private  final UUID id;
    private final String name;
    private final String description;
    private final LocalDate initialDate;
    private final LocalDate finalDate;
    private final ProjectStatus status;

    public static ProjectDTO create(Project project) { // Método estático para criar um ProjectDTO a partir de um objeto Project
        return new ProjectDTO(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getInitialDate(),
                project.getFinalDate(),
                project.getStatus()
        );
    }
}
