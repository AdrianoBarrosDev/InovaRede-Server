package InovaRedeServer.repository;

import InovaRedeServer.entity.Project;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {
    List<Project> findByNameContaining(String name);
    List<Project> findByCourseContaining(String course);
    List<Project> findByNameContainingAndCourseContaining(String name, String course);
}
