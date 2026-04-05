package com.quizpilot.quizpilot.repository;

import com.quizpilot.quizpilot.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
}