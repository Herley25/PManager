package com.java360.pmanager.infrastructure.dto;

import com.java360.pmanager.domain.entity.Task;
import com.java360.pmanager.domain.model.TaskStatus;
import lombok.Data;

import java.util.UUID;

@Data // Anotação Lombok para gerar getters, setters, toString, equals e hashCode
public class TaskDTO { // Classe DTO (Data Transfer Object) para representar uma tarefa
    private final UUID id;
    private final String title;
    private final String description;
    private final Integer numberOfDays;
    private final TaskStatus status;
    private final ProjectDTO project; // Referência ao projeto ao qual a tarefa pertence, representado como um ProjectDTO
    private final MemberDTO assignedMember; // Referência ao membro atribuído à tarefa, representado como um MemberDTO

    public static TaskDTO create(Task task) { // Método estático para criar um TaskDTO a partir de um objeto Task
        return new TaskDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getNumberOfDays(),
                task.getStatus(),
                ProjectDTO.create(task.getProject()), // Cria um ProjectDTO a partir do projeto da tarefa
                MemberDTO.create(task.getAssignedMember()) // Cria um MemberDTO a partir do membro atribuído à tarefa
        );
    }
}
