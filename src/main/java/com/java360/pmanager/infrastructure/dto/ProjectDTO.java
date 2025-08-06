package com.java360.pmanager.infrastructure.dto;

import com.java360.pmanager.domain.entity.Project;
import com.java360.pmanager.domain.model.ProjectStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static java.util.stream.Collectors.toSet;

@Data
public class ProjectDTO { // DTO (Data Transfer Object) para transferir dados de um projeto
    private  final UUID id;
    private final String name;
    private final String description;
    private final LocalDate initialDate;
    private final LocalDate finalDate;
    private final ProjectStatus status;
    private final Set<String> memberIds; // Conjunto de IDs dos membros associados ao projeto

    public static ProjectDTO create(Project project) { // Método estático para criar um ProjectDTO a partir de um objeto Project
        return new ProjectDTO(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getInitialDate(),
                project.getFinalDate(),
                project.getStatus(),
                Optional.ofNullable(project.getMembers()) // Verifica se a lista de membros é nula
                        .orElse(List.of())
                        .stream() // Verifica se a lista de membros é nula e, se for, usa uma lista vazia
                        .map(member -> member.getId().toString()) // Converter o UUID para String
                        .collect(toSet()) // Coletar os IDs dos projetos em um Set de Strings
        );
    }
}
