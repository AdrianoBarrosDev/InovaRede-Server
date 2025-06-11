package InovaRedeServer.service;

import InovaRedeServer.dto.CreateSolicitationDto;
import InovaRedeServer.dto.SolicitationResponseDto;
import InovaRedeServer.entity.Project;
import InovaRedeServer.entity.Solicitation;
import InovaRedeServer.entity.User;
import InovaRedeServer.repository.ProjectRepository;
import InovaRedeServer.repository.SolicitationRepository;
import InovaRedeServer.repository.UserRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class SolicitationService {
    
    UserRepository userRepository;
    SolicitationRepository solicitationRepository;
    ProjectRepository projectRepository;

    public SolicitationService(UserRepository userRepository, SolicitationRepository solicitationRepository, ProjectRepository projectRepository) {
        this.userRepository = userRepository;
        this.solicitationRepository = solicitationRepository;
        this.projectRepository = projectRepository;
    }
    
    public UUID createSolicitation(CreateSolicitationDto createSolicitationDto) { 
            
        User sender = userRepository.findById(createSolicitationDto.senderId()).orElse(null);
        User recipient = userRepository.findById(createSolicitationDto.recipientId()).orElse(null);
        Project project = projectRepository.findById(createSolicitationDto.projectId()).orElse(null);
        
        // DTO -> Entity
        var entity = new Solicitation(
                null, 
                sender,
                recipient,
                project
                );
        
        var solicitationSaved = solicitationRepository.save(entity); // Salva a solicitação no banco de dados
        return solicitationSaved.getSolicitationId(); // Retorna o UUID da solicitação
        
    }
    
    public void deleteById(String solicitationId) {
        var id = UUID.fromString(solicitationId);
        var solicitationExists = solicitationRepository.existsById(id);
        if(solicitationExists) {
            solicitationRepository.deleteById(id);
        }
    }
    
    public List<SolicitationResponseDto> listSolicitations(String userId) {
        
        // Validação
        var user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        
        return user.getSolicitationsReceived()
                .stream()
                .map(as -> new SolicitationResponseDto(as.getSolicitationId().toString(), as.getSender(), as.getRecipient(), as.getProject()))
                .toList();
    }
    
}
