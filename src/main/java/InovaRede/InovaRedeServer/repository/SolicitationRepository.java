package InovaRede.InovaRedeServer.repository;

import InovaRede.InovaRedeServer.entity.Solicitation;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitationRepository extends JpaRepository<Solicitation, UUID> {
    
}
