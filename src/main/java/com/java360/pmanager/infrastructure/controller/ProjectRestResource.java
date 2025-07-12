package com.java360.pmanager.infrastructure.controller;

import com.java360.pmanager.domain.applicationService.ProjectService;
import com.java360.pmanager.domain.entity.Project;
import com.java360.pmanager.infrastructure.dto.ProjectDTO;
import com.java360.pmanager.infrastructure.dto.SaveProjectDataDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

import static com.java360.pmanager.infrastructure.controller.RestConstants.PATH_PROJECTS;

@RestController // Anotação para indicar que esta classe é um controlador REST
@RequestMapping(PATH_PROJECTS) // Anotação para mapear a URL base
@RequiredArgsConstructor
@SuppressWarnings("unused") // Suprime avisos de código não utilizado
public class ProjectRestResource {

    private final ProjectService projectService;

    @PostMapping // Mapeia o método para responder a requisições POST na URL /projects
    public ResponseEntity<ProjectDTO> createProject(@RequestBody @Valid SaveProjectDataDTO saveProjectDataDTO) {
        Project project = projectService.createProject(saveProjectDataDTO);

        return ResponseEntity // Cria uma resposta HTTP
                .created(URI.create(PATH_PROJECTS + "/" + project.getId()))
                .body(ProjectDTO.create(project));
    }

    @GetMapping("/{id}") // Mapeia o método para responder a requisições GET na URL /projects/{id}
    public ResponseEntity<ProjectDTO> loadProject(@PathVariable("id") String projectId) {
        UUID uuid = UUID.fromString(projectId); // Conversão segura
        Project project = projectService.loadProject(uuid);
        return ResponseEntity.ok(ProjectDTO.create(project));
    }

    @DeleteMapping("/{id}") // Mapeia o método para responder a requisições DELETE na URL /projects/{id}
    public ResponseEntity<Void> deleteProject(@PathVariable("id") String projectId) {
        UUID uuid = UUID.fromString(projectId); // Conversão segura
        projectService.deleteProject(uuid);
        return ResponseEntity.noContent().build(); // Retorna uma resposta 204 No Content
    }

    @PutMapping("/{id}") // Mapeia o método para responder a requisições PUT na URL /projects/{id}
    public ResponseEntity<ProjectDTO> updateProject(
            @PathVariable("id") String projectId,
            @RequestBody @Valid SaveProjectDataDTO saveProjectDataDTO) {
        UUID uuid = UUID.fromString(projectId); // Conversão segura
        Project project = projectService.updateProject(uuid, saveProjectDataDTO);
        return ResponseEntity.ok(ProjectDTO.create(project));
    }
}
