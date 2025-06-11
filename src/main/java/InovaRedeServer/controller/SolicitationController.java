package InovaRedeServer.controller;

import InovaRedeServer.dto.CreateSolicitationDto;
import InovaRedeServer.dto.SolicitationResponseDto;
import InovaRedeServer.entity.Solicitation;
import InovaRedeServer.service.SolicitationService;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/solicitations")
public class SolicitationController {
    
    SolicitationService solicitationService;

    public SolicitationController(SolicitationService solicitationService) {
        this.solicitationService = solicitationService;
    }
    
    // Método para criar solicitação
    @PostMapping
    public ResponseEntity<Solicitation> createUser(@RequestBody CreateSolicitationDto createSolicitationDto) {
        var solicitationId = solicitationService.createSolicitation(createSolicitationDto);
        return ResponseEntity.created(URI.create("/solicitation/" + solicitationId.toString())).build();
    }
    
    // Método para listar solicitações recebidas do usuário
    @GetMapping("/{userId}")
    public ResponseEntity<List<SolicitationResponseDto>> listSolicitations(@PathVariable("userId") String userId) {
        var solicitations = solicitationService.listSolicitations(userId);
        return ResponseEntity.ok(solicitations);
    }
    
    // Método para excluir solicitações
    @DeleteMapping("/{solicitationId}")
    public ResponseEntity<Void> deleteById(@PathVariable("solicitationId") String solicitationId) {
        solicitationService.deleteById(solicitationId);
        return ResponseEntity.noContent().build();
    }
    
}
