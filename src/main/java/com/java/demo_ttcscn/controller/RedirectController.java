package com.java.demo_ttcscn.controller;

import com.java.demo_ttcscn.enitities.payload.FilterQuestionRequest;
import com.java.demo_ttcscn.enitities.result.QuestionResponse;
import com.java.demo_ttcscn.services.model.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RedirectController {
  @Autowired private QuestionService questionService;

  @GetMapping("/")
  public String home() {
    return "home";
  }

  @GetMapping("/user")
  public String user() {
    return "user";
  }

  @GetMapping("/403")
  public String error() {
    return "403";
  }

  @GetMapping("/admin")
  public String admin() {
    return "admin";
  }

  @GetMapping("/filter")
  public String doFilter() {
    return "filter";
  }

  //  @GetMapping("/filter/course")
  //  public String doFilterWithSearch(Model model, @RequestParam String course) {
  //    System.out.println(course);
  //    return "filter";
  //  }

  @PostMapping("/filter/do-exam")
  public String doFilter(Model model, @ModelAttribute FilterQuestionRequest filterQuestionRequest) {
    QuestionResponse questionResponse =
        questionService.handleQuestionByModalFilter(filterQuestionRequest);
    model.addAttribute("rs", questionResponse);
    return "exam";
  }

  @PostMapping("/select/do-exam")
  public String doFilterBySelect(Model model, @RequestParam String message) {
    QuestionResponse questionResponse = questionService.handleQuestionBySelectByUser(message);
    model.addAttribute("rs", questionResponse);
    return "exam";
  }

  @PostMapping("/random/do-exam")
  public String getRandomQuestionByCode(
      Model model, @RequestParam("code") String code, @RequestParam("page") String page) {
    QuestionResponse questionResponse =
        questionService.getmQuestionByCodeAndPage(code, Integer.parseInt(page));
    model.addAttribute("rs", questionResponse);
    return "exam";
  }
}
