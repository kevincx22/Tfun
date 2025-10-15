package edu.demo.edubackend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users") // user是H2关键字
public class User {
    @Id @GeneratedValue private Long id;
    @Column(unique = true) private String email;
    private String passwordHash;
    private String role; // ROLE_STUDENT | ROLE_TEACHER
    public Long getId(){return id;}
    public String getEmail(){return email;}
    public void setEmail(String e){this.email=e;}
    public String getPasswordHash(){return passwordHash;}
    public void setPasswordHash(String p){this.passwordHash=p;}
    public String getRole(){return role;}
    public void setRole(String r){this.role=r;}
}