package InovaRede.InovaRedeServer.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "tb_users")
public class User {
    
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;
    
    @Column(name = "username")
    private String username;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "password")
    private String password;
    
    @Column(name = "course")
    private String course;
    
    @Column(name = "image", columnDefinition = "LONGTEXT")
    private String image; // Imagem Base64
    
    @CreationTimestamp
    private Instant creationTimestamp;
    
    @UpdateTimestamp
    private Instant updateTimestamp;
    
    @Version
    @Column(name = "version")
    private Long version;
    
    @OneToMany(mappedBy = "user")
    @JsonManagedReference("project-userProject")
    private List<UserProject> userProject;
    
    @OneToMany(mappedBy = "owner")
    private List<Project> myProjects;
    
    @OneToMany(mappedBy = "recipient")
    @JsonManagedReference("user-solicitation-recipient")
    @JsonIgnore
    private List<Solicitation> solicitationsReceived;

    public User() {
    }

    public User(UUID userId, String username, String name, String email, String password, String course, String image, Instant creationTimestamp, Instant updateTimestamp, Long version) {
        this.userId = userId;
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
        this.course = course;
        this.image = image;
        this.creationTimestamp = creationTimestamp;
        this.updateTimestamp = updateTimestamp;
        this.version = version;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Instant getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(Instant creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public Instant getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(Instant updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public List<UserProject> getUserProject() {
        return userProject;
    }

    public void setUserProject(List<UserProject> userProject) {
        this.userProject = userProject;
    }

    public List<Solicitation> getSolicitationsReceived() {
        return solicitationsReceived;
    }

    public void setSolicitationsReceived(List<Solicitation> solicitationsReceived) {
        this.solicitationsReceived = solicitationsReceived;
    }
    
}
