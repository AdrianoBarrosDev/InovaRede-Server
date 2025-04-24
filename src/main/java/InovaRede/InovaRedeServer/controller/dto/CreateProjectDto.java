package InovaRede.InovaRedeServer.controller.dto;

import java.util.UUID;

public record CreateProjectDto(String name, String description, String start_date, String end_date, String image, String course, UUID ownerId) {

}
