package InovaRede.InovaRedeServer.service;

import InovaRede.InovaRedeServer.controller.dto.CreateProjectDto;
import InovaRede.InovaRedeServer.entity.Project;
import InovaRede.InovaRedeServer.repository.ProjectRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    
    private ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }
    
    public Optional<Project> getProjectById(String projectId) {
        return projectRepository.findById(UUID.fromString(projectId));
    }
    
    public UUID createProject(CreateProjectDto createProjectDto) {
        
        // DTO -> Entity
        var project = new Project(
                null,
                createProjectDto.name(),
                createProjectDto.description(),
                createProjectDto.start_date(),
                createProjectDto.end_date(),
                createProjectDto.image()
        );
        
        var projectSaved = projectRepository.save(project); // Salva o projeto no banco de dados
        return projectSaved.getProjectId(); // Retorna o UUID do projeto
    }
    
    public List<Project> listProjects() {
        return projectRepository.findAll();
    }
    
}
