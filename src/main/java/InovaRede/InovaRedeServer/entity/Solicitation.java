package InovaRede.InovaRedeServer.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "tb_solicitations")
public class Solicitation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "solicitation_id")
    private UUID solicitationId;
    
    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "user_id")
    @JsonIgnore
    private User sender;
    
    @ManyToOne
    @JoinColumn(name = "recipient_id", referencedColumnName = "user_id")
    @JsonIgnore
    private User recipient;
    
    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "project_id")
    private Project project;
    
    public Solicitation() {
    }

    public Solicitation(UUID solicitationId, User sender, User recipient, Project project) {
        this.solicitationId = solicitationId;
        this.sender = sender;
        this.recipient = recipient;
        this.project = project;
    }

    // Getters e Setters
    public UUID getSolicitationId() {
        return solicitationId;
    }

    public void setSolicitationId(UUID solicitationId) {
        this.solicitationId = solicitationId;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
    
}
