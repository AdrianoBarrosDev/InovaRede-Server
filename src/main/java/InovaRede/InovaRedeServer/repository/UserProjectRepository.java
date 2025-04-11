package InovaRede.InovaRedeServer.repository;

import InovaRede.InovaRedeServer.entity.UserProject;
import InovaRede.InovaRedeServer.entity.UserProjectId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProjectRepository extends JpaRepository<UserProject, UserProjectId> {
    
}
