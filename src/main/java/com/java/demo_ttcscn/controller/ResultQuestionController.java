package com.java.demo_ttcscn.controller;

import com.java.demo_ttcscn.enitities.dto.ResultQuestionDto;
import com.java.demo_ttcscn.enitities.model.ResultQuestion;
import com.java.demo_ttcscn.services.UserDetailsImpl;
import com.java.demo_ttcscn.services.base.AuthenticationFacade;
import com.java.demo_ttcscn.services.base.BaseService;
import com.java.demo_ttcscn.services.model.ResultQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/result")
public class ResultQuestionController
    extends BaseControllerImpl<ResultQuestion, ResultQuestionDto> {
  @Autowired AuthenticationFacade authenticationFacade;
  @Autowired private ResultQuestionService resultQuestionService;

  @Override
  protected BaseService<ResultQuestion, ResultQuestionDto> getService() {
    return resultQuestionService;
  }
  @Override
  public ResponseEntity<?> create(ResultQuestionDto created) {
    UserDetailsImpl user = (UserDetailsImpl) authenticationFacade.getAuthentication().getPrincipal();
    created.setUsername(user.getUsername());
    return super.create(created);
  }
  @GetMapping("result-code")
  public ResponseEntity<?> getResultByCode(@RequestParam("code") String code) {
    return ResponseEntity.ok(resultQuestionService.getResultByCode(code));
  }

  @GetMapping("result-by-username")
  public ResponseEntity<?> getResultByUsername(@RequestParam("username") String username) {
    return ResponseEntity.ok(resultQuestionService.getResultByUsername(username));
  }
  @GetMapping("result-username/desc")
  public ResponseEntity<?> getResultByUsernameAndDesc(@RequestParam("username") String username) {
    return ResponseEntity.ok(resultQuestionService.getResultByUsernameAndDesc(username));
  }
  @GetMapping("result-username/asc")
  public ResponseEntity<?> getResultByUsernameAndAsc(@RequestParam("username") String username) {
    return ResponseEntity.ok(resultQuestionService.getResultByUsernameAndAsc(username));
  }
}
