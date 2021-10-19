package com.java.demo_ttcscn.services.model;

import com.java.demo_ttcscn.enitities.dto.AnswerDto;
import com.java.demo_ttcscn.enitities.model.Answer;
import com.java.demo_ttcscn.enitities.result.AnswerResponse;
import com.java.demo_ttcscn.enitities.result.Response;
import com.java.demo_ttcscn.services.base.BaseService;
import org.springframework.stereotype.Service;

public interface AnswerService extends BaseService<Answer, AnswerDto> {
    public AnswerResponse submitAnswer(String answerRequest);
}
