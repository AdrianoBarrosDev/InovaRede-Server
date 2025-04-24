package InovaRede.InovaRedeServer.service;

import InovaRede.InovaRedeServer.controller.dto.CreateProjectDto;
import InovaRede.InovaRedeServer.entity.Project;
import InovaRede.InovaRedeServer.entity.User;
import InovaRede.InovaRedeServer.repository.ProjectRepository;
import InovaRede.InovaRedeServer.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    
    private ProjectRepository projectRepository;
    private UserRepository userRepository;

    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }
    
    public Optional<Project> getProjectById(String projectId) {
        return projectRepository.findById(UUID.fromString(projectId));
    }
    
    public UUID createProject(CreateProjectDto createProjectDto) {
        
        User owner = userRepository.findById(createProjectDto.ownerId()).orElse(null);
        
        // DTO -> Entity
        var project = new Project(
                null,
                createProjectDto.name(),
                createProjectDto.description(),
                createProjectDto.start_date(),
                createProjectDto.end_date(),
                createProjectDto.image(),
                createProjectDto.course(),
                owner
        );
        
        var projectSaved = projectRepository.save(project); // Salva o projeto no banco de dados
        return projectSaved.getProjectId(); // Retorna o UUID do projeto
    }
    
    public List<Project> listProjects() {
        return projectRepository.findAll();
    }
    
}
