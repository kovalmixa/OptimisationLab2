package edu.dnu.fpm.pz.db.Entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "activity_log")
public class ActivityLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String action;

    private String details;

    private LocalDateTime timestamp;

    @PrePersist
    protected void onCreate() {
        this.timestamp = LocalDateTime.now();
    }

    public ActivityLog() {}

    public ActivityLog(User user, String action, String details, LocalDateTime timestamp) {
        this.user = user;
        this.action = action;
        this.details = details;
        this.timestamp = timestamp;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public User getUser() {return user;}
    public void setUser(User user) {this.user = user;}

    public String getAction() {return action;}
    public void setAction(String action) {this.action = action;}

    public String getDetails() {return details;}
    public void setDetails(String details) {this.details = details;}

    public LocalDateTime getTimestamp() {return timestamp;}
    public void setTimestamp(LocalDateTime timestamp) {this.timestamp = timestamp;}
}
