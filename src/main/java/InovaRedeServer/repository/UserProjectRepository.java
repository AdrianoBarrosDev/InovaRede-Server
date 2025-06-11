package InovaRedeServer.repository;

import InovaRedeServer.entity.UserProject;
import InovaRedeServer.entity.UserProjectId;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProjectRepository extends JpaRepository<UserProject, UserProjectId> {
    List<UserProject> findByUserUserId(UUID userId);
    
    boolean existsByUserUserIdAndProjectProjectId(UUID userId, UUID projectId);
}
