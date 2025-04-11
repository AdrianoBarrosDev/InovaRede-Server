package InovaRede.InovaRedeServer.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_users_projects")
public class UserProject {
    
    @EmbeddedId
    private UserProjectId id;
    
    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    @JsonBackReference("user-userProject")
    private User user;
    
    @ManyToOne
    @MapsId("projectId")
    @JoinColumn(name = "project_id")
    @JsonBackReference("project-userProject")
    private Project project;

    public UserProject() {
    }

    public UserProject(UserProjectId id, User user, Project project) {
        this.id = id;
        this.user = user;
        this.project = project;
    }

    public UserProjectId getId() {
        return id;
    }

    public void setId(UserProjectId id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
    
}
