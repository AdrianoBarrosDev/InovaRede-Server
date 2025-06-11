package InovaRedeServer.dto;

import InovaRedeServer.entity.Project;
import InovaRedeServer.entity.User;

public record SolicitationResponseDto(String solicitationId, User sender, User recipient, Project project) {

}
