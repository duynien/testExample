package com.java.demo_ttcscn.controller;

import com.java.demo_ttcscn.enitities.dto.CodeQuestionDto;
import com.java.demo_ttcscn.enitities.model.CodeQuestion;
import com.java.demo_ttcscn.services.base.BaseService;
import com.java.demo_ttcscn.services.model.CodeQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/code-question")
public class CodeQuestionController extends BaseControllerImpl<CodeQuestion, CodeQuestionDto> {
  @Autowired private CodeQuestionService codeQuestionService;

  @Override
  protected BaseService<CodeQuestion, CodeQuestionDto> getService() {
    return codeQuestionService;
  }

  @GetMapping("/topic-random")
  ResponseEntity<?> getRandomByTopic(@RequestParam(name = "topic") String topic) {
    return ResponseEntity.ok(codeQuestionService.getRandomCodeQuestionByTopic(topic));
  }

  @GetMapping("/all-code")
  ResponseEntity<?> getAllCode() {
    return ResponseEntity.ok(codeQuestionService.getAllCode());
  }
  @GetMapping("/topic")
  ResponseEntity<?> getAllCodeQuestionByTopic(@RequestParam(name = "topic") String topic) {
    return ResponseEntity.ok(codeQuestionService.getAllCodeQuestionByTopic(topic));
  }
}
