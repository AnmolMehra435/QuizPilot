package com.quizpilot.quizpilot.model;

import jakarta.persistence.*;

@Entity
public class Attempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private Long quizId;
    private int score;

    public Attempt() {}

    public Attempt(String username, Long quizId, int score) {
        this.username = username;
        this.quizId = quizId;
        this.score = score;
    }

    public Long getId() { return id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public Long getQuizId() { return quizId; }
    public void setQuizId(Long quizId) { this.quizId = quizId; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
}