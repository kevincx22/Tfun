package edu.demo.edubackend.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
public class ChatTurn {
    @Id @GeneratedValue private Long id;
    private Long userId;
    @Lob private String question;
    @Lob private String answer;
    private Instant createdAt = Instant.now();

    public Long getId(){return id;}
    public Long getUserId(){return userId;}
    public void setUserId(Long u){this.userId=u;}
    public String getQuestion(){return question;}
    public void setQuestion(String q){this.question=q;}
    public String getAnswer(){return answer;}
    public void setAnswer(String a){this.answer=a;}
    public Instant getCreatedAt(){return createdAt;}
    public void setCreatedAt(Instant c){this.createdAt=c;}
}