package com.java.demo_ttcscn.controller;

import com.java.demo_ttcscn.enitities.dto.AnswerDto;
import com.java.demo_ttcscn.enitities.model.Answer;
import com.java.demo_ttcscn.enitities.model.User;
import com.java.demo_ttcscn.enitities.payload.AnswerRequest;
import com.java.demo_ttcscn.services.UserDetailsImpl;
import com.java.demo_ttcscn.services.base.AuthenticationFacade;
import com.java.demo_ttcscn.services.base.BaseService;
import com.java.demo_ttcscn.services.model.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/answer")
public class AnswerController extends BaseControllerImpl<Answer, AnswerDto> {
  @Autowired private AnswerService answerService;

  @Override
  protected BaseService<Answer, AnswerDto> getService() {
    return answerService;
  }

  @PostMapping("/verify-answer")
  public ResponseEntity<?> verifyAnswer(@ModelAttribute AnswerRequest answerRequest) {
    return ResponseEntity.ok(answerService.submitAnswer(answerRequest.getAnswer()));
  }
}
