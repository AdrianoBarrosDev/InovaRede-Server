package InovaRede.InovaRedeServer.controller;

import InovaRede.InovaRedeServer.controller.dto.CreateProjectDto;
import InovaRede.InovaRedeServer.entity.Project;
import InovaRede.InovaRedeServer.entity.User;
import InovaRede.InovaRedeServer.service.ProjectService;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@CrossOrigin(origins = "http://localhost:5173", exposedHeaders = "Location")
@RestController
@RequestMapping("/projects")
public class ProjectController {
    
    private ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }
    
    // Método para listar todos os projetos
    @GetMapping
    public ResponseEntity<List<Project>> listProjects() {
        var projects = projectService.listProjects();
        return ResponseEntity.ok(projects);
    }
    
    // Método para buscar projeto por id
    @GetMapping("/{projectId}")
    public ResponseEntity<Project> getProjectById(@PathVariable("projectId") String projectId) {
        var project = projectService.getProjectById(projectId);
        
        if(project.isPresent()) {
            return ResponseEntity.ok(project.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Método para criar projeto
    @PostMapping
    public ResponseEntity<User> createProject(@RequestBody CreateProjectDto createProjectDto) {
        UUID projectId = projectService.createProject(createProjectDto);
    
        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(projectId)
            .toUri();

        return ResponseEntity.created(location).header("Access-Control-Expose-Headers", "Location").build();
    }
    
}
