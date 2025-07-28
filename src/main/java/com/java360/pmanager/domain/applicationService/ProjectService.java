package com.java360.pmanager.domain.applicationService;

import com.java360.pmanager.domain.entity.Member;
import com.java360.pmanager.domain.entity.Project;
import com.java360.pmanager.domain.exception.DuplicateProjectException;
import com.java360.pmanager.domain.exception.InvalidProjectStatusException;
import com.java360.pmanager.domain.exception.ProjectNotFoundException;
import com.java360.pmanager.domain.model.ProjectStatus;
import com.java360.pmanager.domain.repository.ProjectRepository;
import com.java360.pmanager.infrastructure.dto.SaveProjectDataDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor // Anotações do Lombok para injeção de dependência
@Slf4j // Anotação do Lombok para gerar um logger
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final MemberService memberService;

    @Transactional // Anotação do Spring Data para indicar que este método deve ser executado dentro de uma transação
    public Project createProject(SaveProjectDataDTO saveProjectDataDTO) { // Método para criar um novo projeto

        if (existsProjectWithName(saveProjectDataDTO.getName(), null)) {
            throw new DuplicateProjectException(saveProjectDataDTO.getName());
        }

        Project project = Project
                .builder()
                .name(saveProjectDataDTO.getName())
                .description(saveProjectDataDTO.getDescription())
                .initialDate(saveProjectDataDTO.getInitialDate())
                .finalDate(saveProjectDataDTO.getFinalDate())
                .status(ProjectStatus.PENDING)
                .build();

        // Salva o projeto no repositório e retorna o projeto salvo
        Project savedProject = projectRepository.save(project);

        // Adiciona os membros ao projeto, se houver IDs de membros fornecidos no DTO
        addMemberToProject(saveProjectDataDTO.getMemberIds(), project);

        log.info("Project created: {}", project); // Loga o ID do projeto criado
        return savedProject; // Retorna o projeto salvo com o ID gerado pelo banco de dados
    }

    public Project loadProject(UUID projectId) {
        return projectRepository
                .findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(projectId));
    }

    @Transactional // Anotação do Spring Data para indicar que este método deve ser executado dentro de uma transação
    public void deleteProject(UUID projectId) {
        Project project = loadProject(projectId); // Carrega o projeto pelo ID
        projectRepository.delete(project); // Deleta o projeto do repositório
        log.info("Project deleted: {}", projectId); // Loga o ID do projeto deletado
    }

    @Transactional
    public Project updateProject(UUID projectId, SaveProjectDataDTO saveProjectDataDTO) {
        if (existsProjectWithName(saveProjectDataDTO.getName(), projectId)) {
            throw new DuplicateProjectException(saveProjectDataDTO.getName());
        }

        Project project = loadProject(projectId); // Carrega o projeto pelo ID

        // Atualiza os campos do projeto com os dados do DTO
        project.setName(saveProjectDataDTO.getName());
        project.setDescription(saveProjectDataDTO.getDescription());
        project.setInitialDate(saveProjectDataDTO.getInitialDate());
        project.setFinalDate(saveProjectDataDTO.getFinalDate());
        project.setStatus(convertToProjectStatus(saveProjectDataDTO.getStatus())); // Atualiza o status do projeto

        // Adiciona os membros ao projeto, se houver IDs de membros fornecidos no DTO
        addMemberToProject(saveProjectDataDTO.getMemberIds(), project);

        // Salva as alterações no repositório e retorna o projeto atualizado
        // Project updatedProject = projectRepository.save(project);
        log.info("Project updated: {}", project);
        return project;
    }

    private ProjectStatus convertToProjectStatus(String statusStr) {
        try {
            return ProjectStatus.valueOf(statusStr);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new InvalidProjectStatusException(statusStr);
        }
    }

    private boolean existsProjectWithName(String name, UUID idToExclude) {
        return projectRepository
                .findByName(name)
                .filter(p -> !Objects.equals(p.getId(), idToExclude))
                .isPresent(); // Verifica se existe um projeto com o mesmo nome, excluindo o ID fornecido
    }

    private void addMemberToProject(Set<String> memberIds, Project project) {
        Set<String> memberIdsNotNull = Optional.ofNullable(memberIds).orElse(Set.of()); // Garante que memberIds não seja nulo, usando um Set vazio se for o caso

        List<Member> members = memberIdsNotNull.stream().map(
                memberId -> {
                    UUID uuid = UUID.fromString(memberId); // Converte o ID de String para UUID
                    return memberService.loadMemberById(uuid); // Carrega o membro pelo ID
                }) // Mapeia cada ID de membro para um objeto Member
                .collect(toList()); // Coleta os membros em uma lista

        project.setMembers(members); // Define os membros do projeto
    }
}
