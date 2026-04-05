package com.quizpilot.quizpilot.repository;

import com.quizpilot.quizpilot.model.Attempt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttemptRepository extends JpaRepository<Attempt, Long> {

    List<Attempt> findByQuizIdOrderByScoreDesc(Long quizId);
}