package InovaRede.InovaRedeServer.controller.dto;

import java.util.UUID;

public record CreateSolicitationDto(UUID senderId, UUID recipientId, UUID projectId) {

}
