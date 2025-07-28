package com.java360.pmanager.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity // Anota que a classe como uma entidade JPA
@Table(name = "member") // Define o nome da tabela no banco de dados
@Data // Gera automaticamente os métodos getters, setters, toString, equals e hashCode
@NoArgsConstructor // Gera um construtor sem parâmetros
@AllArgsConstructor // Gera um construtor com todos os parâmetros
@Builder // Permite a criação de instâncias da classe usando o padrão Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, length = 36)
    private UUID id;

    @Column(name = "secret", nullable = false, length = 36)
    private String secret;

    @Column(name = "name", nullable = false, length = 80)
    private String name;

    @Column(name = "email", nullable = false, length = 80)
    private String email;

    @Column(name = "delete", nullable = false)
    private Boolean deleted;

    @ManyToMany(mappedBy = "members") // Define um relacionamento muitos-para-muitos com a entidade Project
    private List<Project> projects; // Lista de projetos associados ao membro
}
