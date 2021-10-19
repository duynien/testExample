package com.java.demo_ttcscn.services.model;

import com.java.demo_ttcscn.enitities.dto.ResultQuestionDto;
import com.java.demo_ttcscn.enitities.model.ResultQuestion;
import com.java.demo_ttcscn.services.base.BaseService;

import java.util.List;

public interface ResultQuestionService extends BaseService<ResultQuestion, ResultQuestionDto> {
    List<ResultQuestionDto> getResultByCode(String code);
    List<ResultQuestionDto> getResultByUsername(String username);
    List<ResultQuestionDto> getResultByUsernameAndDesc(String username);
    List<ResultQuestionDto> getResultByUsernameAndAsc(String username);
}
