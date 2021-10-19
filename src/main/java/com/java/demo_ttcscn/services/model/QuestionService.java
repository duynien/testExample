package com.java.demo_ttcscn.services.model;

import com.java.demo_ttcscn.enitities.dto.QuestionDto;
import com.java.demo_ttcscn.enitities.enum_value.Level;
import com.java.demo_ttcscn.enitities.model.Question;
import com.java.demo_ttcscn.enitities.payload.FilterQuestionRequest;
import com.java.demo_ttcscn.enitities.result.PageResponse;
import com.java.demo_ttcscn.enitities.result.QuestionResponse;
import com.java.demo_ttcscn.services.base.BaseService;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QuestionService extends BaseService<Question, QuestionDto> {
  QuestionResponse getmQuestionByCodeAndPage(String code,int page);

  int totalRecordRenderPage(String code);
  int totalElement(String code);
  List<Level> getLevels();
  // With API,SPA
  QuestionResponse getQuestionByLevelAndCode(FilterQuestionRequest filterQuestionRequest);
  // MPA
  QuestionResponse handleQuestionByModalFilter(FilterQuestionRequest filterQuestionRequest);

  PageResponse getFilterByCodeQuestionByAdmin(Pageable pageable, String code);

  PageResponse getFilterByCodeQuestionByUser(Pageable pageable, String code);
  // MPA
  QuestionResponse handleQuestionBySelectByUser(String message);
}
