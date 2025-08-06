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

    public static TaskDTO create(Task task) { // Método estático para criar um TaskDTO a partir de um objeto Task
        return new TaskDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getNumberOfDays(),
                task.getStatus()
        );
    }
}
