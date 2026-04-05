package com.quizpilot.quizpilot.repository;

import com.quizpilot.quizpilot.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findByQuiz_Id(Long quizId); // ✅ FIXED
}