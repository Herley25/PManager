package com.java360.pmanager.infrastructure.controller;

import com.java360.pmanager.domain.applicationService.ProjectService;
import com.java360.pmanager.domain.applicationService.TaskService;
import com.java360.pmanager.domain.entity.Project;
import com.java360.pmanager.domain.entity.Task;
import com.java360.pmanager.infrastructure.dto.ProjectDTO;
import com.java360.pmanager.infrastructure.dto.SaveProjectDataDTO;
import com.java360.pmanager.infrastructure.dto.SaveTaskDataDTO;
import com.java360.pmanager.infrastructure.dto.TaskDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

import static com.java360.pmanager.infrastructure.controller.RestConstants.PATH_PROJECTS;
import static com.java360.pmanager.infrastructure.controller.RestConstants.PATH_TASKS;

@RestController // Anotação para indicar que esta classe é um controlador REST
@RequestMapping(PATH_TASKS) // Anotação para mapear a URL base
@RequiredArgsConstructor
@SuppressWarnings("unused") // Suprime avisos de código não utilizado
public class TaskRestResource {

    private final TaskService taskService; // Serviço para operações de CRUD com tarefas

    @PostMapping // Mapeia o método para responder a requisições POST na URL /projects
    public ResponseEntity<TaskDTO> createTask(@RequestBody @Valid SaveTaskDataDTO saveTaskDataDTO) {
        Task task = taskService.createTask(saveTaskDataDTO);

        return ResponseEntity // Cria uma resposta HTTP
                .created(URI.create(PATH_TASKS + "/" + task.getId()))
                .body(TaskDTO.create(task));
    }

    @GetMapping("/{id}") // Mapeia o método para responder a requisições GET na URL /tasks/{id}
    public ResponseEntity<TaskDTO> loadTask(@PathVariable("id") String taskId) {
        UUID uuid = UUID.fromString(taskId); // Conversão segura do ID de String para UUID

        Task task = taskService.loadTask(uuid); // Carrega a tarefa pelo ID
        return ResponseEntity.ok(TaskDTO.create(task)); // Retorna a tarefa como DTO
    }

    @DeleteMapping("/{id}") // Mapeia o método para responder a requisições DELETE na URL /tasks/{id}
    public ResponseEntity<Void> deleteTask(@PathVariable("id") String taskId) {
        UUID uuid = UUID.fromString(taskId); // Conversão segura do ID de String para UUID
        taskService.deleteTask(uuid); // Deleta a tarefa pelo ID
        return ResponseEntity.noContent().build(); // Retorna uma resposta 204 No Content
    }

    @PutMapping("/{id}") // Mapeia o método para responder a requisições PUT na URL /tasks/{id}
    public ResponseEntity<TaskDTO> updateTask(
            @PathVariable("id") String taskId,
            @RequestBody @Valid SaveTaskDataDTO saveTaskDataDTO) { // Recebe os dados da tarefa a serem atualizados
        UUID uuid = UUID.fromString(taskId); // Conversão segura do ID de String para UUID
        Task task = taskService.updateTask(uuid, saveTaskDataDTO); // Atualiza a tarefa
        return ResponseEntity.ok(TaskDTO.create(task)); // Retorna a tarefa atualizada como DTO
    }
}
