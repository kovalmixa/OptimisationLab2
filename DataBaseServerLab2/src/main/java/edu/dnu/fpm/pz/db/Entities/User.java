package edu.dnu.fpm.pz.db.Entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid")
    private Long id;

    @Column(name = "username", nullable = false, length = 255)
    private String username;

    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "passwordHash", nullable = false, length = 64)
    private String passwordHash;

    @Column(name = "bio", columnDefinition = "TEXT")
    private String bio;

//    @Lob
//    @Column(name = "profilePicture")
//    private byte[] profilePicture;

    @Column(name = "lastLogin")
    private LocalDateTime lastLogin;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    public User() {}

    public User(String username, String email, String passwordHash, String bio,
                byte[] image, LocalDateTime lastLogin, LocalDateTime createdAt) {
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.bio = bio;
//        this.profilePicture = image;
        this.lastLogin = lastLogin;
        this.createdAt = createdAt;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
//
//    public byte[] getProfilePicture() { return profilePicture; }
//    public void setProfilePicture(byte[] image) { this.profilePicture = image; }

    public LocalDateTime getLastLogin() { return lastLogin; }
    public void setLastLogin(LocalDateTime lastLogin) { this.lastLogin = lastLogin; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
