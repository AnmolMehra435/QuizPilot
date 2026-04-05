package com.quizpilot.quizpilot.repository;

import com.quizpilot.quizpilot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}