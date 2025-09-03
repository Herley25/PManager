package com.java360.pmanager.domain.repository;

import com.java360.pmanager.domain.entity.Task;
import com.java360.pmanager.domain.model.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    // Busca tarefas de um membro em um projeto específico com status e título filtrados
    // Se o título for nulo, não aplica filtro por título
    // Ordena os resultados pela data de criação da tarefa em ordem decrescente
    @Query("""
                SELECT t FROM Task t
                WHERE (:projectId IS NULL OR t.project.id = :projectId)
                  AND (:memberId IS NULL OR t.assignedMember.id = :memberId)
                  AND (:status IS NULL OR t.status = :status)
                  AND (:title IS NULL OR UPPER(t.title) LIKE CONCAT('%', UPPER(:title), '%'))
            """)
//    List<Task> find(
//        @Param("projectId") UUID projectId,
//        @Param("memberId") UUID memberId,
//        @Param("status") TaskStatus status,
//        @Param("title") String title
//    );

    Page<Task> find(
            @Param("projectId") UUID projectId,
            @Param("memberId") UUID memberId,
            @Param("status") TaskStatus status,
            @Param("title") String title,
            Pageable pageable
    );
}
