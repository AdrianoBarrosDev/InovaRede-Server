package InovaRede.InovaRedeServer.controller.dto;

import InovaRede.InovaRedeServer.entity.Project;
import InovaRede.InovaRedeServer.entity.User;

public record SolicitationResponseDto(String solicitationId, User sender, User recipient, Project project) {

}
