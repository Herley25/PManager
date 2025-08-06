package com.java360.pmanager.domain.entity;

import com.java360.pmanager.domain.model.ProjectStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity // Anotação do JPA que indica que esta classe é uma entidade persistente
@Table(name = "project") // Anotação do JPA que define o nome da tabela no banco de dados
@Data // Anotação do Lombok que gera getters, setters, toString, equals e hashCode
@Builder // Anotação do Lombok que permite usar o padrão Builder para criar instâncias da classe
@AllArgsConstructor // Anotação do Lombok que gera um construtor com todos os campos
@NoArgsConstructor // Anotação do Lombok que gera um construtor sem parâmetros
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // Gera um ID único para cada instância do projeto
    @Column(name = "id", nullable = false, length = 36) // Define a coluna ID como não nula e com tamanho máximo de 36 caracteres
    private UUID id;

    @Column(name = "name", nullable = false, length = 80) // Define a coluna name como não nula e com tamanho máximo de 100 caracteres
    private String name;

    @Column(name = "description", nullable = false, length = 150) // Define a coluna description como não nula e com tamanho máximo de 150 caracteres
    private String description;

    @Column(name = "initial_date", nullable = false) // Define a coluna initial_date como não nula
    private LocalDate initialDate;

    @Column(name = "final_date", nullable = false) // Define a coluna final_date como não nula
    private LocalDate finalDate;

    @Column(name = "status", nullable = false) // Define a coluna status como não nula
    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    @ManyToMany // Define um relacionamento muitos-para-muitos com a entidade Member
    @JoinTable(
            name = "project_member", // Nome da tabela de junção
            joinColumns = @JoinColumn(name = "project_id"), // Coluna que referencia o projeto
            inverseJoinColumns = @JoinColumn(name = "member_id") // Coluna que referencia o membro
    ) // Define a tabela de junção entre projetos e membros
    private List<Member> members; // Lista de membros associados ao projeto

    @OneToMany(mappedBy = "project") // Define um relacionamento um-para-muitos com a entidade Task
    private List<Task> tasks; // Lista de tarefas associadas ao projeto
}
