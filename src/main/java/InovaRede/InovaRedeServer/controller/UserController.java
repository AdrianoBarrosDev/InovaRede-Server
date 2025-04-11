package InovaRede.InovaRedeServer.controller;

import InovaRede.InovaRedeServer.controller.dto.AssociateUserProjectDto;
import InovaRede.InovaRedeServer.controller.dto.UpdateUserDto;
import InovaRede.InovaRedeServer.controller.dto.CreateUserDto;
import InovaRede.InovaRedeServer.controller.dto.UserProjectResponseDto;
import InovaRede.InovaRedeServer.entity.User;
import InovaRede.InovaRedeServer.service.UserService;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    // Método para criar usuário
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody CreateUserDto createUserDto) {
        var userId = userService.createUser(createUserDto);
        return ResponseEntity.created(URI.create("/users/" + userId.toString())).build();
    }
    
    // Método para consultar usuário pelo Id
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") String userId) {
        var user = userService.getUserById(userId);
        
        if(user.isPresent()) { // Se o usuário existir no banco de dados
            return ResponseEntity.ok(user.get());
        } else { // Se o usuário não existir, retorna o erro 404 na API
            return ResponseEntity.notFound().build();
        }
    }
    
    // Método para consultar usuário pelo username
    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable("username") String username) {
        var user = userService.getUserByUsername(username);
        
        if(user.isPresent()) { // Se o usuário existir no banco de dados
            return ResponseEntity.ok(user.get());
        } else { // Se o usuário não existir, retorna o erro 404 na API
            return ResponseEntity.notFound().build();
        }
    }
    
    // Método para listar todos os usuários
    @GetMapping
    public ResponseEntity<List<User>> listUsers() {
        var users = userService.listUsers();
        return ResponseEntity.ok(users);
    }
    
    // Método para atualizar usuário por Id
    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateUserById(@PathVariable("userId") String userId, @RequestBody UpdateUserDto updateUserDto) {
        userService.updateUserById(userId, updateUserDto);
        return ResponseEntity.noContent().build();
    }
    
    // Método para deletar usuário por Id
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteById(@PathVariable("userId") String userId) {
        userService.deleteById(userId);
        return ResponseEntity.noContent().build();
    }
    
    
    // Método para adicionar projeto ao usuário
    @PostMapping("/{userId}/projects")
    public ResponseEntity<Void> associateProjects(@PathVariable("userId") String userId, @RequestBody AssociateUserProjectDto dto) {
        userService.associateProject(userId, dto);
        return ResponseEntity.noContent().build();
    }
    
    // Método para listar projetos do usuário
    @GetMapping("/{userId}/projects")
    public ResponseEntity<List<UserProjectResponseDto>> listAssociateProjects(@PathVariable("userId") String userId) {
        var projects = userService.listProjects(userId);
        return ResponseEntity.ok(projects);
    }
    
}
