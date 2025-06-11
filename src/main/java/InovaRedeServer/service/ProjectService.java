package InovaRedeServer.service;

import InovaRedeServer.dto.CreateProjectDto;
import InovaRedeServer.dto.ProjectWithParticipationDto;
import InovaRedeServer.entity.Project;
import InovaRedeServer.entity.User;
import InovaRedeServer.repository.ProjectRepository;
import InovaRedeServer.repository.UserProjectRepository;
import InovaRedeServer.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    
    private ProjectRepository projectRepository;
    private UserRepository userRepository;
    private UserProjectRepository userProjectRepository;

    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository, UserProjectRepository userProjectRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.userProjectRepository = userProjectRepository;
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
    
    public List<ProjectWithParticipationDto> findProjects(String name, String course, String username) {
        
        // Buscar usuário pelo username
        var userOptional = userRepository.findByUsername(username);
        UUID userId = userOptional.get().getUserId();

        // Buscar projetos conforme filtros
        List<Project> projetos;
        if (name != null && course != null) {
            projetos = projectRepository.findByNameContainingAndCourseContaining(name, course);
        } else if (name != null) {
            projetos = projectRepository.findByNameContaining(name);
        } else if (course != null) {
            projetos = projectRepository.findByCourseContaining(course);
        } else {
            projetos = projectRepository.findAll();
        }

        // Buscar todos os projetos que o usuário participa
        Set<UUID> projetosDoUsuario = userProjectRepository.findByUserUserId(userId).stream()
            .map(assoc -> assoc.getProject().getProjectId())
            .collect(Collectors.toSet());

        // Mapear os projetos para o DTO com a flag "participando"
        return projetos.stream()
            .map(p -> new ProjectWithParticipationDto(
                p.getProjectId(),
                p.getName(),
                p.getDescription(),
                p.getCourse(),
                p.getImage(),
                projetosDoUsuario.contains(p.getProjectId())
            ))
            .toList();
    }
    
}
