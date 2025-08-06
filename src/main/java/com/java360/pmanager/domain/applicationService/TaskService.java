package com.java360.pmanager.domain.applicationService;

import com.java360.pmanager.domain.entity.Member;
import com.java360.pmanager.domain.entity.Project;
import com.java360.pmanager.domain.entity.Task;
import com.java360.pmanager.domain.exception.InvalidTaskStatusException;
import com.java360.pmanager.domain.exception.TaskNotFoundException;
import com.java360.pmanager.domain.model.TaskStatus;
import com.java360.pmanager.domain.repository.TaskRepository;
import com.java360.pmanager.infrastructure.dto.SaveTaskDataDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service // Anotação para indicar que esta classe é um serviço Spring
@RequiredArgsConstructor // Anotação do Lombok para gerar um construtor com todos os campos finais
public class TaskService {

    private final ProjectService projectService; // Serviço para operações relacionadas a projetos
    private final MemberService memberService; // Serviço para operações relacionadas a membros
    private final TaskRepository taskRepository; // Repositório para operações de CRUD com tarefas

    @Transactional // Anotação do Spring Data para indicar que este método deve ser executado dentro de uma transação
    public Task createTask(SaveTaskDataDTO saveTaskDataDTO) { // Método para criar uma nova tarefa}

        Project project = null;
        UUID uuidProject = UUID.fromString(saveTaskDataDTO.getProjectId()); // Converte o ID do projeto de String para UUID
        UUID uuidMember = UUID.fromString(saveTaskDataDTO.getMemberId());

        if (!Objects.isNull(saveTaskDataDTO.getProjectId())) {
            project = projectService.loadProject(uuidProject);
        }

        Member member = null;
        if (!Objects.isNull(saveTaskDataDTO.getMemberId())) {
            member = memberService.loadMemberById(uuidMember);
        }

        Task task = Task.builder() // Cria uma nova instância de Task usando o builder
                .title(saveTaskDataDTO.getTitle())
                .description(saveTaskDataDTO.getDescription())
                .numberOfDays(saveTaskDataDTO.getNumberOfDays())
                .status(TaskStatus.PENDING)
                .project(project) // Define o projeto associado à tarefa
                .assignedMember(member) // Define o membro associado à tarefa
                .build();

        return taskRepository.save(task); // Salva a tarefa no repositório e retorna a tarefa salva
    }

    public Task loadTask(UUID taskId) { // Método para carregar uma tarefa pelo ID
        return taskRepository
                .findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId)); // Lança uma exceção se a tarefa não for encontrada
    }

    @Transactional // Anotação do Spring Data para indicar que este método deve ser executado dentro de uma transação
    public void deleteTask(UUID taskId) { // Método para deletar uma tarefa pelo ID
        Task task = loadTask(taskId); // Carrega a tarefa para verificar se existe
        taskRepository.delete(task); // Deleta a tarefa do repositório
    }

    public Task updateTask(UUID taskId, SaveTaskDataDTO saveTaskDataDTO) {
        Project project = null;
        UUID uuidProject = UUID.fromString(saveTaskDataDTO.getProjectId()); // Converte o ID do projeto de String para UUID
        UUID uuidMember = UUID.fromString(saveTaskDataDTO.getMemberId());

        if (!Objects.isNull(saveTaskDataDTO.getProjectId())) {
            project = projectService.loadProject(uuidProject);
        }

        Member member = null;
        if (!Objects.isNull(saveTaskDataDTO.getMemberId())) {
            member = memberService.loadMemberById(uuidMember);
        }

        Task task = loadTask(taskId); // Carrega a tarefa existente pelo ID

        // Atualiza os campos da tarefa com os dados do DTO
        task.setTitle(saveTaskDataDTO.getTitle());
        task.setDescription(saveTaskDataDTO.getDescription());
        task.setNumberOfDays(saveTaskDataDTO.getNumberOfDays());
        task.setStatus(convertToTaskStatus(saveTaskDataDTO.getStatus())); // Converte o status do DTO para o enum TaskStatus
        task.setProject(project); // Atualiza o projeto associado à tarefa
        task.setAssignedMember(member); // Atualiza o membro associado à tarefa

        // Salva as alterações no repositório
        taskRepository.save(task);
        return task; // Retorna a tarefa atualizada
    }

    private TaskStatus convertToTaskStatus(String status) {
        try {
            return TaskStatus.valueOf(status.toUpperCase()); // Converte a string para o enum TaskStatus
        } catch (IllegalArgumentException | NullPointerException e) { // Captura exceções se a string não corresponder a nenhum valor do enum
            throw new InvalidTaskStatusException(status); // Lança exceção se o status for inválido
        }
    }
}
