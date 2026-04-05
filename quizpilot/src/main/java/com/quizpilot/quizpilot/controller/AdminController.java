package com.quizpilot.quizpilot.controller;

import com.quizpilot.quizpilot.model.Question;
import com.quizpilot.quizpilot.model.Quiz;
import com.quizpilot.quizpilot.repository.QuestionRepository;
import com.quizpilot.quizpilot.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private QuizRepository quizRepo;

    @Autowired
    private QuestionRepository questionRepo;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("quizzes", quizRepo.findAll());
        return "admin_dashboard";
    }

    @PostMapping("/createQuiz")
    public String createQuiz(@RequestParam String title) {
        Quiz quiz = new Quiz(title);
        quizRepo.save(quiz);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/addQuestion")
    public String addQuestion(@RequestParam Long quizId,
                              @RequestParam String questionText,
                              @RequestParam String option1,
                              @RequestParam String option2,
                              @RequestParam String option3,
                              @RequestParam String option4,
                              @RequestParam int correctOption) {

        Quiz quiz = quizRepo.findById(quizId).get();

        Question q = new Question();
        q.setQuestionText(questionText);
        q.setOption1(option1);
        q.setOption2(option2);
        q.setOption3(option3);
        q.setOption4(option4);
        q.setCorrectOption(correctOption);
        q.setQuiz(quiz);

        questionRepo.save(q);

        return "redirect:/admin/dashboard";
    }
}