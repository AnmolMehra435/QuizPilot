package com.quizpilot.quizpilot.controller;

import jakarta.servlet.http.HttpSession;
import com.quizpilot.quizpilot.model.Attempt;
import com.quizpilot.quizpilot.model.Question;
import com.quizpilot.quizpilot.repository.AttemptRepository;
import com.quizpilot.quizpilot.repository.QuestionRepository;
import com.quizpilot.quizpilot.repository.QuizRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private QuizRepository quizRepo;

    @Autowired
    private QuestionRepository questionRepo;

    @Autowired
    private AttemptRepository attemptRepo;

    // ✅ Show all quizzes
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("quizzes", quizRepo.findAll());
        return "student_dashboard";
    }

    // ✅ Open quiz
    @GetMapping("/quiz/{id}")
    public String takeQuiz(@PathVariable Long id, Model model) {
        model.addAttribute("questions", questionRepo.findByQuiz_Id(id));
        model.addAttribute("quizId", id);
        return "quiz";
    }

    // ✅ Submit quiz + leaderboard
    @PostMapping("/submit")
    public String submitQuiz(@RequestParam Long quizId,
                             HttpServletRequest request,
                             HttpSession session,
                             Model model) {

        List<Question> questions = questionRepo.findByQuiz_Id(quizId);

        int score = 0;

        for (Question q : questions) {
            String ans = request.getParameter("q_" + q.getId());

            if (ans != null) {
                try {
                    int selected = Integer.parseInt(ans);

                    if (selected == q.getCorrectOption()) {
                        score++;
                    }
                } catch (Exception e) {
                    // ignore invalid input
                }
            }
        }

        // ✅ Save attempt (for leaderboard)
        String username = (String) session.getAttribute("username");
        Attempt attempt = new Attempt(username, quizId, score);
        attemptRepo.save(attempt);

        // ✅ Send data to result page
        model.addAttribute("score", score);
        model.addAttribute("total", questions.size());

        model.addAttribute("leaderboard",
                attemptRepo.findByQuizIdOrderByScoreDesc(quizId));

        return "result";
    }
}