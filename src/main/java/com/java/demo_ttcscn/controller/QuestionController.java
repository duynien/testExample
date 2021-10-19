package com.java.demo_ttcscn.controller;

import com.java.demo_ttcscn.enitities.dto.QuestionDto;
import com.java.demo_ttcscn.enitities.model.Question;
import com.java.demo_ttcscn.enitities.payload.FilterQuestionRequest;
import com.java.demo_ttcscn.services.base.BaseService;
import com.java.demo_ttcscn.services.model.QuestionService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@CrossOrigin("*")
@RestController
@RequestMapping("/question")
public class QuestionController extends BaseControllerImpl<Question, QuestionDto> {
  @Autowired private QuestionService questionService;

  @Override
  protected BaseService<Question, QuestionDto> getService() {
    return questionService;
  }

  @GetMapping("/records")
  public ResponseEntity<?> getRecordPage(@RequestParam String code) {
    return ResponseEntity.ok(questionService.totalRecordRenderPage(code));
  }
  @GetMapping("/elements")
  public ResponseEntity<?> getTotalElement(@RequestParam String code) {
    return ResponseEntity.ok(questionService.totalElement(code));
  }
  @GetMapping("/level")
  public ResponseEntity<?> getLevel() {
    return ResponseEntity.ok(questionService.getLevels());
  }
  // Đợi xử lý random
  //  @GetMapping("/do-exam/random")
  //  public ResponseEntity<?> getRandomQuestionByCode(@RequestParam("code") String
  // code,@RequestParam("page") String page) {
  //    return ResponseEntity.ok(questionService.getRandomQuestionByCode(code));
  //  }

  @PostMapping("/do-exam/filter")
  public ResponseEntity<?> getQuestionByLevelAndCode(
      @ModelAttribute FilterQuestionRequest filterQuestionRequest) {
    return ResponseEntity.ok(questionService.handleQuestionByModalFilter(filterQuestionRequest));
  }

  @PostMapping("/do-exam/select")
  public ResponseEntity<?> getQuestionBySelectByUser(@RequestParam String message) {
    return ResponseEntity.ok(questionService.handleQuestionBySelectByUser(message));
  }

  @GetMapping("/get/filter")
  @ApiImplicitParams({
    @ApiImplicitParam(
        name = "page",
        dataType = "string",
        paramType = "query",
        value = "Results page you want to retrieve (0..N)"),
    @ApiImplicitParam(
        name = "size",
        dataType = "string",
        paramType = "query",
        value = "Number of records per page."),
    @ApiImplicitParam(
        name = "sort",
        allowMultiple = true,
        dataType = "string",
        paramType = "query",
        value =
            "Sorting criteria in the format: property(,asc|desc). "
                + "Default sort order is ascending. "
                + "Multiple sort criteria are supported.")
  })
  public ResponseEntity<?> getFilterByCodeQuestionByAdmin(
      @ApiIgnore @PageableDefault(size = 10) Pageable pageable, @RequestParam("code") String code) {
    return ResponseEntity.ok(questionService.getFilterByCodeQuestionByAdmin(pageable, code));
  }

  @GetMapping("/user/filter")
  @ApiImplicitParams({
    @ApiImplicitParam(
        name = "page",
        dataType = "string",
        paramType = "query",
        value = "Results page you want to retrieve (0..N)"),
    @ApiImplicitParam(
        name = "size",
        dataType = "string",
        paramType = "query",
        value = "Number of records per page."),
    @ApiImplicitParam(
        name = "sort",
        allowMultiple = true,
        dataType = "string",
        paramType = "query",
        value =
            "Sorting criteria in the format: property(,asc|desc). "
                + "Default sort order is ascending. "
                + "Multiple sort criteria are supported.")
  })
  //  http://localhost:8080/question/user/filter?code=123&sort=create_at,desc
  public ResponseEntity<?> getFilterByCodeQuestionByUser(
      @ApiIgnore @PageableDefault(size = 10) Pageable pageable, @RequestParam("code") String code) {
    return ResponseEntity.ok(questionService.getFilterByCodeQuestionByUser(pageable, code));
  }


}
