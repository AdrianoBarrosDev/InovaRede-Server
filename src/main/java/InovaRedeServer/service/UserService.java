package InovaRedeServer.service;

import InovaRedeServer.dto.AssociateUserProjectDto;
import InovaRedeServer.dto.CreateUserDto;
import InovaRedeServer.dto.UpdateUserDto;
import InovaRedeServer.dto.UserProjectResponseDto;
import InovaRedeServer.entity.User;
import InovaRedeServer.entity.UserProject;
import InovaRedeServer.entity.UserProjectId;
import InovaRedeServer.exception.UsernameAlreadyExistsException;
import InovaRedeServer.repository.ProjectRepository;
import InovaRedeServer.repository.UserProjectRepository;
import InovaRedeServer.repository.UserRepository;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {
    
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private ProjectRepository projectRepository;
    private UserProjectRepository associateRepository;

    public UserService(UserRepository userRepository, ProjectRepository projectRepository, UserProjectRepository associateRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.associateRepository = associateRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    public UUID createUser(CreateUserDto createUserDto) {
        
        // Verifica se o username já existe no banco
        if (userRepository.findByUsername(createUserDto.username()).isPresent()) {
            throw new UsernameAlreadyExistsException("Username '" + createUserDto.username() + "' já está em uso.");
        }
        
        // DTO -> Entity
        var entity = new User(
                null, 
                createUserDto.username(),
                createUserDto.name(),
                createUserDto.email(), 
                passwordEncoder.encode(createUserDto.password()), // Senha criptografada com BCrypt
                createUserDto.course(),
                createUserDto.image(),
                Instant.now(), 
                null,
                null);
        
        var usedSaved = userRepository.save(entity); // Salva o usuário no banco de dados
        return usedSaved.getUserId(); // Retorna o UUID do usuário
        
    }
    
    public boolean authenticate(String username, String rawPassword) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if(userOpt.isEmpty()) return false;
        
        User user = userOpt.get();
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }
    
    public Optional<User> getUserById(String userId) {
        return userRepository.findById(UUID.fromString(userId));
    }
    
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    public List<User> listUsers() {
        return userRepository.findAll();
    }
    
    public void updateUserById(String userId, UpdateUserDto updateUserDto) {
        
        // Transforma o userId para UUID, para conseguir ser analizado pelo repository
        var id = UUID.fromString(userId);
        
        // Verifica se o usuário existe
        var userEntity = userRepository.findById(id);
        
        if(userEntity.isPresent()) { // Se o usuário existe
            var user = userEntity.get();
            
            // Atualiza todos os atributos que não estão nulos no updateUserDto
            if(updateUserDto.username() != null) {
                user.setUsername(updateUserDto.username());
            }
            if(updateUserDto.name() != null) {
                user.setName(updateUserDto.name());
            }
            if(updateUserDto.email() != null) {
                user.setEmail(updateUserDto.email());
            }
            if(updateUserDto.course() != null) {
                user.setCourse(updateUserDto.course());
            }
            if(updateUserDto.image() != null) {
                user.setImage(updateUserDto.image());
            }
            
            // Atualiza as alterações no banco de dados
            userRepository.save(user);
        }
    }
    
    public void deleteById(String userId) {
        var id = UUID.fromString(userId);
        var userExists = userRepository.existsById(id);
        if(userExists) {
            userRepository.deleteById(id);
        }
    }

    public void associateProject(String userId, AssociateUserProjectDto dto) {
        
        // Validação
        var user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        
        var project = projectRepository.findById(UUID.fromString(dto.projectId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        
        // DTO -> Entity
        var id = new UserProjectId(user.getUserId(), project.getProjectId());
        var entity = new UserProject(
                id,
                user,
                project
        );
        
        // Salva no banco de dados
        associateRepository.save(entity);
        
    }
    
    public List<UserProjectResponseDto> listProjects(String userId) {
        
        // Validação
        var user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        
        return user.getUserProject()
                .stream()
                .map(as -> new UserProjectResponseDto(as.getProject().getProjectId().toString(), as.getProject().getName(), as.getProject().getDescription(), as.getProject().getStart_date(), as.getProject().getEnd_date(), as.getProject().getImage()))
                .toList();
        
    }
    
}
