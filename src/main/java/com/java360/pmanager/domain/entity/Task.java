package com.java360.pmanager.domain.entity;

import com.java360.pmanager.domain.model.TaskStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity // Anotação para indicar que esta classe é uma entidade JPA
@Data // Anotação do Lombok para gerar getters, setters, toString, equals e hashCode
@Builder // Anotação do Lombok para permitir o uso do padrão Builder na criação de instâncias
@NoArgsConstructor // Anotação do Lombok para gerar um construtor sem parâmetros
@AllArgsConstructor // Anotação do Lombok para gerar um construtor com todos os parâmetros
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, length = 36)
    private UUID id;

    @Column(name = "title", nullable = false, length = 80)
    private String title;

    @Column(name = "description", nullable = false, length = 150)
    private String description;

    @Column(name = "number_of_days", nullable = false)
    private Integer numberOfDays;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @ManyToOne
    @JoinColumn(name = "project_id") // Define a coluna que referencia o projeto
    private Project project; // Referência ao projeto ao qual a tarefa pertence, pode ser uma relação @ManyToOne se necessário

    @ManyToOne
    @JoinColumn(name = "assigned_member") // Define a coluna que referencia o membro atribuído
    private Member assignedMember; // Referência ao membro atribuído à tarefa, pode ser uma relação @ManyToOne se necessário
}
