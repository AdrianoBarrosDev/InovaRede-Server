package InovaRede.InovaRedeServer.repository;

import InovaRede.InovaRedeServer.entity.Project;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {
    
}
