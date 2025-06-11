package InovaRedeServer.dto;

import java.util.UUID;

public record ProjectWithParticipationDto(UUID projectId, String name, String description, String course, String image, boolean participating) {

}
